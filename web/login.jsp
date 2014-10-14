<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>香车</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  
  <%@include file="/WEB-INF/pages/common/head_lib.jsp" %>

  <link href="<%=root %>/css/style.css" rel="stylesheet" type="text/css" />
  
  <script type="text/javascript" src="<%=root %>/script/system.js"></script>
  <script type="text/javascript">
    	/*实施登陆提交*/
    	function login_submit(){
    		document.getElementById("login").submit();//提交搜索表单
        }
    </script>
</head>

  
  <body class="login_body">
	<div class="lg_wraper">
		<div style="height:170px; overflow:hidden;"><img src="images/login_bg01.jpg" height="170" /></div>
		<div style="height:97px; overflow:hidden;"><img src="images/login_bg02.jpg" height="97"/></div>
		<div class="login_box">
			<img src="images/login_bg03.jpg" height="301"/>
			<div>
			<form action="<%=root %>/biz/login.action" method="post" id="login">
			<input name="user_name" type="text" class="ipt_txt" />
			<input name="user_password" type="text" class="ipt_txt"  />
			<input name="user_code" type="text" class="ipt_txt ipt_sort" /><img src="images/login_code.jpg" />
			<input name="login_OK" type="button" class="ipt_btn" onclick="login_submit();" value="登 录" />
			</form>
			</div>
		</div>
	</div>
</body>
</html>
