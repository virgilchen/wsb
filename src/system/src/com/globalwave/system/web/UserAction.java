package com.globalwave.system.web;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.Convertor;
import com.globalwave.common.U;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.entity.Privilege;
import com.globalwave.system.entity.SessionUser;
import com.globalwave.system.entity.User;
import com.globalwave.system.entity.UserSO;
import com.globalwave.system.service.UserBO;
import com.globalwave.system.service.ValidationCodeBO;
import com.globalwave.system.web.annotations.Pid;
import com.opensymphony.xwork2.Preparable;

@Service("system_userAction")
@Scope("prototype")
public class UserAction extends BaseAction implements Preparable {
    
    private static final long serialVersionUID = 182557698841454660L;
    
    private UserBO userBO = null ;
    
    private User user ;
    private UserSO userSO ;
    private Long[] organization_ids ;
    private Long[] role_ids ;
    private boolean isLoginPos = false ;
    private HashMap<String, String> rates;
    
    public String execute() throws Exception {
    	
        return this.list();    
        
    }
    
    @Override
    public String view() throws Exception {
    	/*
    	this.getRequest().setAttribute(
    			"companyOptions", 
    			GsonUtil.getGson().toJson(getCompanyBO().queryAll())) ;
         */
		//user.setCompanyOptions(getCompanyBO().queryAll());
    	return super.view();
    }

    @Pid(value=Pid.LOGINED, log=false)
    public String list() throws Exception {        

    	userSO.setType_not(User.TYPE_CUSTOMER);
    	
        ArrayPageList<User> pageList = userBO.query(userSO) ;

        renderList(pageList) ;
        
        return null ;    
        
    }

    //列出所有同公司的员工
    @Pid(value=Pid.LOGINED, log=false)
    public String listUsersByCompanyId() throws Exception {        
    	//SessionUser su = SessionUser.get();
    	userSO = new UserSO();
    	userSO.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE) ;
    	userSO.setStatus_("A");
        ArrayPageList<User> pageList = userBO.query(userSO) ;

        renderList(pageList) ;
        
