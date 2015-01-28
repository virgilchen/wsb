<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
Object has_customer_id = request.getAttribute("has_customer_id");
%>
<div id="productPack_searchViewDiv" style="display:none;">
<script>
g$v<%=view_id%>.productSearchView = $.extend(newView(), {
    view:document.getElementById("productPack_searchViewDiv"), 
    pageIndex : document.getElementById("productPackSO.pageIndex") ,
    
    list:function() {
	    ajax(
	        root + "/biz/productPack_query4Selection.action", 
	        E$("sProductPackForm").serialize(),
	        function(data, textStatus){
	            viewJs.addRows("productPackTB", data.list) ;
	            var $pageSize = E$("productPackSO.pageSize") ;
	            
	            viewJs.lastIndex = Math.ceil(data.total/parseInt($pageSize.val()));
	            viewJs.setPaginationInfo(data.total, data.pageIndex, viewJs.lastIndex, "productSearchView") ;
	        }
	    );
    },
    
    open:function() {
    	showCL('productPack_searchViewDiv');
    	V("productPackSO.customer_id_1", g$v<%=view_id%>.customer_id);
    }
});
</script>


<form method="post" id="sProductPackForm" name="sProductPackForm" onsubmit="return false;" style="margin: 0">
      <input name="productPackSO.pageIndex" id="productPackSO.pageIndex" value="1" type="hidden" />
      <input name="productPackSO.pageSize" id="productPackSO.pageSize" value="10" type="hidden" />
      
      <div class="pop_box" style="width: 800px;height: 550px;top:50px;">
        <div class="pop_title">
            <a href="javascript:closeCL('productPack_searchViewDiv')" class="fr">[关闭]</a>选择商品
        </div>
        <div class="main_list pop_content">
            <div class="pop_search">
                <!-- 
                <select name="">
                  <option selected="selected">商品包名称</option>
                  <option>商品包编号</option><option>销售价格</option><option>所属业务</option>
                </select>
                 -->
                <%if (has_customer_id != null) { %>
                <input type="radio" name="productPackSO.customer_id" id="productPackSO.customer_id_1" checked="checked" value="<%=customer_id%>" style="height: 10px" onchange="javascript:viewJs.productSearchView.list();"/>
                <label for="productPackSO.customer_id_1" style="line-height: 28px">用户已有</label>
                <input type="radio" name="productPackSO.customer_id" id="productPackSO.customer_id_2" value="" style="height: 10px" onchange="javascript:viewJs.productSearchView.list();"/>
                <label for="productPackSO.customer_id_2" style="line-height: 28px">销售</label>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <%} %>
                <lable>商品包名称</lable>
                <input type="text" name="productPackSO.prod_pack_name"/>
                <a href="javascript:viewJs.productSearchView.list();" class="btn_blue">搜索</a>
            </div>
            
            <table width="100%" border="0" id="productPackTB">
                <thead>
	              <tr>
	                <th>商品包编号</th>
	                <th>商品包名称</th>
	                <th>所属业务</th>
	                <th>售价</th>
	                <th>操作</th>
	              </tr>
                </thead>
		        
		        <tbody id="listBody" >
		        </tbody>
              
		         <tbody style="display:none;visibility: false;" disabled="true">
		          <tr>
		            <td>
		              <textarea id="templateBody" jTemplate="yes">
		                  <tr id="{$T.id}" ondblclick="viewJs.selectProdPack({$T.id}, '{$T.prod_pack_name}', {$T.prod_pack_selling_price});closeCL('productPack_searchViewDiv');">
		                    <td><input type="hidden" name="ids" id="ids" value="{$T.id}" />{$T.prod_pack_name}</td>
		                    <td>{$T.prod_pack_name}</td>
		                    <td>{$T.prod_pack_owner}</td>
		                    <td>{$T.prod_pack_selling_price}</td>
		                    <td><a href="javascript:viewJs.selectProdPack({$T.id}, '{$T.prod_pack_name}', {$T.prod_pack_selling_price});closeCL('productPack_searchViewDiv');">选择</a></td>
		                  </tr>
		              </textarea>
		            </td>
		          </tr>
		        </tbody>
            </table>
            
            <%request.setAttribute("pagination_id", "productSearchView"); %>
            <%@include file="/WEB-INF/pages/frame/pagination.jsp" %>
        </div>
     
      </div>
      <div class="pop_bg_500"></div>
</form>
</div>