<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
<!--
g$v<%=view_id%>.orderHistoryView = $.extend(newView(), {
    view:document.getElementById("orderHistoryListDiv"), 
    pageIndex : document.getElementById("orderSO.pageIndex") ,
    
    list:function() {
    	this.getMyOrders();
    },
    
    getMyOrders:function() {
    	V("orderSO.psdo_cust_id", V("customer.id"));
    	
        ajax(
            root + "/biz/customer_getMyOrders.action", 
            E$("sOrderHistoryForm").serialize(),
            function(data, textStatus){
                var $div = E$("orderHistoryListDiv");
                $div.html("");
                
                var orderRowTemplate = V("orderRowTemplate");
                
                $(data.list).each(function(i, order) {
                    $div.append(parse(orderRowTemplate, order));
                });
                
                var $pageSize = E$("orderSO.pageSize") ;
                
                viewJs.lastIndex = Math.ceil(data.total/parseInt($pageSize.val()));
                viewJs.setPaginationInfo(data.total, data.pageIndex, viewJs.lastIndex, "orderHistoryView") ;
                
                
                /*
                var $pageSize = E$(viewJs.entityName + "SO.pageSize") ;
                if ($pageSize.length <= 0) {
                    $pageSize = E$("pageSize") ;
                }
                
                viewJs.lastIndex = Math.ceil(data.total/parseInt($pageSize.val()));
                viewJs.setPaginationInfo(data.total, data.pageIndex, viewJs.lastIndex) ;
                
                if (viewJs.onList) {
                    viewJs.onList(data) ;
                }*/
            }
        );
    },
    
    showOrderPackDetail:function(orderProdPackId, orderId, index, length) {
        var _this = this ;
        
        var contentId = 'conOrder' + orderId + '_';
        tabShow('menuOrder' + orderId + '_', contentId, index, length);
        
        var $content = E$(contentId + 1);
        
        if ($content.attr("isInited") == "N") {
            var buinessTitleTemplate = "<li onclick=\"tabShow('menu{$T._name_}_','con{$T._name_}_',{$T._index_},{$T._length_});\" id=\"menu{$T._name_}_{$T._index_}\">{$T.business_name}</li>";
            
            ajax(
                root+"/biz/order_getOrderInfo.action", 
                {"order.id":orderId},
                function(data, textStatus){
                    $content.attr("isInited", "Y");
                    
                     var orderProdPacks = data.orderProdPacks;
                     var products = data.products;
                     var orderProdPackEvents = data.orderProdPackEvents;
                     
                     var prodLen = orderProdPacks.length;

                     for(var i = 0 ; i < prodLen ; i ++) {                         
                         var orderProdPack = orderProdPacks[i];

                         orderProdPack._name_ = "OrderProduct" + orderProdPack.id;
                         orderProdPack._index_ = (i + 1);
                         orderProdPack._length_ = prodLen;
                         //orderProdPack.order_remark = data.order_remark;

                         //$("#productPackTitlesDiv").append(parse(titleTemplate, orderProdPack));
                         var $productPackContents = E$(contentId + (i + 1));//$(parse(contentTemplate, orderProdPack));
                         //$("#productPackContentsDiv").append($productPackContents);

                         var businesses = _this.getBusiness(products, orderProdPack.prod_pack_id);

                         var buinessLen = businesses.length;
                         for (var j = 0 ; j < buinessLen ; j ++) {
                             var business = businesses[j];
                             var businessEvents = _this.getBusinessEvents(orderProdPackEvents, orderProdPack.prod_pack_id, business.business_id);
                             var $businessTabs = $("#businessTabs", $productPackContents);
                             var $businessContents = $("#businessContents", $productPackContents);
                             var lastEvent = businessEvents.length == 0
						                             ?{id:-1,event_staff_id:-1}
						                             :businessEvents[businessEvents.length - 1];
                             
                             business.order_id = orderProdPack.order_id;
                             business._name_ = "OrderBusiness" + orderProdPack.id + "_" + business.id;
                             business._index_ = (j + 1);
                             business._length_ = buinessLen;
                             business.last_event_id = lastEvent.id;
                             business.event_staff_id = lastEvent.event_staff_id;
                             
                             var $buinessTitle = $(parse(buinessTitleTemplate, business));
                             if (lastEvent.id == -1) {
                                 $buinessTitle.hide();
                             }

                             $businessTabs.append($buinessTitle);
                             
                             orderProdPack.product_names = _this.getProductNames (products, orderProdPack.prod_pack_id, business.business_id);
                             orderProdPack.businessEvents = businessEvents ;
                             orderProdPack._name_ = business._name_ ;
                             orderProdPack._index_ = business._index_ ;
                             orderProdPack._length_ = business._length_ ;
                             
                             $businessContents.append(parse(V("orderBusinessTemplate"), orderProdPack));

                             if (typeof(data.order_type) != "undefined" && data.order_type == "P") {
                            	 $businessContents.hide();
                             }
                             
                             if (j == buinessLen - 1) {
                                 tabShow('menu' + business._name_ + "_", 'con' + business._name_ + "_", 1, buinessLen);
                             }
                             
                         }
                         
                     }
                     
                     tabShow('menuOrder' + orderId + '_', contentId, index, length);
                }
            );
        }
    },
    
    // same as order followUp
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

    // same as order followUp
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

    // same as order followUp
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
    }
});
//-->
</script>
<form method="post" id="sOrderHistoryForm" name="sOrderHistoryForm" onsubmit="return false;" style="margin: 0">
<input name="orderSO.pageIndex" id="orderSO.pageIndex" value="1" type="hidden" />
<input name="orderSO.pageSize" id="orderSO.pageSize" value="5" type="hidden" />
<input name="orderSO.psdo_cust_id" id="orderSO.psdo_cust_id" value="" type="hidden" />