        return null ;    
    }
    
    @Pid(value=2070, log=false)
    public String getMenu() throws Exception {        

        ArrayPageList<Privilege> pageList = SessionUser.get().getPrivileges() ;
        List<String> l = new ArrayList<String>();
        l.add("id");

//        for (int i = pageList.size() - 1 ; i >= 0 ; i --) {
//        	Privilege p = pageList.get(i) ;
//        	if (p.getUrl_() == null|| "".equals(p.getUrl_())) {
//        		pageList.remove(p) ;
//        	}
//        }
        
        renderList(pageList, l) ;
        
        return null ;    
        
    }

    
    @Pid(value=Pid.LOGINED, log=false)
    public String get() throws Exception {   
    	User user = SessionUser.get().getUser();
    	String userType = user.getType_();
    	if ("C".equals(userType)) {
    		if (this.id.longValue() != user.getId().longValue()) {
    			return null;
    		}
    	}

    	user = userBO.get(this.id, true, true) ;
    	user.setPassword_("") ;
    	
    	Object rates = null;

    	renderObject(new Object[]{user, rates}, null) ; 
        return null ;  
    }

    @Pid(value=Pid.LOGINED, log=false)
    public String getInfo() throws Exception {  
    	User user = SessionUser.get().getUser();
    	String userType = user.getType_();
    	if ("C".equals(userType)) {
    		if (this.id.longValue() != user.getId().longValue()) {
    			return null;
    		}
    	}

    	user = userBO.get(this.id, true, false) ;
    	user.setPassword_("") ;
    	
    	renderObject(user, null) ; 
        return null ;  
    }
    
    //@Pid(value=102)
    @Pid(value=2070)
    public String create()  throws Exception { 
    	
    	if (user.getExpired_date() == null) {
    		user.setExpired_date(new Timestamp(U.int2Date(20300101).getTime())) ;
    	}
        
        Object newUser = userBO.create(user, organization_ids, role_ids, this.rates) ;

        CodeHelper.reload("ProfitRate") ;
        
        renderObject(newUser, ResponseMessage.KEY_CREATE_OK) ;
        
        return null;    
        
    }
    
    /**
     * 更新信息，包括组织与权限
     * 
     * @return
     * @throws Exception
     */
    //@Pid(value=103)
    @Pid(value=2070)
    public String update()  throws Exception { 
    	
    	if (user.getExpired_date() == null) {
    		user.setExpired_date(new Timestamp(U.int2Date(20300101).getTime())) ;
    	}
    	
        userBO.update(user,organization_ids, role_ids, this.rates) ;

        CodeHelper.reload("ProfitRate") ;
        
        renderObject(user, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }
    
    /**
     * 更新基础信息，不包括组织与权限
     * 
     * @return
     * @throws Exception
     */
    //@Pid(value=109)
    @Pid(value=2070)
    public String updateBaseInfo()  throws Exception { 

    	if (user.getExpired_date() == null) {
    		user.setExpired_date(new Timestamp(U.int2Date(20300101).getTime())) ;
    	}
    	
        userBO.update(user) ;

        renderObject(user, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }
    
   // @Pid(value=103)
    @Pid(value=2070)
    public String forzen()  throws Exception { 
        
        userBO.forzen(user) ;

        CodeHelper.reload("ProfitRate") ;
        
        renderObject(user, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
    	
    }
    
    //@Pid(value=103)
    @Pid(value=2070)
    public String active()  throws Exception { 
        
        userBO.active(user) ;

        CodeHelper.reload("ProfitRate") ;
        
        renderObject(user, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
    	
    }

    //@Pid(value=104)
    @Pid(value=2070)
    public String delete()  throws Exception {
        
        if (ids == null) {
            userBO.delete(user) ;
        } else {
            userBO.deleteAll(ids) ;
        }

        CodeHelper.reload("ProfitRate") ;
        //renderXML(this.getSuccessDeleteMessage().asXML()) ;
        renderObject(null, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }


    @Pid(value=Pid.DO_NOT_CHECK)
    public String changePassword() throws Exception {
    	
    	if (user == null) {
    		return "jsp" ;
    	}
    	
    	userBO.changePassword(user) ;

        //renderXML(this.getMessage(null, "成功更改密码！").asXML()) ;
    	renderObject(null, 1107L) ;//('1107', '用户已经成功更改密码！' );
    	
        return null; 
    }
    

    @Pid(value=Pid.DO_NOT_CHECK)
    public String reloginWin() throws Exception {
    	
    		return "jsp" ;
    }

    @Pid(value=Pid.LOGINED)
    public String loginForward() throws Exception {
    	if (user == null) {
    		return "jsp" ;// 转到JSP Login
    	}
    	// 具体权限校验在BO中
    	
    	Locale locale = null ;
    	if ("en".equals(this.getRequest().getParameter("language"))) {
    		locale = Locale.ENGLISH ;
    		SessionUser.setLocale(locale) ;
    	} else {
    		SessionUser.setLocale(null) ;
    	}
    	
        SessionUser sessionUser = this.userBO.loginForward(this.user) ;
    	sessionUser.setUserLocale(locale) ;
    	sessionUser.setVersionId(CodeHelper.getVersionId()) ;
        
        this.getSession().setAttribute(SessionUser.SESSION_PK, sessionUser);
        
        renderObject(sessionUser, 1106L) ;

    	SessionUser.removeLocale() ;
        return null;
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String login() throws Exception {
    	
    	if (user == null) {
    		return "jsp" ;// 转到JSP Login
    	}

    	String isPos = getRequest().getParameter("isLoginPos") ;
    	if (isPos == null) {
    		this.isLoginPos = false ;
    	} else {
    		this.isLoginPos = Boolean.parseBoolean(isPos) ;
    	}

    	Locale locale = null ;
    	if ("en".equals(this.getRequest().getParameter("language"))) {
    		locale = Locale.ENGLISH ;
    		SessionUser.setLocale(locale) ;
    	} else {
    		SessionUser.setLocale(null) ;
    	}
    	
        SessionUser sessionUser = this.userBO.login(this.user, isLoginPos) ;
    	sessionUser.setUserLocale(locale) ;
    	sessionUser.setVersionId(CodeHelper.getVersionId()) ;
        
        this.getSession().setAttribute(SessionUser.SESSION_PK, sessionUser);
        
        if (isJsonRequest()) {
        	SessionUser su = new SessionUser();
        	su.setUser(sessionUser.getUser());
        	renderObject(su, 1106L) ;
        } else {
		    Element root = sessionUser.toXML() ;
	        Convertor.addProperty(root, "system_date", U.currentDate()) ;
	        renderXML(root.asXML()) ;
        }

    	SessionUser.removeLocale() ;
    	
    	String IS_LOCAL_STORAGE_SUPPORT = getRequest().getParameter("IS_LOCAL_STORAGE_SUPPORT") ;
    	if (IS_LOCAL_STORAGE_SUPPORT != null) {
    		this.getSession().setAttribute(CodeHelper.SESSION_LOCAL_STORAGE_PK, Boolean.TRUE) ;
    	}
    	
        return null;
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String logout() throws Exception {

        this.getSession().removeAttribute(SessionUser.SESSION_PK);
        
    	renderObject(null, 1113L) ;// 1113', '用户已经成功登出！
        
        return null;
    }
    
    //检查用户是否已登录，用于app
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String checkLogon() throws Exception {
        if (this.getSession().getAttribute(SessionUser.SESSION_PK) != null) {
        	//renderObject(null, 1106L) ;
        	renderText("1");
        }
        else {
        	//renderObject(null, 1101L) ;
        	renderText("0");
        }
        
        return null;
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String getValidationCode() throws Exception {

        //HttpSession session = request.getSession();

    	HttpServletResponse response = this.getResponse() ;
        response.setContentType("image/jpeg");

        // 设置浏览器不要缓存此图片
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
    	
    	Object[] sizeAndCode = new ValidationCodeBO().generate(response.getOutputStream()) ;
    	
    	if (log.isDebugEnabled()) {
    		log.debug("size:" + sizeAndCode[0]) ;
    		log.debug("code:" + sizeAndCode[1]) ;
    	}
    	
    	response.setContentLength((Integer)sizeAndCode[0]) ;
        
		// 将当前验证码存入到 Session 中

		this.getSession().setAttribute("check_code", sizeAndCode[1]);

		// 直接使用下面的代码将有问题， Session 对象必须在提交响应前获得

		// request.getSession().setAttribute("check_code",new String(rands));
    	return null ;
    }
    
    public String cookieLogin() {
        return null;
    }
    
    // ------------------------------------------------------------------------
    
    // ioc
    public void setUserBO(UserBO userService) {
        this.userBO = userService;
    }

    // entity...
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserSO getUserSO() {
        return userSO;
    }

    public void setUserSO(UserSO userCriterion) {
        this.userSO = userCriterion;
    }

    public Long[] getOrganization_ids() {
        return organization_ids;
    }

    public void setOrganization_ids(Long[] organization_ids) {
        this.organization_ids = organization_ids;
    }

	public Long[] getRole_ids() {
		return role_ids;
	}

	public void setRole_ids(Long[] role_ids) {
		this.role_ids = role_ids;
	}

	public HashMap<String, String> getRates() {
		return rates;
	}

	public void setRates(HashMap<String, String> rates) {
		this.rates = rates;
	}

}
