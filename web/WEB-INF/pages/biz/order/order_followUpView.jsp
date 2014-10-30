<%@page import="com.globalwave.system.entity.SessionUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%

SessionUser sessionUser = (SessionUser)session.getAttribute(SessionUser.SESSION_PK) ;

String view_id=request.getParameter("view_id");
if (sessionUser == null) {
	response.getWriter().write("<script>window.location = '" + request.getContextPath() + "/login.jsp';var g$v" + view_id + " = null;</script>");
	response.getWriter().flush();
	return ;
}

String order_id=request.getParameter("order.id");
String event_id=request.getParameter("event_id");
String parent_view_id = request.getParameter("parent_view_id");
Long staffId=sessionUser.getStaff().getId();
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/order_list.action" ,
    create_url:root + "/biz/order_followUp.action" ,
    update_url:root + "/biz/order_followUp.action" ,
    get_url:root + "/biz/order_get.action" ,
    delete_url:root + "/biz/order_delete.action" ,
    entityName:"order",
    size:0,
    staffsJson:<%=request.getAttribute("staffsJson")%>,
    staffId:<%=staffId%>,
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("orderSO.pageIndex") ;
        
        fillOptions({id:"event_status", dictName:"orderEvent.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"orderSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("orderTB", {height:"400px"}) ;
        
        E$("orderProdPackEvent.event_staff_id").combobox2({
            id:"orderProdPackEvent.event_staff_id", 
            data:this.staffsJson, 
            firstLabel:"请选择...", 
            valueProperty:"id", 
            idProperty:"id", 
            textProperty:["staff_name"], 
            titleProperty:"staff_name"
        });
        
        E$("eForm").validator();
        //E$("sForm").validator();
        E("eForm").setFirstFocus();
        this.size = 0;
        this.getOrderInfo();
    },
    
    getOrderInfo:function() {
        var params = {} ;
        var _this = this;
        params["order.id"] = <%=order_id%> ;
        ajax(
        	root+"/biz/order_getOrderInfo.action", 
            params,
            function(data, textStatus){
                viewJs.entity = data;
                formDeserializeText("customerInfoDetailDiv", "label", data.customer, {}) ;
                formDeserializeText("orderInfoDiv", "label", data, {}) ;
                E$("order.order_no").html(data.order_no);
                //formDeserialize("eForm", data, {}) ;
                
                var orderProdPacks = data.orderProdPacks;
                var products = data.products;
                var orderProdPackEvents = data.orderProdPackEvents;
                
                var titleTemplate = "<li onclick=\"viewJs.setSelectedEventEditForm({$T.prod_pack_id});tabShow('menu{$T._name_}_','con{$T._name_}_',{$T._index_},{$T._length_});\" id=\"menu{$T._name_}_{$T._index_}\">{$T.prod_pack_name}（{$T.no_of_order_prod_pack}份）</li>";
                var buinessTitleTemplate = "<li onclick=\"viewJs.setEventEditForm({$T.prod_pack_id}, {$T.last_event_id}, {$T.event_staff_id});tabShow('menu{$T._name_}_','con{$T._name_}_',{$T._index_},{$T._length_});\" id=\"menu{$T._name_}_{$T._index_}\">{$T.business_name}</li>";
                var contentTemplate = "<div class=\"content\" id=\"con{$T._name_}_{$T._index_}\" ><ul id='businessTabs'></ul><div id='businessContents'></div></div>";

                $("#productPackTitlesDiv").html("");
                $("#productPackContentsDiv").html("");
                
                var prodLen = orderProdPacks.length;

                for(var i = 0 ; i < prodLen ; i ++) {
                    var orderProdPack = orderProdPacks[i];

                    orderProdPack._name_ = "OrderProduct";
                    orderProdPack._index_ = (i + 1);
                    orderProdPack._length_ = prodLen;

                    $("#productPackTitlesDiv").append(parse(titleTemplate, orderProdPack));
                    var $productPackContents = $(parse(contentTemplate, orderProdPack));
                    $("#productPackContentsDiv").append($productPackContents);

                    var businesses = _this.getBusiness(products, orderProdPack.prod_pack_id);

                    var buinessLen = businesses.length;
                    for (var j = 0 ; j < buinessLen ; j ++) {
                    	var business = businesses[j];
                    	var businessEvents = _this.getBusinessEvents(orderProdPackEvents, orderProdPack.prod_pack_id, business.business_id);
                        var $businessTabs = $("#businessTabs", $productPackContents);
                        var $businessContents = $("#businessContents", $productPackContents);
                    	var lastEvent = businessEvents[businessEvents.length - 1];
                    	
                        business.order_id = orderProdPack.order_id;
                    	business._name_ = "OrderBusiness" + i;
                    	business._index_ = (j + 1);
                    	business._length_ = buinessLen;
                        business.last_event_id = lastEvent.id;
                        business.event_staff_id = lastEvent.event_staff_id;

                        $businessTabs.append(parse(buinessTitleTemplate, business));
                        
                        orderProdPack.product_names = _this.getProductNames (products, orderProdPack.prod_pack_id, business.business_id);
                        orderProdPack.businessEvents = businessEvents ;
                        orderProdPack._name_ = business._name_ ;
                        orderProdPack._index_ = business._index_ ;
                        orderProdPack._length_ = business._length_ ;
                        
                        $businessContents.append(parse(V("orderBusinessTemplate"), orderProdPack));
                        
                        if (lastEvent.id == <%=event_id%>) {
                        	orderProdPacks.selectedIndex = i;
                            businesses.selectedIndex = j;
                        }
                    }
                    
                    if (buinessLen > 0) {
                    	var selectIndex = businesses.selectedIndex;
                    	if (typeof(selectIndex) == "undefined") {
                    		selectIndex = 0;
                    	}
                    	var business = businesses[selectIndex];
                    	_this.setEventEditForm(business.prod_pack_id, business.last_event_id, business.event_staff_id);
                        tabShow('menu' + business._name_ + "_", 'con' + business._name_ + "_", selectIndex + 1, buinessLen);
                    }
                }

                if (prodLen > 0) {
                	_this.setSelectedEventEditForm(orderProdPacks[orderProdPacks.selectedIndex].prod_pack_id);
                    tabShow('menuOrderProduct_', 'conOrderProduct_', orderProdPacks.selectedIndex + 1, prodLen);
                }
            }
        );
    },
    
    getBusiness:function (products, productPackId) {
        var ret = [] ;
        for(var i = 0 ; i < products.length ; i ++) {
            var product = products[i];
            if (product.prod_pack_id == productPackId) {
                if (typeof(ret["K_" + product.business_id]) == "undefined") {
                    ret["K_" + product.business_id] = product ;
                    ret[ret.length] = product ;
                }
            }
        }
        
        return ret ;
    },
    
    getProductNames:function (products, productPackId, businessId) {
        var ret = "" ;
        for(var i = 0 ; i < products.length ; i ++) {
            var product = products[i];
            if (product.prod_pack_id == productPackId && product.business_id == businessId) {
                if ( ret != "") {
                    ret += ", ";
                }
                ret += product.prod_name ;
            }
        }
        
        return ret ;
    },
    
    getBusinessEvents:function (businessEvents, prodPackId, businessId) {
        var ret = [] ;
        for(var i = 0 ; i < businessEvents.length ; i ++) {
            var event = businessEvents[i];
            if (event.business_id == businessId && event.prod_pack_id == prodPackId) {
            	ret[ret.length] = event ;
            }
        }
        
        return ret ;
    },
    
    setEventEditForm:function(prodPackId, lastEventId, eventStaffId) {
    	if (typeof(this.entity.selectedEventMap) == "undefined") {
    		this.entity.selectedEventMap = {};
    	}
    	
    	this.entity.selectedEventMap[prodPackId] = {lastEventId:lastEventId, eventStaffId:eventStaffId};
    	
    	if (eventStaffId == this.staffId) {
    		E$("orderFollowUpContent").show();
    	} else {
    		E$("orderFollowUpContent").hide();
    	}
    	
        V("orderProdPackEvent.id", lastEventId);
    },
    
    setSelectedEventEditForm:function(prodPackId) {
    	var selected = this.entity.selectedEventMap[prodPackId] ;
    	if (typeof(selected) == "undefined") {
    	    return ;
    	}
    	this.setEventEditForm(prodPackId, selected.lastEventId, selected.eventStaffId);
    },
    
    onSaveOk:function(data) {
    	//alert(data.id);
    	V("order.id", data.id);
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
    
    followUp:function() {

        var frm = E("eForm") ;
        if(typeof(frm.checkValidity) != "undefined" && !frm.checkValidity()) {
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
            this.create_url, 
            E$("eForm").serialize(),
            function(data, textStatus){
                if (data.code == "0") {
                	removeView(<%=view_id%>);
                	g$v<%=parent_view_id%>.list();
                }
            }
        );
    }
    
    
    
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">
	<div class="main_title">
	  <b>业务单业务处理：<label id="order.order_no">xxxxxxxxxxxxxxxxxx</label></b>
	  <DIV class="main_tt_right fr">
        <A class=blue href="javascript:removeView(<%=view_id%>);">返回</A>
      </DIV>
	</div>
	 
	<div class="main_infomation" id="orderInfoDiv">
	    <table width="100%" border="0">
	      <tr>
		    <td width="10%" style="text-align: right;">业务单单号：</td>
		    <td width="20%" style="text-align: left;"><label id="order.order_no">xxxxxxxxxxxxxxxxxxx</label></td>
		    <td width="10%" style="text-align: right;">业务单发起时间：</td>
            <td width="20%" style="text-align: left;"><label id="customer.order_init_time_stamp">2010-12-01 13:22</label></td>
            <td width="10%" style="text-align: right;">业务单发起人：</td>
            <td width="30%" style="text-align: left;"><label id="customer.order_init_staff_id">启动业务人员</label></td>
	      </tr>
	      <tr>
            <td width="10%" style="text-align: right;">业务单备注：</td>
            <td width="20%" style="text-align: left;" colspan="5"><label id="order.order_remark">备注备注备注备注备注</label></td>
	      </tr>
	    </table>
	</div>
	 
	<%@include file="/WEB-INF/pages/biz/order/customerInfo.jsp" %>
	 
	<div class="main_order_detail">
	    <div class="title">
	        <b>业务信息</b>
	        <ul class="title_right" id="productPackTitlesDiv">
	            <li class="selected" onclick="tabShow('menu1_','con1_',1,3);" id="menu1_1">车险A餐（1份）</li>
                <li onclick="tabShow('menu1_','con1_',2,3);" id="menu1_2">洗车B套餐（1份）</li>
                <li onclick="tabShow('menu1_','con1_',3,3);" id="menu1_3">洗车C套餐（1份）</li>
	        </ul>
	    </div>
	    
        <div id="productPackContentsDiv">
	      <div class="content" id="con1_1" style="display:block;">
	        <ul>
	            <li class="selected" onclick="tabShow('menu2_','con2_',1,2);" id="menu2_1">保险</li>
	            <li onclick="tabShow('menu2_','con2_',2,2);" id="menu2_2">道路救援</li>
	        </ul>
	        <div id="con2_1" style="display:block;" >
                <p>基础商品：车损险、车上司机险</p>
                <p>购买日期：2013-12-01</p>
                <p>起效日期：2013-12-01</p>
                <p>备注：备注备注备注备注备注</p>
                <b>流程处理记录：</b>
	            <table width="100%" border="0">
	              <tr>
	                <th>流程</th>
	                <th>处理人</th>
	                <th>处理人角色</th>
	                <th>处理环节</th>
	                <th >处理时间</th>
	                <th >处理结果</th>
	                <th >用时</th>
	                <th>备注</th>
	              </tr>
	              <tr>
	                <td>1</td>
	                <td>张小明</td>
	                <td>保险销售员角色</td>
	                <td>业务受理</td>
	                <td>2013-12-01 13:22</td>
	                <td class="c_green">成功</td>
	                <td>13分钟</td>
	                <td>&nbsp;</td>
	              </tr>
	              <tr>
	                <td>2</td>
	                <td>王三伍</td>
	                <td>业务处理人员</td>
	                <td>业务办理</td>
	                <td>2013-12-01 17:22</td>
	                <td class="c_red">失败</td>
	                <td>176分钟</td>
	                <td>客户投诉业务办理慢</td>
	              </tr>
	              <tr>
	                <td>3</td>
	                <td>陈大明</td>
	                <td>业务经理</td>
	                <td>业务办理</td>
	                <td>2013-12-01 18:22</td>
	                <td class="c_green">成功/结束</td>
	                <td>13分钟</td>
	                <td>客户满意</td>
	              </tr>
	            </table>
	        </div>
	        <div id="con2_2" style="display:none;" >我是 道路救援 的内容</div>
	      </div>
          <div class="content" id="con1_2" style="display:none;">我是 洗车B套餐（1份）的内容</div>
          <div class="content" id="con1_3" style="display:none;">我是 洗车C套餐（1份）的内容</div>
	    </div>
	    
        <textarea id="orderBusinessTemplate" style="display:none;" >
            <div id="con{$T._name_}_{$T._index_}" style="display:block;" >
                <p>基础商品：{$T.product_names}</p>
                <p>购买日期：{fmt.maxlen($T.order_prod_pack_purchase_date, 10)}</p>
                <p>起效日期：{fmt.maxlen($T.order_prod_pack_effect_date, 10)}</p>
                <p>备注：{$T.order_prod_pack_remark}</p>
                <p></p>
                <b>流程处理记录：</b>
                <table width="100%" border="0">
                  <tr>
                    <th>流程</th>
                    <th>处理人</th>
                    <th>处理人角色</th>
                    <th>处理环节</th>
                    <th >处理时间</th>
                    <th >处理结果</th>
                    <th >用时</th>
                    <th>备注</th>
                  </tr>
                  {#foreach $T.businessEvents as record}
                  <tr>
                    <td>{$T.record$index + 1}</td>
                    <td>{$T.record.staff_name}</td>
                    <td>{$T.record.staff_role_name}</td>
                    <td>{$T.record.procs_step_name}</td>
                    <td>{$T.record.event_time_stamp}</td>
                    <td>
                      {#if $T.record.event_status == 'R'}
                        <span>正在处理</span>
                      {#else}
                        <span class="{#if $T.record.event_status == 'F'}c_red{#else}c_green{#/if}">
                        {dVal("orderEvent.status", "name_", {PK_ID:$T.record.event_status})}
                        </span>
                      {#/if}
                    </td>
                    <td>{#if $T.record.event_duration != null}{fmt.maxlen($T.record.minute_duration, 10)}分钟{#/if}</td>
                    <td>{fmt.maxlen($T.record.event_remark, 1000)}</td>
                  </tr>
                  {#/for}
                </table>
            </div>
        </textarea>
	    
	    <div class="content" id="orderFollowUpContent">
	      <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" >
              <input type="hidden" name="orderProdPackEvent.id" id="orderProdPackEvent.id"/>
              <input type="hidden" name="orderProdPackEvent.business_id" id="orderProdPackEvent.business_id"/>
              <input type="hidden" name="orderProdPackEvent.order_id" id="orderProdPackEvent.order_id"/>
              <input type="hidden" name="orderProdPackEvent.prod_pack_id" id="orderProdPackEvent.prod_pack_id"/>
	          <input type="hidden" name="orderProdPackEvent.version_id" id="orderProdPackEvent.version_id"/>
	          
	          
              <div class="order_result">
                <p><b>处理结果记录</b></p>
                <table width="80%" border="0">
                  <tr>
                    <th>处理结果：</th>
                    <td>
                      <select name="orderProdPackEvent.event_status" id="event_status">
                      </select>
                    </td>
                    <th> 业务处理人：</th>
                    <td>
                      <input name="orderProdPackEvent.event_staff_id" id="orderProdPackEvent.event_staff_id" type="text">
                      <!-- 
                        <a href="#" class="link_blue">选择</a>
                       -->
                    </td>
                  </tr>
                  
                  <tr>
                    <th>备注：</th>
                    <td colspan="5"><textarea name="orderProdPackEvent.event_remark" cols="46" rows="5"></textarea> <span class="c_red">*</span></td>
                  </tr>
                  
                  <tr>
                    <th>&nbsp;</th>
                    <td><a href="javascript:viewJs.followUp();" class="btn_orange">保存</a></td>
                  </tr>
                </table>
            </div>
          </form>
        </div>
	</div>

</div>
