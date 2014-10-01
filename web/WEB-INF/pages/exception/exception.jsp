<%@page import="com.globalwave.system.entity.SessionUser"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.interceptor.ExceptionHolder"%>
<%@page import="com.opensymphony.xwork2.util.ValueStack"%>
<%@page import="com.globalwave.common.exception.BusinessException"%>
<%@page import="org.apache.commons.logging.impl.LogFactoryImpl"%>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
LogFactoryImpl.getLog("").error(request.getAttribute("exceptionStack")); 

ValueStack s = ActionContext.getContext().getValueStack() ;

String code = "";
String message = "";
String stack = "" ;
for(int i = s.size();i>0;i--){
    Object obj = s.pop();
    if(obj instanceof ExceptionHolder){
    	ExceptionHolder eh = (ExceptionHolder)obj;
        Exception e = eh.getException();
        if(e instanceof BusinessException){
        	BusinessException be = (BusinessException)e ;
        	code = be.getMessageKey() ;
        	message = be.getMessage() ;
        } else {
        	code = "系统出错，请与管理员联系！" ;
            message = e.toString() ;
            stack = (String)request.getAttribute("exceptionStack") ;
            
            if (stack == null) {
            	stack = "" ;
            }
        }
        break;
    }
}


final String type = (String)request.getAttribute("result_type") ;
if (request.getHeader("Accept").indexOf("json") > -1||true) {
	response.setContentType("application/json;charset=UTF-8") ;
	com.google.gson.JsonObject jsonObject = new com.google.gson.JsonObject(); 
    jsonObject.addProperty("code", code);//正常返回
    jsonObject.addProperty("message", message);
    jsonObject.addProperty("error", message);
    jsonObject.addProperty("stack", stack);
	response.getWriter().write(jsonObject.toString()) ;
	
} else if ( "html".equalsIgnoreCase(type) ) {
%>

<html>
 <head>
    <title>异常信息</title>
 </head>
 
 <body>
 <h2>
 出现异常啦
 </h2>
 <hr/>
   <h3 style="color:red">
   <!-- 获得异常对象 -->
    <s:property value="exception.message"/>
    </h3>
    <br/>
    <!-- 异常堆栈信息 -->
    <s:property value="exceptionStack"/>
</html>

<%
} else {
%>
<%@ page contentType="text/plain;charset=UTF-8" %>
<root>
  <code><%=code%></code>
  <error><s:property value="exception.localizedMessage"/></error>
  <message><%=message%></message>
  <stack><%=stack%></stack>
</root>	
<%
}



SessionUser.remove() ;
SessionUser.removeLocale() ;
%>