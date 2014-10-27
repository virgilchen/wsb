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
    proIdName:"org_id_upper", 
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("companyOrgSO.pageIndex") ;
        
        //fillOptions({id:"notice.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"noticeSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        this.initDataGrid("companyOrgTB") ;

        
        E$("eForm").validator();
        //E$("sForm").validator();
        //E("sForm").setFirstFocus();
        this.first();
    },
    
    get:function(id) {

        var orgJson = g$dict.Org;
        if (typeof(orgJson) == "undefined") {
        	orgJson = [];
        }
        orgJson = $.merge([{PK_ID:0, org_name:'[无上级机构]'}], orgJson);
         
        var optionParams = {id:"companyOrg.org_id_upper", 
                data:orgJson, 
                //firstLabel:"[无上级机构]", 
                valueProperty:"PK_ID", 
                idProperty:"PK_ID", 
                textProperty:["org_name"], 
                titleProperty:"org_name"
            } ;
        
        E("companyOrg.org_id_upper").isEdit = (id != -1);
        
        if (id == -1) {
            //fillOptions(optionParams) ;
            E$("companyOrg.org_id_upper").combobox2(optionParams) ;
            formDeserialize("eForm", this.eFormInitData, {}) ;// reset form;
            return ;
        }
        
        ajax(
            this.get_url + "?id="+id, 
            null,
            function(data, textStatus){
                if (data.org_id_upper != null && data.org_id_upper > 0) {
                    delete optionParams["firstLabel"] ;
                }
                //var filterValues = {} ;
                //filterValues[data.business_id_upper] = true ;
                //optionParams["filter"] = filterValues ;
                optionParams.data = filter(optionParams.data, {PK_ID:data.org_id_upper});
                //fillOptions(optionParams) ;
                E$("companyOrg.org_id_upper").combobox2(optionParams) ;
                
                formDeserialize("eForm", data, {}) ;
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
      <B>组织架构管理</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search" style="display: none;">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0">
        <input name="companyOrgSO.pageIndex" id="companyOrgSO.pageIndex" value="1" type="hidden" />
        <input name="companyOrgSO.pageSize" id="companyOrgSO.pageSize" value="250" type="hidden" />
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
      <TABLE border=0 width="100%" id="companyOrgTB" title="机构列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
			<TH>机构名称</TH>
			<TH>机构编码</TH>
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
                    <td>
                      <input type="checkbox" name="ids" id="ids" value="{$T.id}" />
                    </td>
                    <td style="text-align: left;padding-left: {$T.org_level * 20 + 5}px;">{$T.org_name}</td>
                    <td>{fmt.maxlen($T.org_code,100)}</td>
                    <td>{fmt.maxlen($T.org_owner_staff,100)}</td>
                    <td>{fmt.maxlen($T.org_owner_phone_no,100)}</td>
                    <td>{fmt.maxlen($T.org_city,100)}</td>
                  </tr>
              </textarea>
            </td>
          </tr>
        </tbody>

      </TABLE>
    </DIV>

    <%--@include file="/WEB-INF/pages/frame/pagination.jsp" --%>
    
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
          <td width="65%">
            <table width="100%" border="0">
              <tr>
                <th width="20%">上级机构：</th>
                <td class="ui-combobox-td"><input id="companyOrg.org_id_upper" name="companyOrg.org_id_upper"/></td>
              </tr>
              <tr>
                <th width="20%">机构名称：</th>
                <td width="30%"><input type="text" name="companyOrg.org_name" maxlength="50"/></td>
                <th width="20%">机构编码：</th>
                <td width="30%"><input type="text" name="companyOrg.org_code" maxlength="50"/></td>
              </tr>
              <tr>
                <th>负责人：</th>
                <td><input type="text" name="companyOrg.org_owner_staff" maxlength="50"/></td>
                <th>联系电话：</th>
                <td><input type="text" name="companyOrg.org_owner_phone_no" maxlength="50"/></td>
              </tr>
              <tr>
                <th>所在省份：</th>
                <td><input type="text" name="companyOrg.org_province" maxlength="50"/></td>
				<th>城市：</th>
				<td><input type="text" name="companyOrg.org_city" maxlength="50"/></td>
              </tr>
              <tr>
              	<th>备注：</th>
              	<td colspan="3">
                  <textarea name="companyOrg.org_remark" style="width: 100%;height: 80px;"></textarea>
                </td>
              </tr>
            </table> 
          </td>
          <td valign="top" width="20%">
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