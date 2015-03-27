<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/staff_list.action" ,
    create_url:root + "/biz/staff_create.action" ,
    update_url:root + "/biz/staff_update.action" ,
    get_url:root + "/biz/staff_get.action" ,
    delete_url:root + "/biz/staff_delete.action" ,
    entityName:"staff",
    eFormInitData:{"staff_status":"A"},
    
    init:function (){
        this.pageIndex = E("staffSO.pageIndex") ;
        
        fillOptions({id:"staff.staff_role_id", dictName:"Role", firstLabel:"请选择...", textProperty:"staff_role_name",titleProperty:"staff_role_name"}) ;// 改为字典取值
        fillOptions({id:"staffSO.staff_role_id", dictName:"Role", firstLabel:"请选择...", textProperty:"staff_role_name",titleProperty:"staff_role_name"}) ;// 改为字典取值
        fillOptions({id:"staff.staff_status", dictName:"staff.status", firstLabel:"请选择..."}) ;
        fillOptions({id:"staff.staff_gender", dictName:"CM.gender", firstLabel:"请选择..."}) ;
        
        this.initDataGrid("staffTB") ;

        E$("eForm").validator();
        E$("sForm").validator();
        E("sForm").setFirstFocus();
        
        this.first();
    },
    
    updateStatus:function(id,status) {

        ajax(
            root + "/biz/staff_updateStatus.action", 
            {"staff.id":id, "staff.staff_status":status},
            function(data, textStatus){
                if (data.code == "0") {
                    viewJs.list() ;
                }
            }
        );
    }, 
    
    checkEditForm:function() {
    	if (V("staff.id") == "" && V("staff.staff_login_pwd") == "") {
    		alert("请输入密码！") ;
            E("staff.staff_login_pwd").focus();
    		return false ;
    	}
    	if(V("staff.staff_login_pwd2") != V("staff.staff_login_pwd")) {
    		alert("密码跟确认密码不一致，请重新输入！");
            V("staff.staff_login_pwd2", "");
            V("staff.staff_login_pwd", "");
            E("staff.staff_login_pwd").focus();
    		return false ;
    	}
    	
    	return true ;
    },
    
    onGet:function(data, event) {
    	V("staff.staff_login_pwd2", "");
    	if (typeof(data.id) == "undefined") {
    		E$("staff.staff_login_profile").removeAttr("readonly");
    	} else {
            E$("staff.staff_login_profile").attr("readonly", "readonly") ;
    	}
    }
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <%--@include file="/WEB-INF/pages/frame/navigation.jsp" --%>
  
  <div id="listDiv">
  
    <DIV class=main_title>
      <B>员工管理</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">新增员工</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
        <!-- 
        <A class=orange href="javascript:viewJs.toDelete();">禁用</A>
         -->
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0 0 5px 0;">
        <input name="staffSO.pageIndex" id="staffSO.pageIndex" value="1" type="hidden" />
        <input name="staffSO.pageSize" id="staffSO.pageSize" value="10" type="hidden" />
        <table width="100%" >
          <tr>
           <td style="width:100px;" >登录账号：</td>
           <td style="width:100px;">
             <input class=mg_r name="staffSO.staff_login_profile" value="" type="text" />
           </td>
           <td style="width:100px;" >角色：</td>
           <td style="width:100px;">
             <select class=mg_r name="staffSO.staff_role_id" id="staffSO.staff_role_id"></select>
             <!-- <input class=mg_r name="staffSO.staff_role_id" value="" type="text" /> -->
           </td>
           <td style="width:100px;" >姓名：</td>
           <td style="width:100px;">
             <input class=mg_r name="staffSO.staff_name" value="" type="text" />
           </td>
           <td style="width:100px;">
             <INPUT class="ipt_btn mg_r" value=搜索 type=button name=""  onclick="viewJs.first();">
           </td>
           <td style="width:1000px;">
           </td>
          </tr>
        </table>
      </form>
    </DIV>
    
    
    <DIV class=main_list>
      <TABLE border=0 width="100%" id="staffTB" title="员工列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
			<TH>编号</TH>
			<TH>登录账号</TH>
			<TH>状态</TH>
			<TH>姓名</TH>
			<TH>性别</TH>
			<TH>员工角色</TH>
			<TH>最后登录时间</TH>
			<TH>操作</TH>
		  <TR>
        </thead>
        
        <tbody id="listBody" >
        </tbody>
         
         
        <tbody style="display:none;visibility: false;" disabled="true">
          <tr>
            <td>
              <textarea id="templateBody" jTemplate="yes">
                  <tr id="{$T.id}" ondblclick="viewJs.toEdit($('#ids', this)[0]);">
                    <td>
                      <input type="checkbox" name="ids" id="ids" value="{$T.id}" />
                    </td>
                    <td>{$T.id}</td>
                    <td>{$T.staff_login_profile}</td>
                    <td>{dVal("staff.status", "name_", {PK_ID:$T.staff_status})}</td>
                    <td>{$T.staff_name}</td>
                    <td>{dVal("CM.gender", "name_", {PK_ID:$T.staff_gender})}</td>
                    <td>{dVal("Role", "staff_role_name", {PK_ID:$T.staff_role_id})}</td>
                    <td>{fmt.maxlen($T.staff_last_login_time, 100)}</td>
                    <td>
                      {#if $T.staff_status == 'I'}
                      <a href="javascript:viewJs.updateStatus({$T.id}, 'A')">激活</a>
                      {#else}
                      <a href="javascript:viewJs.updateStatus({$T.id}, 'I')">禁用</a>
                      {#/if}
                    </td>
                  </tr>
              </textarea>
            </td>
          </tr>
        </tbody>

      </TABLE>
    </DIV>



    <%@include file="/WEB-INF/pages/frame/pagination.jsp" %>
    
  </div>
    
  <div id="editDiv" style="display:none;" >
    
    <DIV class=main_title>
      <B>员工信息编辑</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
    </DIV>


    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="staff.id" id="staff.id"/>
  
      <table width="100%" border="0">
        <tr valign="top">
          <td valign="top" width="15%">
          </td>
          <td width="45%">
            <table width="100%" border="0">
              <tr>
                <th width="25%">登录账号：</th>
                <td>
                 <input type="text" name="staff.staff_login_profile" id="staff.staff_login_profile" maxlength="50"/>
                </td>
              </tr>
              <tr>
                <th width="25%">密码：</th>
                <td>
                 <input type="password" name="staff.staff_login_pwd" id="staff.staff_login_pwd" maxlength="50"/>
                </td>
              </tr>
              <tr>
                <th width="25%">密码确认：</th>
                <td>
                 <input type="password" id="staff.staff_login_pwd2" maxlength="50"/>
                </td>
              </tr>
              <tr>
                <th width="25%">姓名：</th>
                <td>
                 <input type="text" name="staff.staff_name" id="staff.staff_name" maxlength="50"/>
                </td>
              </tr>
              <tr>
                <th width="25%">身份证号：</th>
                <td>
                 <input type="text" name="staff.staff_id_card" id="staff.staff_id_card" maxlength="50"/>
                </td>
              </tr>
              <tr>
                <th width="25%">电话号码：</th>
                <td>
                 <input type="text" name="staff.phone_no" id="staff.phone_no" maxlength="50"/>
                </td>
              </tr>
              <tr>
                <th width="25%">用户角色：</th>
                <td>
                  <select name="staff.staff_role_id" id="staff.staff_role_id"></select>
                  <!-- 
                 <input type="text" name="staff.staff_role_id" id="staff.staff_role_id" maxlength="50"/>
                   -->
                </td>
              </tr>
              <tr>
                <th width="25%">性别：</th>
                <td>
                  <select name="staff.staff_gender" id="staff.staff_gender"></select>
                </td>
              </tr>
              <tr>
                <th width="25%">状态：</th>
                <td>
                  <select name="staff.staff_status" id="staff.staff_status"></select>
                </td>
              </tr>
            </table> 
          </td>
          <td valign="top" width="25%">
          </td>
        </tr>
        <tr height="20"><td colspan="2"></td></tr>
      </table>
    </form>
     <!-- 
    <table cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td colspan="2" align="center" class="tx_center">
            <button onclick="viewJs.save()" tabindex="-1">保存(S)</button>
          </td>
        </tr>
    </table>
    -->
    
    
  </div>
</div>
