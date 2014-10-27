<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/staffRole_list.action" ,
    create_url:root + "/biz/staffRole_create.action" ,
    update_url:root + "/biz/staffRole_update.action" ,
    get_url:root + "/biz/staffRole_get.action" ,
    delete_url:root + "/biz/staffRole_delete.action" ,
    entityName:"staffRole",
    
    init:function (){
        this.pageIndex = E("staffRoleSO.pageIndex") ;
        
        fillOptions({id:"staffRole.staff_role_status", dictName:"staffRole.status", firstLabel:"不限"}) ;// 改为字典取值
        fillOptions({id:"statusSelection", dictName:"staffRole.status", firstLabel:"请选择..."}) ;// 改为字典取值

        this.initDataGrid("staffRoleTB") ;
        this.initDataGrid("pageTB") ;

        
        var orgJson = <%=request.getAttribute("orgJson")%>;
        
        E$("orgSelection").combobox2({id:"orgSelection", 
            data:orgJson, 
            firstLabel:"请选择", 
            valueProperty:"id", 
            idProperty:"id", 
            textProperty:["org_name"], 
            titleProperty:"org_name"
        });
        
        
        E$("eForm").validator();
        E$("sForm").validator();
        E("sForm").setFirstFocus();
        
        this.first();
    },
    
    get:function(id) {
        ajax(
            this.get_url + "?id="+id, 
            null,
            function(data, textStatus){
                
                formDeserialize("eForm", data, {page_ids:true}) ;//以免处理template中的check-box value值

                $(data.rolePages).each(function(i, domEle){
                    domEle['checked'] = (domEle['staff_role_id'] == 0 || domEle['staff_role_id'] == null)? "":"checked" ;
                });
                
                viewJs.addRows("pageTB", data.rolePages) ;
                E$("eForm").validator();//为了初始化check box
            }
        );
    }
}) ;

</script>

<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

    <style>
    .ui-combobox-toggle {
    right: -1px;
    }
    
    .ui-combobox input {
    width: 96%;
    }
    .main_form table td span {
    padding-left: 0px;
    }
    </style>
    
  <div id="listDiv">
  
    <DIV class=main_title>
      <B>角色列表</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0 0 5px 0;">
        <input name="staffRoleSO.pageIndex" id="staffRoleSO.pageIndex" value="1" type="hidden" />
        <input name="staffRoleSO.pageSize" id="staffRoleSO.pageSize" value="10" type="hidden" />
        <table width="100%" >
          <tr>
           <!-- 
           <td style="width:60px;" >角色编码：</td>
           <td style="width:100px;">
             <input class=mg_r name="staffRoleSO.ids" value="" type="text" />
           </td>
           -->
           <td style="width:100px;" >角色名称：</td>
           <td style="width:100px;">
             <input class=mg_r name="staffRoleSO.staff_role_name" value="" type="text" />
           </td>
           <td style="width:100px;" >当前状态：</td>
           <td style="width:100px;">
             <select name="staffRoleSO.staff_role_status" id="staffRole.staff_role_status"></select>
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
      <TABLE border=0 width="100%" id="staffRoleTB" title="角色列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
			<TH>角色编码</TH>
			<TH>角色名称</TH>
			<TH>状态</TH>
			<TH>备注</TH>
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
                    <td>{$T.staff_role_name}</td>
                    <td>{dVal("staffRole.status", "name_", {PK_ID:$T.staff_role_status})}</td>
                    <td>{$T.staff_role_remark}</td>
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
      <B>角色编辑</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
    </DIV>


    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="staffRole.id" id="staffRole.id"/>
      <input type="hidden" name="staffRole.version_id" id="staffRole.version_id"/>
  
      <table width="100%" border="0">
        <tr valign="top">
          <td valign="top" width="10%">
          </td>
          <td width="40%">
            <table width="100%" border="0">
              <tr>
                <th>角色名称：</th>
                <td><input type="text" name="staffRole.staff_role_name" maxlength="50"  required="required"/></td>
              </tr>
              <tr>
                <th>备注：</th>
                <td>
                  <textarea name="staffRole.staff_role_remark"  style="width: 100%;height: 80px;"></textarea>
                </td>
              </tr>
              <tr>
                <th>机构设置：</th>
                <td>
                  <input id="orgSelection" name="staffRole.staff_role_org_id"/>
                </td>
              </tr>
              <tr>
                <th>状态：</th>
                <td>
                  <select name="staffRole.staff_role_status" id="statusSelection"></select>
                </td>
              </tr>

            </table> 
          </td>
          <td valign="top" width="5%">
          
          </td>
          <td valign="top" width="40%" class="main_list">
            <div style="height: 100%;width: 100%;overflow: auto;">
              <table id="pageTB" class="datagrid" style="width: 100%;">
                <thead>
                  <tr>
                    <th width="10%"></th>
                    <th width="90%" style="text-align: left;"><b>功能菜单列表</b></th>
                  </tr>
                </thead>
                <tbody id="listBody" >
                </tbody>
                 
                <tbody style="display:none;visibility: false;" disabled="true">
                  <tr>
                    <td>
                      <textarea id="templateBody" jTemplate="yes">
		                  <tr>
		                    <td style="padding:0;">
		                      <input type="checkbox" name="page_ids" value="{$T.page_id}" {$T.checked} onfocus="$(this).addClass('ui-state-focus');" onblur="$(this).removeClass('ui-state-focus');" style="width:18px;" />
		                    </td>
		                    <td style="text-align: left;padding:0;">{$T.page_name}</td>
		                  </tr>
                      </textarea>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </td>
          
          <td valign="top" width="5%">
          
          </td>
        </tr>
        <tr height="20"><td colspan="2"></td></tr>
      </table>
    </form>

    
    
  </div>


</div>