<div id="orderHistoryListDiv">
    <%--
    <div class="main_order_detail">
        <div class="title">业务单编号：000213｜业务提交时间：2013-12-01 13:22
            <ul class="title_right">
                <li onclick="tabShow('menu3_','con3_',1,2);" id="menu3_1">车险A餐（1份）</li>
                <li onclick="tabShow('menu3_','con3_',2,2);" id="menu3_2">洗车B套餐（1份）</li>
            </ul>
        </div>
        <div class="content" id="con3_1" style="display:block;">
            <div class="business_note"><b>业务单备注：</b>备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注</div>
            <ul>
                <li class="selected" onclick="tabShow('menu4_','con4_',1,2);" id="menu4_1">保险</li>
                <li onclick="tabShow('menu4_','con4_',2,2);" id="menu4_2">道路救援</li>
            </ul>
            <div id="con4_1" style="display:block;" >
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
            <div id="con4_2" style="display:none;" >我是 道路救援 的内容</div>
        </div>
        <div class="content" id="con3_2" style="display:none;">我是 洗车B套餐（1份）的内容</div>
    </div>
     --%>
</div>    

    <textarea id="orderRowTemplate" jTemplate="yes" style="display: none;">
        <div class="main_order_detail">
            <div class="title">{#if $T.order_type == 'P'}订购{#else}流程{#/if}业务单编号：{$T.order_no}｜业务提交时间：{$T.order_init_time_stamp}｜启动业务人员：{$T.order_init_staff_name}
                <ul class="title_right">
                  {#foreach $T.orderProdPacks as pack}
                    <li onclick="viewJs.orderHistoryView.showOrderPackDetail({$T.pack.id}, {$T.id}, {$T.pack$index + 1}, {$T.orderProdPacks.length});" id="menuOrder{$T.id}_{$T.pack$index + 1}">
                      {$T.pack.prod_pack_name}（{$T.pack.no_of_order_prod_pack}份）
                    </li>
                  {#/for}
                </ul>
            </div>
            
            {#foreach $T.orderProdPacks as pack}
            <div class="content" id="conOrder{$T.id}_{$T.pack$index + 1}" style="display:none;" isInited="N">
            
                <div class="business_note"><b>业务单备注：</b><span>{$T.order_remark}</span></div>
            
                <ul id='businessTabs'">
                </ul>
                <div id="businessContents">
                </div>
            </div>
            {#/for}

        </div>
    </textarea>    
        
    <%--
    <textarea id="orderProdPackTemplate" jTemplate="yes" style="display: none;">
        <div class="business_note"><b>业务单备注：</b><span>{$T.order_remark}</span></div>
    
        <ul>
            <li class="selected" onclick="tabShow('menu6_','con6_',1,2);" id="menu6_1">保险</li>
            <li onclick="tabShow('menu6_','con6_',2,2);" id="menu6_2">道路救援</li>
        </ul>
        <div id="con6_1" style="display:block;" >
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
    </textarea>    
        --%> 

        <textarea id="orderBusinessTemplate" style="display:none;" >
            <div id="con{$T._name_}_{$T._index_}" style="display:block;" >
                <p>基础商品：{$T.product_names}</p>
                <p>购买日期：{fmt.maxlen($T.order_prod_pack_purchase_date, 10)}</p>
                <p>有效日期：{fmt.maxlen($T.order_prod_pack_effect_date, 10)}~{fmt.maxlen($T.order_prod_pack_expire_date, 10)}</p>
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
                    <td>{fmt.maxlen($T.record.staff_name, 1000)}</td>
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
        
        
        

	<div class="page_wrap clearfix" id='pagination.orderHistoryView'>
	    <label id="paginationInfo">总记录数为0条，当前为第1页，共0页&nbsp;&nbsp;&nbsp;&nbsp;</label>
	    
	    <div class=paginator>
	      <a href="javascript:viewJs.orderHistoryView.preview();">&lt;上一页</a>
	      <span id="paginationNumbers"></span>
	      <a href="javascript:viewJs.orderHistoryView.next();">下一页&gt;</a>
	    </div>
	</div>
</form>