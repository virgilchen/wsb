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
                  <tr id="{$T.id}" ondblclick="viewJs.toFollowUpView({$T.id});">
                    <td>{$T.order_id}</td>
                    <td>{$T.business_name}</td>
				    <td><a href="#">{$T.cust_name}</a></td>
				    <td>{$T.cust_phone_no}</td>
				    <td>{$T.order_init_staff_id}</td>
				    <td>{$T.order_init_time_stamp}</td>
				    <td>{$T.event_staff_id}</td>
				    <td><span class="c_orange">{$T.procs_step_name}</span></td>
				    <td><a href="javascript:viewJs.toFollowUpView({$T.id});">查看</a> | <a href="#">催单</a></td>
                  </tr>
              </textarea>
            </td>
          </tr>
        </tbody>

      </TABLE>
    </DIV>



    <%@include file="/WEB-INF/pages/frame/pagination.jsp" %>
    
  </div>
   
  <%--
  <div id="editDiv" style="display:none;" >
    
    <DIV class=main_title>
      <B>新增公告</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
    </DIV>


    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="order.id" id="order.id"/>
      <input type="hidden" name="order.version_id" id="order.version_id"/>
  
      <table width="100%" border="0">
        <tr valign="top">
          <td valign="top" width="15%">
          </td>
          <td width="45%">
            <table width="100%" border="0">
              <tr>
                <th width="25%">公告编号：</th>
                <td><input type="text" name="order.name_cn" id="order.name_cn" maxlength="50"/></td>
              </tr>
              <tr>
                <th>标题：</th>
                <td><input type="text" name="order.order_subject" maxlength="50"/></td>
              </tr>
              <tr>
                <th>内容：</th>
                <td>
                  <textarea name="order.order_content" required="required"  style="width: 100%;height: 80px;"></textarea>
                </td>
              </tr>
              <tr>
                <th>发表时间：</th>
                <td><input type="text" id="order.order_timestamp" name="order.order_timestamp" required="required" maxlength="19"/></td>
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
     --%>
    
  </div>
</div>
