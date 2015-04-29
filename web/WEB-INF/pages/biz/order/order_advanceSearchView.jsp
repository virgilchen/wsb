<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/order_advanceSearch.action" ,
    //create_url:root + "/biz/order_create.action" ,
    //update_url:root + "/biz/order_update.action" ,
    //get_url:root + "/biz/order_get.action" ,
    //delete_url:root + "/biz/order_delete.action" ,
    entityName:"order",
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("orderSO.pageIndex") ;
        
        fillOptions({id:"orderSO.order_type", dictName:"Order.type", firstLabel:"全部"}) ;// 改为字典取值
        fillOptions({id:"orderSO.order_cur_status", dictName:"Order.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("orderTB", {height:"400px"}) ;
        
        //E$("order.order_timestamp").datetimepicker({
        //    timeFormat: "HH:mm:ss"
        //});
        
        //E$("eForm").validator();
        //E$("sForm").validator();
        //E("sForm").setFirstFocus();
        
        
        E$("businessSelection").combotree({id:"businessSelection", 
            data:g$dict.Business, 
            //firstLabel:"请选择",
            editable:false,
            valueProperty:"id", 
            idProperty:"id", 
            textProperty:["business_name"], 
            titleProperty:"business_name",
            treeSetting:{
                data: {
                    key: {
                        name: "business_name"
                    },
                    simpleData: {
                        enable: true,
                        idKey: "PK_ID",
                        pIdKey: "business_id_upper",
                        rootPId: null
                    }
                }
            }
        });
        //E$("businessSelection").combotree("disable");
        E("businessSelection").onSelected = function (event, elem) {
        	document.getElementById("orderSO.business_name").value=elem.business_name;
        };
        
        this.first();
        
    },
    
    toFollowUpView:function(order_id, event_id) {
    	var url = '/biz/order_followUpView.action?order.id=' + order_id + '&event_id=' + event_id + '&parent_view_id=' + <%=view_id%>;
    	openView(100701, url, '业务单业务处理');
    },
    
    pickUp:function(orderId, eventId) {
        var _this = this ;
        ajax(
            root + "/biz/order_pickUp.action", 
            {"orderProdPackEvent.id":eventId},
            function(data, textStatus){
                if (data.code == "0") {
                    _this.list();
                    _this.toFollowUpView(orderId, eventId);
                }
            }
        );
    },
    
    updateUrgentLevel:function(orderId, level) {
        var _this = this ;
        ajax(
            root + "/biz/order_updateUrgentLevel.action", 
            {"order.id":orderId, "order.urgent_levent":level},
            function(data, textStatus){
                if (data.code == "0") {
                    _this.list();
                }
            }
        );
    },
    
    toEditView:function(order_id) {
        openView(100003, '/biz/order_openView.action?readonly=true&order_id=' + order_id + '&parent_view_id=' + <%=view_id%>, '业务发起');
    }
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <div id="listDiv">
  
    <DIV class=main_title>
      <B>业务单流查看</B> 
      <DIV class="main_tt_right fr">
        <!-- 
        <A class=orange href="javascript:viewJs.first();">刷新</A>
         -->
      </DIV>
    </DIV>
  
    <DIV class="main_search" >
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0 0 5px 0;">
        <input name="orderSO.pageIndex" id="orderSO.pageIndex" value="1" type="hidden" />
        <input name="orderSO.pageSize" id="orderSO.pageSize" value="10" type="hidden" />

        <table width="100%" >
          <tr>
           <td style="width:80px;" >业务单号：</td>
           <td>
             <input class=mg_r name="orderSO.order_no" value="" type="text" />
           </td>
           <td style="width:80px;" >发起人：</td>
           <td>
             <input class=mg_r name="orderSO.order_init_staff_name" value="" type="text" />
           </td>
           <td style="width:80px;" >业务类型：</td>
           <td>
             <input class=mg_r id="businessSelection" value="" type="text" readonly="readonly" />
             <input name="orderSO.business_name" id="orderSO.business_name" value="" type="hidden" />
           </td>
           <td style="width:80px;" >当前处理人：</td>
           <td>
             <input class=mg_r name="orderSO.event_staff_name" value="" type="text" />
           </td>
          </tr>
          <tr>
           <td>客户名称：</td>
           <td>
             <input class=mg_r name="orderSO.psdo_cust_name" value="" type="text" />
           </td>
           <td>客户电话：</td>
           <td>
             <input class=mg_r name="orderSO.cust_phone_no" value="" type="text" />
           </td>
           <td>订单类型：</td>
           <td>
             <select name="orderSO.order_type" id="orderSO.order_type"></select>
           </td>
           <td>订单状态：</td>
           <td>
             <select name="orderSO.order_cur_status" id="orderSO.order_cur_status"></select>
             &nbsp;
             <INPUT class="ipt_btn mg_r" value=搜索 type=button onclick="viewJs.first();">
           </td>
          </tr>
        </table>

      </form>
    </DIV>
    
    
    <DIV class=main_list>
      <TABLE border=0 width="100%" id="orderTB" title="通知列表">
        <thead>
          <TR>
		    <th>业务单号</th>
		    <th>姓名</th>
		    <th>联系电话</th>
		    <th>业务发起人 </th>
		    <th>发起时间</th>
		    <th>订单类型</th>
            <th>处理状态</th>
		    <th>操作</th>
		  <TR>
        </thead>
        
        <tbody id="listBody" >
        </tbody>
         
         
        <tbody style="display:none;visibility: false;" disabled="true">
          <tr>
            <td>
              <textarea id="templateBody" jTemplate="yes">
                  <tr id="{$T.id}" ondblclick="{#if $T.order_cur_status == 'I'}viewJs.toEditView({$T.order_id});{#else}viewJs.toFollowUpView({$T.order_id});{#/if}">
                    <td>{$T.order_no}</td>
				    <td><a href="#">{$T.cust_name}</a></td>
				    <td>{$T.cust_phone_no}</td>
				    <td>{fmt.maxlen($T.order_init_staff_name, 20)}</td>
				    <td>{$T.order_init_time_stamp}</td>
				    <td>{fmt.maxlen($T.procs_step_name, 100)}</td>
				    <td><span class="c_orange">{dVal("Order.status", "name_", {PK_ID:$T.order_cur_status})}</span></td>
				    <td>
				      {#if $T.order_cur_status != 'I' && $T.order_cur_status != 'E'}
				        {#if $T.urgent_levent == 9}
				        <a href="javascript:viewJs.updateUrgentLevel({$T.order_id}, 0)">撤销催单</a>|
				        {#else}
                        <a href="javascript:viewJs.updateUrgentLevel({$T.order_id}, 9)">催单</a>|
				        {#/if}
				      {#/if}
                      <a href="javascript:{#if $T.order_cur_status == 'I'}viewJs.toEditView({$T.order_id});{#else}viewJs.toFollowUpView({$T.order_id});{#/if}">查看</a> 
				    </td>
                  </tr>
              </textarea>
            </td>
          </tr>
        </tbody>

      </TABLE>
    </DIV>



    <%@include file="/WEB-INF/pages/frame/pagination.jsp" %>
    
  </div>
   
</div>
