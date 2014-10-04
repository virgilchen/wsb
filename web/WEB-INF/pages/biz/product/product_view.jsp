<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head_session_check.jsp" %>
<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/product_list.action" ,
    create_url:root + "/biz/product_create.action" ,
    update_url:root + "/biz/product_update.action" ,
    get_url:root + "/biz/product_get.action" ,
    delete_url:root + "/biz/product_delete.action" ,
    entityName:"product",
    eFormInitData:{},
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("productSO.pageIndex") ;
        
        fillOptions({id:"product.prod_unit", dictName:"product.unit", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"productSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("productTB", {height:"400px"}) ;
        
        E$("product.product_timestamp").datetimepicker({
            timeFormat: "HH:mm:ss"
        });
        
        E$("eForm").validator();
        E$("sForm").validator();
        E("sForm").setFirstFocus();
        
        this.listBusiness();
        
    },
    
    listBusiness:function() {
        ajax(
        	root+"/biz/business_list.action?businessSO.pageIndex=-2",//PAGEINDEX_NO_PAGE 
            null,
            function(data, textStatus){
                viewJs.addRows("businessTB", data.list) ;
            }
        );
    },
    
    toListProduct:function(business_id) {
        V("productSO.business_id", business_id);
        
        this.eFormInitData["business_id"] = business_id;// 无需要带product.前辍
        
    	this.list();
    }
    
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <%--@include file="/WEB-INF/pages/frame/navigation.jsp" --%>
  
  <div id="listDiv">
    <table style="width: 100%;height: 100%;">
      <tr>
        <!-- biz selection -->
        <td width="20%" valign="top">
          <div class="main_bothside" style="width: 100%;height: 100%;">
            <div class="both_left fl" style="width: 100%;height: 100%;">
              <div class="title">业务分类</div>
              <div class="content">
	              <TABLE border=0 width="100%" id="businessTB">
                
	                <tbody id="listBody" >
	                </tbody>
	                 
	                 
	                <tbody style="display:none;visibility: false;" disabled="true">
	                  <tr>
	                    <td>
	                      <textarea id="templateBody" jTemplate="yes">
			                  <tr id="{$T.id}" onclick="viewJs.toListProduct({$T.id});">
			                    <td style="text-align: left;">{$T.business_name}</td>
			                  </tr>
	                      </textarea>
	                    </td>
	                  </tr>
	                </tbody>
	        
	              </TABLE>
	              
              </div>
            </div>
          </div>
        </td>
        
        <td width="1px"></td>
        
        <!-- list of products -->
        <td valign="top">
		    <DIV class=main_title>
		      <B>基础商品列表</B> 
		      <DIV class="main_tt_right fr">
		        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
		        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
		        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
		      </DIV>
		    </DIV>
		  
		    <DIV class="main_search">
		      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0">
		        <input name="productSO.pageIndex" id="productSO.pageIndex" value="1" type="hidden" />
                <input name="productSO.pageSize" id="productSO.pageSize" value="10" type="hidden" />
                
                <input name="productSO.business_id" id="productSO.business_id" value="0" type="hidden" />
                
		        <table width="100%" >
		          <tr>
		           <td style="width:100px;" >商品名称：</td>
		           <td style="width:100px;">
		             <input class=mg_r name="productSO.prod_name" value="" type="text" />
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
		      <TABLE border=0 width="100%" id="productTB" title="商品列表">
		        <thead>
		          <TR>
		            <TH width="20px"></TH>
		            <TH>商品名称</TH>
                    <TH>商品描述</TH>
                    <TH width="80px">单位</TH>
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
		                    <td>{$T.prod_name}</td>
                            <td>{$T.prod_desc}</td>
                            <td>{dVal("product.unit", "name_", {PK_ID:$T.prod_unit})}</td>
		                  </tr>
		              </textarea>
		            </td>
		          </tr>
		        </tbody>
		
		      </TABLE>
		    </DIV>
		
		
		
		    <%@include file="/WEB-INF/pages/frame/pagination.jsp" %>
        </td>
      </tr>
    </table>
    
  </div>
    
  <div id="editDiv" style="display:none;" >
    
    <DIV class=main_title>
      <B>新增基础商品</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
    </DIV>


    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="product.id" id="product.id"/>
      <input type="hidden" name="product.version_id" id="product.version_id"/>
      <input type="hidden" name="product.business_id" id="product.business_id"/>
  
      <table width="100%" border="0">
        <tr valign="top">
          <td valign="top" width="15%">
          </td>
          <td width="45%">
            <table width="100%" border="0">
              <!-- 
              <tr>
                <th width="25%">公告编号：</th>
                <td><input type="text" name="product.name_cn" id="product.name_cn" maxlength="50"/></td>
              </tr>
               -->
              <tr>
                <th>商品名称：</th>
                <td><input type="text" name="product.prod_name" maxlength="50"  required="required"/></td>
              </tr>
              <tr>
                <th>描述：</th>
                <td>
                  <textarea name="product.prod_desc"  style="width: 100%;height: 80px;"></textarea>
                </td>
              </tr>
              <tr>
                <th>描述：</th>
                <td>
                  <select name="product.prod_unit" id="product.prod_unit">
			      </select>
                </td>
              </tr>
              
              
              
              <!-- 
              <tr>
                <th>发表时间：</th>
                <td><input type="text" id="product.product_timestamp" name="product.product_timestamp" required="required" maxlength="19"/></td>
              </tr>
               -->
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
