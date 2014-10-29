<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/recmdtInventory_list.action" ,
    create_url:root + "/biz/recmdtInventory_create.action" ,
    update_url:root + "/biz/recmdtInventory_update.action" ,
    get_url:root + "/biz/recmdtInventory_get.action" ,
    delete_url:root + "/biz/recmdtInventory_delete.action" ,
    entityName:"recmdtInventory",
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("recmdtInventorySO.pageIndex") ;
        
        fillOptions({id:"recmdtStatusSelection", dictName:"staffRole.status", firstLabel:"不限"}) ;// 改为字典取值
        fillOptions({id:"statusSelection", dictName:"staffRole.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"noticeSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("noticeTB", {height:"400px"}) ;
        
        //E$("notice.notice_timestamp").datetimepicker({
          //  timeFormat: "HH:mm:ss"
        //});

        
        E$("eForm").validator();
        E$("sForm").validator();
        E("sForm").setFirstFocus();
        
        this.first();
    }
}) ;

</script>


<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <div id="listDiv">
  
    <DIV class=main_title>
      <B>决策信息管理</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0 0 5px 0;">
        <input name="recmdtInventorySO.pageIndex" id="recmdtInventorySO.pageIndex" value="1" type="hidden" />
        <input name="recmdtInventorySO.pageSize" id="recmdtInventorySO.pageSize" value="10" type="hidden" />
        <table width="100%" >
          <tr>
           <td style="width:80px;" >决策名称：</td>
           <td style="width:100px;">
             <input class=mg_r name="recmdtInventorySO。recmdt_name" value="" type="text" />
           </td>
           <td style="width:80px;" >当前状态：</td>
           <td style="width:100px;">
             <select name="recmdtInventorySO.recmdt_status" id="recmdtStatusSelection"></select>
           </td>
           <td style="width:100px;">
             <INPUT class="ipt_btn mg_r" value=搜索 type=button name=""  onclick="viewJs.first();">
           </td>
           <td style="width:600px;">
           </td>
          </tr>
        </table>
      </form>
    </DIV>
    
    
    <DIV class=main_list>
      <TABLE border=0 width="100%" id="recmdtInventoryTB" title="决策列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
			<TH>决策编码</TH>
			<TH>决策名称</TH>
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
                    <td>{$T.recmdt_code}</td>
                    <td>{$T.recmdt_name}</td>
                    <td>{dVal("staffRole.status", "name_", {PK_ID:$T.recmdt_status})}</td>
                    <td>{$T.recmdt_remark}</td>
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
      <B>添加决策信息</B> 
      
      <%-- 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
      --%>
    </DIV>
    
    <div class="step_01">
	<span>第1步</span><span>第2步</span>
    </div>
    

    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="recmdtInventory.id" id="recmdtInventory.id"/>
      <input type="hidden" name="recmdtInventory.version_id" id="recmdtInventory.version_id"/>
  
      <table width="100%" border="0">
        <tr valign="top">
          <td valign="top" width="15%">
          </td>
          <td width="45%">
            <table width="100%" border="0">
              <tr>
                <th>决策编码：</th>
                <td><input type="text" name="recmdtInventory.recmdt_code" maxlength="50"  required="required"/></td>
              </tr>
              <tr>
                <th>决策名称：</th>
                <td><input type="text" name="recmdtInventory.recmdt_name" maxlength="50"  required="required"/></td>
              </tr>
              <tr>
                <th>决策备注：</th>
                <td>
                  <textarea name="recmdtInventory.recmdt_remark"  style="width: 100%;height: 80px;"></textarea>
                </td>
              </tr>
              <tr>
                <th>状态：</th>
                <td>
                  <select name="recmdtInventory.recmdt_status" id="statusSelection"></select>
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