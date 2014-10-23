<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    create_url:root + "/biz/staff_changePassword.action" ,
    update_url:root + "/biz/staff_changePassword.action" ,
    entityName:"staff",
    
    init:function (){

        E$("eForm").validator();
        E("eForm").setFirstFocus();
    },
    onSaveOk:function(data) {
    	removeView(<%=view_id%>);
    	return true;
    }
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

<div class="main_title">
    <b>修改密码</b>
    <div class="main_tt_right fr">
        <a href="javascript:viewJs.save();" class="orange">修改</a>
        <a href="javascript:removeView(<%=view_id%>);" class="blue">取消</a>
    </div>
</div>
 
<div class="main_form">


  <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="staff.id" id="staff.id"/>
      <input type="hidden" name="staff.version_id" id="staff.version_id"/>
	  <table width="100%" border="0">
	    <!-- 
		<tr>
		  <th>帐号：</th>
		  <td>wangxiaoming</td>
		</tr>
	     -->  
		<tr>
		  <th>输入原密码：</th>
		  <td><input type="password" name="staff.old_password"/><span class="c_red">*</span></td>
		</tr>
	    <tr>
	      <th>输入新密码：</th>
	      <td><input type="password" name="staff.staff_login_pwd"/><span class="c_red">*</span></td>
	    </tr>
	    <tr>
	      <th>确认新密码：</th>
	      <td><input type="password" name="staff.staff_login_pwd2"/><span class="c_red">*</span></td>
	    </tr>
		<!-- 
		<tr>
		    <td>&nbsp;</td>
		    <td><a href="#" class="btn_orange">修改</a><a href="#" class="btn_blue">取消</a></td>
		</tr>
		 -->  
	  </table>
  </form>
</div>
