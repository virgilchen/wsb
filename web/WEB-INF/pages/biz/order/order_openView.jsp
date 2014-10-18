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
    staffsJson:<%=request.getAttribute("staffsJson")%>,
    
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
        
        
        /*
        E$("order.order_init_staff_id").combobox2({id:"order.order_init_staff_id", 
            data:staffsJson, 
            firstLabel:"请选择...", 
            valueProperty:"id", 
            idProperty:"id", 
            textProperty:["staff_name"], 
            titleProperty:"staff_name"
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

        E$("purchase_date" + this.size).datepicker();
        E$("effect_date" + this.size).datepicker();
        
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
    },
    
    selectProdPack:function(id, name) {
        E$("prod_pack_id" + this.selectedIndex).val(id);
        E$("prod_pack_name" + this.selectedIndex).val(name);

        var _this = this;
        
        ajax(
            root + "/biz/productPack_queryBusinesses.action", 
            {"productPack.id":id},
            function(data, textStatus){
                //viewJs.addRows("businessTB", data.list) ;
                var $content = $("#businessTB #listBody", viewJs.view);
                $content.html("");
                $(data.list).each(function (i, elem) {
                	elem.index = _this.selectedIndex;
                	$content.append(parse(V("businessTemplateBody"), elem));

                    E$("event_staff_ids_" + elem.index + "_" + elem.id).combobox2({
                    	id:"event_staff_ids_" + elem.index + "_" + elem.id, 
                        data:_this.staffsJson, 
                        firstLabel:"请选择...", 
                        valueProperty:"id", 
                        idProperty:"id", 
                        textProperty:["staff_name"], 
                        titleProperty:"staff_name"
                    });
                });
            }
        );
    },
    
    openProductSearchView:function(index) {
    	this.selectedIndex = index ;
    	this.productSearchView.open();
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
            <td><input type="text"  name="order.order_no" readonly="readonly"/><span class="c_red">*</span></td>
            <th>业务单发起时间：</th>
            <td><input type="text"  name="order.order_init_time_stamp" readonly="readonly" /><span class="c_red">*</span></td>
            <th>业务处理人：</th>
            <td><input type="text"  name="order.order_init_staff_id" id="order.order_init_staff_id"/> <!-- a href="" class="link_blue">选择</a --></td>
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
			                <td width="35%">
                              <input type="hidden" name="orderProdPacks[{$T.index}].id" value="{$T.index}" /> 
                              <input type="hidden" name="orderProdPacks[{$T.index}].prod_pack_id" id="prod_pack_id{$T.index}" value="" /> 
                              <input type="text" name="orderProdPacks[{$T.index}].prod_pack_name" id="prod_pack_name{$T.index}" value="" readonly="readonly"/> 
			                  <a href="javascript:viewJs.openProductSearchView({$T.index});" class="link_blue">选择</a>
			                  <span class="c_red"></span>
			                </td>
			                <th width="10%">数量：</th>
			                <td width="25%">
			                  <input type="text" name="orderProdPacks[{$T.index}].no_of_order_prod_pack" style=" width:50px;" /> 份<span class="c_red">*</span>
			                </td>
			                <td width="15%"></td>
			              </tr>
			              <tr>
			                <th>业务类型：</th>
			                <td colspan="4" >
			                    <!-- 
			                    <input type="text" name="orderProdPacks[{$T.index}].order_prod_pack_idxx" disabled="disabled" value="车险,意外险" />
			                     -->
				                <table width="100%" border="0" id="businessTB">
				                  <thead>
					                  <tr>
					                    <th style="text-align: center;">业务类型</th>
					                    <th style="text-align: center;">业务处理人</th>
					                  </tr>
				                  </thead>
        
							      <tbody id="listBody" >
							      </tbody>
				                </table>
			                </td>
			              </tr>
			              <tr>
			                <th>购买日期：</th>
			                <td>
			                  <input type="text" name="orderProdPacks[{$T.index}].order_prod_pack_purchase_date" id="purchase_date{$T.index}" class="ipt_date" /><span class="c_red">*</span>
			                </td>
			                <th>起效日期：</th>
			                <td>
			                  <input type="text" name="orderProdPacks[{$T.index}].order_prod_pack_effect_date" id="effect_date{$T.index}" class="ipt_date" /><span class="c_red">*</span>
			                </td>
                            <td></td>
			              </tr>
			              <tr height="70px">
			                <th>备注：</th>
			                <td colspan="3"><{$T.textarea_tagName}  name="orderProdPacks[{$T.index}].order_prod_pack_remark" style="width:400px;height:50px;"></{$T.textarea_tagName}></td>
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
	 
    <%@include file="/WEB-INF/pages/biz/productPack/productPack_SearchView.jsp" %>
	 
	 
	 

    <textarea id="businessTemplateBody" jTemplate="yes" style="display: none;">
        <tr>
          <td style="text-align: center;">{$T.business_name}</td>
          <td style="text-align: center;">
            <input type="hidden" name="orderProdPacks[{$T.index}].business_ids" value="{$T.id}"/>
            <input type="text" name="orderProdPacks[{$T.index}].event_staff_ids" id="event_staff_ids_{$T.index}_{$T.id}"  />
          </td>
        </tr>
    </textarea>
</div>
