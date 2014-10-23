<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>香车驿站-登录</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  
  <%@include file="/WEB-INF/pages/common/head_lib.jsp" %>

  <link href="<%=root %>/css/style.css" rel="stylesheet" type="text/css" />
  
	<script type="text/javascript">
		function login_submit(){
			
	   		$.ajax({
	   			type: "post",
	   			data: $("#login").serialize(),
	   			url: "<%=root %>/biz/login.action", 
	   			success: function(data){
	   				if (data.code == 0) {
          				window.location.href = "index.jsp";  
	   				} else if (typeof(data.message) != "undefined") {
	                    alert(data.message) ;

	                    $('#login_profile').focus();
	                    $("#login_profile").select() ;
	   				}
	   			}
	   		});
	   	}
		
	    $(function () {           
	        $('#login_profile').bind('keydown', function (e) {
	            if (e.which == 13) {
	                $("#login_pwd").focus() ;
	                $("#login_pwd").select() ;
	            }
	        });
	        $('#login_pwd').bind('keydown', function (e) {
	            if (e.which == 13) {
	            	login_submit() ;
	            }
	        });

	        $('#login_profile').focus();
	        
	        $("#IS_LOCAL_STORAGE_SUPPORT").attr("disabled", !g$LS.isEnabled()) ;
	        
	        if (!g$LS.isEnabled()) {
	            alert("你的浏览器不支持本地缓存，建议使用更高版本的浏览器（如：chrome26+、IE8+)，\n以达到更好的体验效果！") ;
	        }
	    }) ;
    </script>
</head>

  
  <body class="login_body">
	<div class="lg_wraper">
		<div style="height:170px; overflow:hidden;"><img src="images/login_bg01.jpg" height="170" /></div>
		<div style="height:97px; overflow:hidden;"><img src="images/login_bg02.jpg" height="97"/></div>
		<div class="login_box">
		  <img src="images/login_bg03.jpg" height="301"/>
	      <div>
			<form id="login">
			  <input name="staffSO.staff_login_profile" id="login_profile" type="text" class="ipt_txt" />
			  <input name="staffSO.staff_login_pwd" id="login_pwd" type="password" class="ipt_txt"  />
			  <input name="staffSO.login_code" type="text" class="ipt_txt ipt_sort" /><img src="images/login_code.jpg" />
			  <input name="login_OK" type="button" class="ipt_btn" onclick="login_submit();" value="登 录" />
			</form>
		  </div>
		</div>
	</div>
</body>
</html>
