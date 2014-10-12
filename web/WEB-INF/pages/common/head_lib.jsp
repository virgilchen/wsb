<%@page import="com.globalwave.util.GsonUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.globalwave.common.cache.CodeHelper"%>
<%@page import="com.globalwave.system.entity.SessionUser"%>

<meta http-equiv="X-UA-Compatible" content="IE=100">
<meta charset="utf-8" />

<%@include file="/WEB-INF/pages/common/head_session_check.jsp" %>

<%
final String root = request.getContextPath() ;
final String context = root ;
final String contextPath = root ;
//final String company_name = "";//i18n.html("CompanyName") ;

String js_min = CodeHelper.getString("DEV.js.config", "name_", "min_mode") ;
if (js_min == null) {
    js_min = "" ;
} ;
%>

<script>
var root = "<%=root%>";
</script>
<!-- base lib -->
<script type="text/javascript" src="<%=root%>/script/jquery-1.11.1.<%=js_min %>js"></script>
<script type="text/javascript" src="<%=root%>/script/jquery-migrate-1.2.1.min.js"></script>

<script type="text/javascript" src="<%=root%>/script/core.js"></script>
<script type="text/javascript" src="<%=root%>/script/script.js"></script>

<!-- template lib -->
<script type="text/javascript" src="<%=root%>/plugin/jTemplates/jquery-jtemplates.<%=js_min %>js"></script>


<%if(request.getAttribute("_noJqueryUI") == null) { %>
<!-- jquery ui -->
<link rel="stylesheet" type="text/css" href="<%=root%>/plugin/jquery-ui-1.9.0/css/custom-theme/jquery-ui-1.9.0.custom.<%=js_min %>css">

<script type="text/javascript" src="<%=root%>/plugin/jquery-ui-1.9.0/js/jquery-ui-1.9.0.custom.<%=js_min %>js"></script>
<%--
<script type="text/javascript" src="<%=root%>/plugin/jquery-ui-1.11.1/jquery-ui.<%=js_min %>js"></script> --%>
<link rel="stylesheet" type="text/css" href="<%=root%>/plugin/jquery-ui-1.9.0/css/custom-theme/jquery-ui-1.9.0.ext.css">
<script type="text/javascript" src="<%=root%>/plugin/jquery-ui-1.9.0/js/jquery-ui-1.9.0.ext.js"></script>

<script src="<%=request.getContextPath() %>/plugin/timepicker/jquery-ui-timepicker-addon.js"></script>
<script src="<%=request.getContextPath() %>/plugin/timepicker/i18n/jquery-ui-timepicker-zh-CN.js"></script>

<link rel="stylesheet" type="text/css" href="<%=root%>/plugin/jquery-tools/validator.css">
<script type="text/javascript" src="<%=root%>/plugin/jquery-tools/validator.js"></script>
<script type="text/javascript" src="<%=root%>/script/keyboard.js"></script>
<%} %>




<%if(request.getAttribute("_noDictLib") == null) { %>
<!-- 
<script type="text/javascript" src="<%=root%>/system/dict_findOptions.action?dataType=json" id="dict_script"></script>
 -->
<script>
<%SessionUser sessionUser = (SessionUser)session.getAttribute(SessionUser.SESSION_PK) ;%>

<%if (request.getServletPath().indexOf("logon.jsp") >= 0 
          || session.getAttribute(CodeHelper.SESSION_LOCAL_STORAGE_PK) == null 
          || sessionUser.getVersionId() < CodeHelper.getVersionId()) {%>

var g$dict = <%=GsonUtil.getGson().toJson(CodeHelper.findOptions(0))%>;
g$LS.put("g$dict", g$dict) ;

<%if (sessionUser != null) sessionUser.setVersionId(CodeHelper.getVersionId());%>
<%System.err.println("load g$dict from remote") ;%>

<%} else {%>

var g$dict = g$LS.get("g$dict") ;
<%System.err.println("load g$dict from local") ;%>

<%}%>


</script>
<script type="text/javascript" src="<%=root%>/script/dict-util.js"></script>
<%} %>



