package com.globalwave.system.web;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;

import com.globalwave.base.BaseAction;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.entity.EventLog;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.common.service.EventLogBO;
import com.globalwave.system.entity.SessionUser;
import com.globalwave.system.entity.User;
import com.globalwave.system.web.annotations.Pid;
import com.globalwave.util.GsonUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class PrivilegeCheckInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -4519855194951398071L;
    
    Log log = LogFactoryImpl.getLog(this.getClass()) ;
    
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

    	ActionProxy actionProxy = actionInvocation.getProxy();
		if (log.isDebugEnabled()) {
    		log.debug("action Namespace : " + actionProxy.getNamespace()) ;
    		log.debug("action Name : " + actionProxy.getActionName()) ;
            log.debug("action class : " + actionProxy.getAction().getClass().getName()) ;
            log.debug("action method : " + actionProxy.getMethod()) ;
    	}
    	
        final BaseAction action = (BaseAction) actionProxy.getAction() ;
        final String methodName = actionProxy.getMethod() ;
        final Method method = action.getClass().getMethod(methodName, new Class[]{}) ;
        
        
        final SessionUser user = 
            (SessionUser) action.getRequest().getSession().getAttribute(SessionUser.SESSION_PK) ;
        
        if (user != null) {
            SessionUser.set(user) ;// 即使无需要权限验证，也得设置用户信息
        } 

        final Pid pid = method.getAnnotation(Pid.class) ;
        
        if (pid !=null && pid.log()) {
        	log(actionInvocation, user, pid.value()) ;
        }
        
        if(pid == null || pid.ignore() || pid.value() == Pid.DO_NOT_CHECK) {// 无需要作权限验证
            String result = actionInvocation.invoke() ;
            SessionUser.remove() ;
            return result ;
        } else {
        	if(user == null) {
        		if (action.getRequest().getHeader("Accept").indexOf("json") > -1) {
        		    throw new BusinessException(1000L) ;// 未登陆或已经超时，请重新登陆！
        		} else {
        			action.getResponse().sendRedirect(action.getRequest().getContextPath()+"/logon.jsp");
        		    return null;
        		}
        	}
        	if(pid.value() == Pid.LOGINED || user.hasPrivilege(pid.value())) {
        		String result = actionInvocation.invoke() ;
        		SessionUser.remove() ;
                return result ;
	        } else {
	    		log.warn("can't visite : " + actionProxy.getActionName()) ;
	        	throw new BusinessException(1001L, CodeHelper.getString("Privilege", "name_", new Long(pid.value()))) ;// 当前用户没有操作权限！
	        }
        }
    }

    
    private void log(ActionInvocation actionInvocation, SessionUser user, short pid) {

    	EventLog eventLog = new EventLog() ;
    	try {
	    	EventLogBO eventLogBO = (EventLogBO)CodeHelper.getAppContext().getBean("eventLogBO") ;
	    	
	    	User u = null ;
	    	
	    	String method = actionInvocation.getProxy().getMethod() ;
	    	if ("login".equals(method)) {
	    		u = ((UserAction) actionInvocation.getAction()).getUser() ;
	    	} else {
	    		u = user.getUser() ;
	    	}

    		String action_url = actionInvocation.getProxy().getNamespace() + "/" + actionInvocation.getProxy().getActionName() ;
    		String desc_ = CodeHelper.getString("SYS.ActionName", "name_", action_url) ;
    		
    		if (desc_ == null) {
    			desc_ = CodeHelper.getString("SYS.ActionName", "name_", method) ;
    		}

    		if (log.isErrorEnabled()) {
    			if (desc_ == null) {
    				System.err.println("no config path for event:" + action_url);
    			}
    		}
    		
    		if (pid != Pid.DO_NOT_CHECK) {
    			if (desc_ == null) {
    				desc_ = method ;
    			}
			    desc_ = CodeHelper.getString("Privilege", "name_", new Long(pid)) + "-" + desc_  ;
    		}
    		
	    	eventLog.setDesc_(desc_) ;
	    	eventLog.setExt_c2(action_url) ;
	    	
	    	eventLog.setEvent_type_code("ACTION") ;
	    	eventLog.setUser_id(u.getId()) ;
	    	eventLog.setUser_name(user == null?u.getLogin_id():u.getName_cn() + " " + (u.getName_en() == null?"":u.getName_en())) ;
	    	eventLog.setCreated_by(u.getLogin_id()) ;
	    	
	    	HttpServletRequest request = ((HttpServletRequest) ((BaseAction)actionInvocation.getProxy().getAction()).getRequest()) ;
	    	
	    	Map<String, String> paramMap = new HashMap<String, String>() ;
	    	paramMap.putAll(request.getParameterMap()) ;
	    	if (paramMap.get("user.password_") != null) {
	    	    paramMap.put("user.password_", "******") ;
	    	}
	    	String biz_data = 
	    			GsonUtil.getGson().toJson(paramMap) ;
	    	String data1 = (biz_data.length() > 2500)? biz_data.substring(0, 2500) : biz_data ;
	    	String data2 = (biz_data.length() > 2500)? biz_data.substring(2500) : "" ;
	    	eventLog.setBiz_data(data1) ;
	    	eventLog.setExt_c1(data2.length() > 2500?data2.substring(0, 2500):data2) ;
	    	eventLog.setExt_c3(request.getRemoteHost()) ;
	    	
	    	eventLogBO.create(eventLog) ;
    	} catch (Exception e) {
    		log.error(GsonUtil.getGson().toJson(eventLog)) ;
    		log.error(e, e) ;
    	}
        
    }
}
