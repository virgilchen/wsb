<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/order_list.action" ,
    create_url:root + "/biz/order_save.action" ,
    update_url:root + "/biz/order_save.action" ,
    get_url:root + "/biz/order_get.action" ,
    delete_url:root + "/biz/order_delete.action" ,
    entityName:"order",
    size:0,
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("orderSO.pageIndex") ;
        
        //fillOptions({id:"order.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"orderSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("orderTB", {height:"400px"}) ;
        /*
        E$("order.order_timestamp").datetimepicker({
            timeFormat: "HH:mm:ss"
        });
        */
        E$("eForm").validator();
        //E$("sForm").validator();
        E("eForm").setFirstFocus();
        this.size = 0;
        this.getOpenInfo();
    },
    
    getOpenInfo:function() {
        var params = {} ;
        params.customer_id = 1 ;
        ajax(
        	root+"/biz/order_getOpenInfo.action", 
            params,
            function(data, textStatus){
                viewJs.entity = data;
                formDeserializeText("customerInfoDiv", "label", data.customer, {}) ;
                formDeserialize("eForm", data, {}) ;
            }
        );
    },
    
    onSaveOk:function(data) {
    	//alert(data.id);
    	V("order.id", data.id)
    },
    
    add:function() {
        this.addRows ("orderProdPacksTB", [{index:this.size, textarea_tagName:"textarea" }], {deep:1});
        this.size ++;
        this.refreshTableList();
    },
    
    refreshTableList:function() {
        var $packageIndexNames = $("#orderProdPacksTB #listBody #packageIndexName", viewJs.view); 

        $packageIndexNames.each(function(i, elem) {
            $(elem).html("商品包" + (i + 1));
        });
    },
    
    open:function () {
        var frm = E("eForm") ;
        if(!frm.checkValidity()) {
            alert("请正确填写表单！") ;
            if (typeof(frm.setErrorFocus) != "undefined") {
                frm.setErrorFocus() ;
            }
            return ;
        }
        
        if (!this.checkEditForm(frm)) {
            return ;
        }
        
        if (!window.confirm("是否确定要保存？")) {
            return ;
        }
        
        ajax(
        	root + "/biz/order_open.action", 
            E$("eForm").serialize(),
            function(data, textStatus){
                if (data.code == "0") {
                	removeView(<%=view_id%>);
                }
            }
        );
    }
    
    
    
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">
    <div id="customerInfoDiv" >
		<div class="main_title">
		    <b>发起“<label id="customer.cust_name">王小明</label>”的业务单</b>
		    <div class="main_tt_right fr">
		        <a href="javascript:viewJs.open();" class="orange">保存并发起业务</a>
		        <a href="javascript:viewJs.save();" class="orange">保存</a>
		        <a href="#" class="blue">返回</a>
		    </div>
		</div>

		<%@include file="/WEB-INF/pages/biz/order/customerInfo.jsp" %>
		
	</div>
	 
	<div class="order_launched">
	  <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" >
        <input type="hidden" name="order.id" id="order.id"/>
        <input type="hidden" name="order.version_id" id="order.version_id"/>
        <input type="hidden" name="order.psdo_cust_id" id="order.psdo_cust_id"/>
        
	    <table width="100%" border="0">
	      <tr>
            <th>业务单单号：</th>
            <td><input type="text"  name="order.idxx"/><span class="c_red">*</span></td>
            <th>业务单发起时间：</th>
            <td><input type="text"  name="order.order_init_time_stamp" /><span class="c_red">*</span></td>
            <th>业务处理人：</th>
            <td><input type="text"  name="order.order_init_staff_id"/> <a href="" class="link_blue">选择</a></td>
          </tr>
          <tr>
            <th>业务单备注：</th>
            <td colspan="5"><textarea  name="order.order_remark" cols="55" rows="3"></textarea></td>
          </tr>
	    </table>

        <table width="100%" border="0" id="orderProdPacksTB">
	      <tbody id="listBody">
	      </tbody>
	      

	      <tbody style="display:none;visibility: false;" disabled="true">
	          <tr>
	            <td>
	              <textarea id="templateBody" jTemplate="yes">
            
			        <div class="goods_bag clearfix">
			            <div id="packageIndexName">商品包1</div>
			            <table width="80%" border="0" class="fl">
			              <tr>
			                <th width="15%">商品包名称：</th>
			                <td width="25%">
                              <input type="hidden" name="orderProdPacks[{$T.index}].id" value="{$T.index}" /> 
                              <input type="text" name="orderProdPacks[{$T.index}].prod_pack_id" value="车险A套餐(0192847)" /> 
			                  <a href="javascript:showCL('test_condition')" class="link_blue">选择</a>
			                  <span class="c_red"></span>
			                </td>
			                <th width="10%">数量：</th>
			                <td width="25%">
			                  <input type="text" name="orderProdPacks[{$T.index}].no_of_order_prod_pack" style=" width:50px;" /> 份<span class="c_red">*</span>
			                </td>
			                <td width="25%"></td>
			              </tr>
			              <tr>
			                <th>业务类型：</th>
			                <td><input type="text" name="orderProdPacks[{$T.index}].order_prod_pack_idxx" disabled="disabled" value="车险,意外险" /></td>
			              </tr>
			              <tr>
			                <th>购买日期：</th>
			                <td>
			                  <input type="text" name="orderProdPacks[{$T.index}].order_prod_pack_purchase_date" class="ipt_date" /><span class="c_red">*</span>
			                </td>
			                <th>起效日期：</th>
			                <td>
			                  <input type="text" name="orderProdPacks[{$T.index}].order_prod_pack_effect_date" class="ipt_date" /><span class="c_red">*</span>
			                </td>
                            <td></td>
			              </tr>
			              <tr>
			                <th>备注：</th>
			                <td colspan="3"><{$T.textarea_tagName}  name="orderProdPacks[{$T.index}].order_prod_pack_remark" cols="55" rows="3"></{$T.textarea_tagName}></td>
                            <td></td>
			              </tr>
			          
			            </table>
			        </div>
	              </textarea>
	            </td>
	          </tr>
          </tbody>
        </table>
        
        <table>
          <tr>
            <td>
              <a href="javascript:viewJs.add();" class="link_blue">+新增商品包</a>
            </td>
          </tr>
        </table>
	    
	  </form>
	</div>
	
	<a href="#" class="btn_orange mg_r">保存并发起业务</a><a href="#" class="btn_orange">保存</a><a href="#" class="btn_blue">返回</a>
	 
	 
	 
	 
	 
	 
	<div id="test_condition" style="display:none;">
	  <div class="pop_box">
	    <div class="pop_title"><a href="javascript:closeCL('test_condition')" class="fr">[关闭]</a>选择商品</div>
	        <div class="main_list pop_content">
	            <div class="pop_search">
	            <select name="">
	                  <option selected="selected">商品包名称</option>
	                  <option>商品包编号</option><option>销售价格</option><option>所属业务</option>
	                </select>
	                <input type="text" /><a href="#" class="btn_blue">搜索</a>
	            </div>
	            <table width="100%" border="0">
	              <tr>
	                <th><input name="" type="checkbox" value=""> 商品包编号</th>
	                <th>商品包名称</th>
	                <th>所属业务</th>
	                <th>售价</th>
	                <th>操作</th>
	              </tr>
	              <tr>
	                <td><input name="" type="checkbox" value=""> yw1001_1_p001</td>
	                <td>车险A餐</td>
	                <td>车险</td>
	                <td>￥1,999.00</td>
	                <td><a href="#">查看</a> | <a href="#">选择</a></td>
	              </tr>
	              <tr>
	                <td><input name="" type="checkbox" value=""> yw1001_1_p002</td>
	                <td>车险A餐</td>
	                <td>车险</td>
	                <td>￥1,999.00</td>
	                <td><a href="#">查看</a> | <a href="#">选择</a></td>
	              </tr>
	              <tr>
	                <td><input name="" type="checkbox" value=""> yw1001_1_p003</td>
	                <td>轮胎</td>
	                <td>配件</td>
	                <td>￥899.00</td>
	                <td><a href="#">查看</a> | <a href="#">选择</a></td>
	              </tr>
	            </table>
	            <div class="page_wrap clearfix">
	                <div class="paginator">
	                    <span class="page-start">＜上一页</span>
	                    <span class="page-this">1</span>
	                    <a href="#">2</a>
	                    <a href="#">3</a>
	                    <a href="#">4</a>
	                    <a href="#">5</a>
	                    <a href="#">6</a>
	                    <a href="#">7</a>
	                    <a href="#">8</a>
	                    <span>...</span>
	                    <a href="#">20</a>
	                    <a href="#" class="page-next">下一页＞</a>
	                </div>
	                第1/3页，共30条记录
	            </div>
	        </div>
	 
	  </div>
	  <div class="pop_bg_500"></div>
	</div>

</div>
