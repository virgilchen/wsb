<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/companyOrg_list.action" ,
    create_url:root + "/biz/companyOrg_create.action" ,
    update_url:root + "/biz/companyOrg_update.action" ,
    get_url:root + "/biz/companyOrg_get.action" ,
    delete_url:root + "/biz/companyOrg_delete.action" ,
    entityName:"companyOrg",
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("companyOrgSO.pageIndex") ;
        
        //fillOptions({id:"notice.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"noticeSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("noticeTB", {height:"400px"}) ;
        
        //E$("notice.notice_timestamp").datetimepicker({
          //  timeFormat: "HH:mm:ss"
        //});
        
        E$("eForm").validator();
        E$("sForm").validator();
        E("sForm").setFirstFocus();
        
    }
}) ;


</script>

<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <%--@include file="/WEB-INF/pages/frame/navigation.jsp" --%>
  
  
    <div id="listDiv">
  
    <DIV class=main_title>
      <B>组织架构管理</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0">
        <input name="companyOrgSO.pageIndex" id="companyOrgSO.pageIndex" value="1" type="hidden" />
        <input name="companyOrgSO.pageSize" id="companyOrgSO.pageSize" value="10" type="hidden" />
        <%-- <table width="100%" >
          <tr>
           <td style="width:60px;" >标题：</td>
           <td style="width:100px;">
             <input class=mg_r name="noticeSO.notice_subject" value="" type="text" />
           </td>
           <td style="width:100px;">
             <INPUT class="ipt_btn mg_r" value=搜索 type=button name=""  onclick="viewJs.first();">
           </td>
           <td style="width:1000px;">
           </td>
          </tr>
        </table> --%>
      </form>
    </DIV>
    
    
    <DIV class=main_list>
      <TABLE border=0 width="100%" id="noticeTB" title="组织列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
			<TH>机构名称</TH>
			<TH>机构ID</TH>
			<TH>负责人</TH>
			<TH>联系电话</TH>
			<TH>所在城市</TH>
		  <TR>
        </thead>
        
        <tbody id="listBody" >
        </tbody>
         
         
        <tbody style="display:none;visibility: false;" disabled="true">
          <tr>
            <td>
              <textarea id="templateBody" jTemplate="yes">
                  <tr id="{$T.id}" ondblclick="viewJs.toEdit($('#ids', this)[0]);">
                    <td>{$T.org_name}</td>
                    <td>{$T.org_id}</td>
                    <td>{$T.org_owner_staff_id}</td>
                    <td>{$T.org_owner_phone_no}</td>
                    <td>{$T.org_city}</td>
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
      <B>添加机构</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
    </DIV>


    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="companyOrg.id" id="companyOrg.id"/>
  
      <table width="100%" border="0">
        <tr valign="top">
          <td valign="top" width="15%">
          </td>
          <td width="45%">
            <table width="100%" border="0">
              <tr>
                <th width="25%">上级机构：</th>
                <td><input type="text" name="companyOrg.org_id_upper" id="companyOrg.org_id_upper" maxlength="50"/></td>
              </tr>
              <tr>
                <th>机构名称：</th>
                <td><input type="text" name="companyOrg.org_name" maxlength="50"/></td>
                <th>机构ID：</th>
                <td><input type="text" name="companyOrg.org_id" maxlength="50"/></td>
              </tr>
              <tr>
                <th>负责人：</th>
                <td><input type="text" name="companyOrg.org_owner_staff_id" maxlength="50"/></td>
                <th>联系电话：</th>
                <td><input type="text" name="companyOrg.org_owner_phone_no" maxlength="50"/></td>
              </tr>
              <tr>
                <th>所在城市：</th>
                <td><select name="companyOrg.org_province">
						<option>省份</option>
						<option>广东省</option>
					</select>
				</td>
				<td><select name="companyOrg.org_city">
						<option>城市</option>
						<option>广州</option>
						<option>深圳</option>
						<option>珠海</option>
					</select>
				</td>
              </tr>
              <tr>
              	<th>备注：</th>
              	<td>
                  <textarea name="companyOrg.org_remark" style="width: 100%;height: 80px;"></textarea>
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