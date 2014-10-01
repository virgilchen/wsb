<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>香车</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  
  <%@include file="/WEB-INF/pages/common/head_lib.jsp" %>

  <link href="<%=root %>/css/style.css" rel="stylesheet" type="text/css" />
  
  <script type="text/javascript" src="<%=root %>/script/system.js"></script>
</head>

<!--
<frameset rows="80,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="frameTitle.jsp" id="topFrame" title="topFrame" scrolling="No" noresize="noresize" />
  <frameset id="FrameStat" cols="180,*" frameborder="no" border="0" framespacing="0">
    <frame src="frameMenu.jsp" id="leftFrame" title="leftFrame" scrolling="yes" />
    <frame src="frameMain.jsp" id="mainFrame" title="mainFrame" noresize="noresize" style="border-top:1px #ccc solid;" />
  </frameset>
</frameset>
 
 -->

<body style="margin: 0;">
  <table style="width: 100%;height: 100%;"  border="0" cellpadding="0" cellspacing="0">
    <tr style="height: 80px;">
      <td colspan="2"><%@include file="frameTitle.jsp" %></td>
    </tr>
    <tr>
      <td width="180px" valign="top">
        <%@include file="frameMenu.jsp" %>
      </td>
      <!-- for IE, set big width -->
      <td width="100000px" valign="top" style="padding: 5px;">
        <!-- 
        <div style="background-color: red;height: 100%; width: 100%;">x</div>
         -->
        <div style="height: 100%; width: 100%; " id="viewContentDiv"></div>
      </td>
    </tr>
  </table>
</body>

</html>
