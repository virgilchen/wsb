<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/order_getMyTasks.action" ,
    //create_url:root + "/biz/order_create.action" ,
    //update_url:root + "/biz/order_update.action" ,
    //get_url:root + "/biz/order_get.action" ,
    //delete_url:root + "/biz/order_delete.action" ,
    entityName:"order",
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("orderSO.pageIndex") ;
        
        //fillOptions({id:"order.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"orderSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("orderTB", {height:"400px"}) ;
        
        //E$("order.order_timestamp").datetimepicker({
        //    timeFormat: "HH:mm:ss"
        //});
        
        //E$("eForm").validator();
        //E$("sForm").validator();
        //E("sForm").setFirstFocus();
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
                    _this.list(function() {
                        _this.toFollowUpView(orderId, eventId);
                    });
                }
            }
        );
    }
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <div id="listDiv">
  
    <DIV class=main_title>
      <B>待办业务单</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.first();">刷新</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search" style="display: none;">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0">
        <input name="orderSO.pageIndex" id="orderSO.pageIndex" value="1" type="hidden" />
        <input name="orderSO.pageSize" id="orderSO.pageSize" value="10" type="hidden" />
        <%--
        <table width="100%" >
          <tr>
           <td style="width:60px;" >标题：</td>
           <td style="width:100px;">
             <input class=mg_r name="orderSO.order_subject" value="" type="text" />
           </td>
           <td style="width:100px;">
             <INPUT class="ipt_btn mg_r" value=搜索 type=button name=""  onclick="viewJs.first();">
           </td>
           <td style="width:1000px;">
           </td>
          </tr>
        </table>
         --%>
      </form>
    </DIV>
    
    
    <DIV class=main_list>
      <TABLE border=0 width="100%" id="orderTB" title="通知列表">
        <thead>
          <TR>
		    <th>业务单号</th>
		    <th>业务类型</th>
		    <th>姓名</th>
		    <th>联系电话</th>
		    <th>业务发起人 </th>
		    <th>发起时间</th>
		    <th>当前处理人</th>
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
                  <tr id="{$T.id}" {#if $T.event_staff_id != null}ondblclick="viewJs.toFollowUpView({$T.order_id}, {$T.event_id});"{#/if}>
                    <td>{$T.order_no}</td>
                    <td>{$T.business_name}</td>
				    <td><a href="#">{$T.cust_name}</a></td>
				    <td>{$T.cust_phone_no}</td>
				    <td>{fmt.maxlen($T.order_init_staff_name, 20)}</td>
				    <td>{$T.order_init_time_stamp}</td>
				    <td>{fmt.maxlen($T.event_staff_name)}</td>
				    <td><span class="c_orange">{$T.procs_step_name}</span></td>
				    <td>
				      {#if $T.event_staff_id == null}
                      <a href="javascript:viewJs.pickUp({$T.order_id}, {$T.event_id});">领取</a> 
                      {#else} 
                      <a href="javascript:viewJs.toFollowUpView({$T.order_id}, {$T.event_id});">查看</a>
                      {#/if}
                      <!-- 
				      | 
				      <a href="#">催单</a>
                       -->
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
