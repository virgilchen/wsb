<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Locale"%>
<%@page import="com.globalwave.common.message.I18N"%>
<%@page import="com.globalwave.system.entity.SessionUser"%>

<%

Locale locale = null ;
I18N i18n = null ;

if (request.getServletPath().indexOf("login.jsp") == -1) {
	SessionUser sessionUser = (SessionUser)session.getAttribute(SessionUser.SESSION_PK) ;
    if (sessionUser == null) {
	     response.sendRedirect(request.getContextPath()+"/login.jsp");
	     return;
    }
    locale = sessionUser.getUserLocale() ;
    i18n = new I18N(locale) ;
} else {
	locale = request.getLocale() ;
	String lang = request.getParameter("language") ;
	if ("en".equals(lang)) {
	    locale = Locale.ENGLISH ;
	} else if ("cn".equals(lang)) {
        locale = Locale.CHINA ;
    }
    i18n = new I18N(locale) ;
}
%>

<script>var language = "<%=locale == null ?"cn":locale.getLanguage()%>" ;</script>