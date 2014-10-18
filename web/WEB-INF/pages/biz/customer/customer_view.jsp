<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/customer_list.action" ,
    create_url:root + "/biz/customer_create.action" ,
    update_url:root + "/biz/customer_update.action" ,
    get_url:root + "/biz/customer_get.action" ,
    delete_url:root + "/biz/customer_delete.action" ,
    entityName:"customer",
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("customerSO.pageIndex") ;
        
        //fillOptions({id:"customer.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"customerSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("customerTB", {height:"400px"}) ;
        
        E$("customer.cust_birthday").datepicker({});
        
        E$("eForm").validator();
        E$("sForm").validator();
        E("sForm").setFirstFocus();
        
    }
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <%--@include file="/WEB-INF/pages/frame/navigation.jsp" --%>
  
  <div id="listDiv">
  
    <DIV class=main_title>
      <B>客户列表</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=blue href="javascript:viewJs.toEdit();">业务发起</A>
        <A class=blue href="javascript:viewJs.toEdit();">发展成会员</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0">
        <input name="customerSO.pageIndex" id="customerSO.pageIndex" value="1" type="hidden" />
        <input name="customerSO.pageSize" id="customerSO.pageSize" value="10" type="hidden" />
        <table width="100%" >
          <tr>
           <td style="width:60px;" >姓名：</td>
           <td style="width:100px;">
             <input class=mg_r name="customerSO.cust_name" value="" type="text" />
           </td>
           <td style="width:100px;" >客户号：</td>
           <td style="width:100px;">
             <input class=mg_r name="customerSO.cust_code" value="" type="text" />
           </td>
           <td style="width:60px;" >电话：</td>
           <td style="width:100px;">
             <input class=mg_r name="customerSO.cust_phone_no" value="" type="text" />
           </td>
           <td style="width:100px;" >会员号：</td>
           <td style="width:100px;">
             <input class=mg_r name="customerSO.member_id" value="" type="text" />
           </td>
           <td style="width:100px;">
             <INPUT class="ipt_btn mg_r" value=搜索 type=button name=""  onclick="viewJs.first();">
           </td>
           <td style="width:400px;">
           </td>
          </tr>
        </table>
      </form>
    </DIV>
    
    
    <DIV class=main_list>
      <TABLE border=0 width="100%" id="customerTB" title="客户列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
			<TH width="80px">客户号</TH>
			<TH width="80px">姓名</TH>
			<TH width="40px">性别</TH>
			<TH width="80px">生日</TH>
			<TH width="100px">电话号码</TH>
			<TH width="100px">会员号</TH>
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
                    <td>{$T.cust_code}</td>
                    <td>{$T.cust_name}</td>
                    <td>{$T.cust_gender}</td>
                    <td>{$T.cust_birthday}</td>
                    <td>{$T.cust_phone_no}</td>
                    <td>{$T.member_id}</td>
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
      <B>新增客户</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
    </DIV>


    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="customer.id" id="customer.id"/>
  
      <table width="100%" border="0">
        <tr valign="top">
          <td valign="top" width="15%">
          </td>
          <td width="45%">
            <table width="100%" border="0">
              <tr>
                <th width="25%">公告编号：</th>
                <td><input type="text" name="customer.name_cn" id="customer.name_cn" maxlength="50"/></td>
              </tr>
              <tr>
                <th>标题：</th>
                <td><input type="text" name="customer.customer_subject" maxlength="50"/></td>
              </tr>
              <tr>
                <th>内容：</th>
                <td>
                  <textarea name="customer.customer_content" required="required"  style="width: 100%;height: 80px;"></textarea>
                </td>
              </tr>
              <tr>
                <th>发表时间：</th>
                <td><input type="text" id="customer.customer_timestamp" name="customer.customer_timestamp" required="required" maxlength="19"/></td>
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
