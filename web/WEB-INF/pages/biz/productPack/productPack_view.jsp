<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/productPack_list.action" ,
    create_url:root + "/biz/productPack_create.action" ,
    update_url:root + "/biz/productPack_update.action" ,
    get_url:root + "/biz/productPack_get.action" ,
    delete_url:root + "/biz/productPack_delete.action" ,
    entityName:"productPack",
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("productPackSO.pageIndex") ;
        
        //fillOptions({id:"productPack.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"productPackSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("productPackTB", {height:"400px"}) ;
        
        E$("productPack.productPack_timestamp").datetimepicker({
            timeFormat: "HH:mm:ss"
        });
        
        E$("eForm").validator();
        E$("sForm").validator();
        E("sForm").setFirstFocus();
        
    }
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">
  
  <div id="listDiv">
  
    <DIV class=main_title>
      <B>商品包列表</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0">
        <input name="productPackSO.pageIndex" id="productPackSO.pageIndex" value="1" type="hidden" />
        <input name="productPackSO.pageSize" id="productPackSO.pageSize" value="10" type="hidden" />
        <table width="100%" >
          <tr>
           <td style="width:100px;" >商品包名称：</td>
           <td style="width:100px;">
             <input class=mg_r name="productPackSO.prod_pack_name" value="" type="text" />
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
      <TABLE border=0 width="100%" id="productPackTB" title="商品包列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
            <TH>商品包编号</TH>
            <TH>商品包名称</TH>
            <TH>商品归属</TH>
            <TH>售价</TH>
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
                    <td>{$T.prod_pack_name}</td>
                    <td>{$T.prod_pack_owner}</td>
                    <td>{$T.prod_pack_selling_price}</td>
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
      <B>商品包公告</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
    </DIV>


    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="productPack.id" id="productPack.id"/>
      <input type="hidden" name="productPack.version_id" id="productPack.version_id"/>
  
      <table width="100%" border="0">
        <tr valign="top">
          <td valign="top" width="15%">
          </td>
          <td width="45%">
            <table width="100%" border="0">
              <tr>
                <th width="25%">商品包名称：</th>
                <td><input type="text" name="productPack.prod_pack_name" id="productPack.prod_pack_name" maxlength="50"/></td>
              </tr>
              <tr>
                <th>商品包归属：</th>
                <td><input type="text" name="productPack.prod_pack_owner" maxlength="50"/></td>
              </tr>
              <tr>
                <th>选择基础商品：</th>
                <td><input type="text" name="productPack.prod_pack_owner" maxlength="50"/></td>
              </tr>
              <tr>
                <th>原始价格：</th>
                <td><input type="text" name="productPack.prod_pack_orignal_price" required="required" maxlength="19"/></td>
              </tr>
              <tr>
                <th>原始价格：</th>
                <td><input type="text" name="productPack.prod_pack_selling_price" required="required" maxlength="19"/></td>
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
