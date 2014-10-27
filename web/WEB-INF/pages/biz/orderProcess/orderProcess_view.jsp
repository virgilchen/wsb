<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
final String view_id=request.getParameter("view_id");
final String business_id=request.getParameter("business_id");
final String business_name=request.getParameter("business_name");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/orderProcess_list.action" ,
    create_url:root + "/biz/orderProcess_save.action" ,
    update_url:root + "/biz/orderProcess_save.action" ,
    get_url:root + "/biz/orderProcess_get.action" ,
    delete_url:root + "/biz/orderProcess_delete.action" ,
    entityName:"orderProcess",
    size:0,
    
    init:function (){
    	
        //this.pageIndex = E("orderProcessSO.pageIndex") ;
        
        //fillOptions({id:"orderProcess.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        this.initDataGrid("orderProcessTB") ;
        
        
        //E$("eForm").validator();
        //E$("sForm").validator();
        //E("eForm").setFirstFocus();
        
        this.list();
        
    },
    
    add:function() {
        this.addRows ("orderProcessTB", [{procs_step_no:this.size ++ }], {deep:1});
        this.refreshTableList();
    },

    moveUp:function(index) {
    	var $trs = $("#orderProcessTB #listBody tr", viewJs.view);
    	
    	$($trs[index]).remove().insertBefore($($trs[index - 1]));
    	this.refreshTableList();
    },

    moveDown:function(index) {
        this.moveUp(index + 1);
        //var $trs = $("#orderProcessTB #listBody tr", viewJs.view);
        //$trs[index].remove().insertAfter($trs[index + 1]);
    },
    
    refreshTableList:function() {
    	var $trs = $("#orderProcessTB #listBody tr", viewJs.view); 
    	var trLen = $trs.length;
    	
    	$trs.each(function(i, elem) {
    		var $tds = $("td", elem);
    		
    		$($tds[0]).html(i + 1);
    		$("#orderProcesses\\.procs_step_no", elem).val(i);
    		
    		if (i == 0) {
                $($tds[1]).html("&nbsp;&nbsp;&nbsp;<a href='javascript:viewJs.moveDown(" + i + ")'>↓</a>");
    		} else if (i == trLen - 1) {
                $($tds[1]).html("<a href='javascript:viewJs.moveUp(" + i + ")'>↑</a>&nbsp;&nbsp;&nbsp;"); 
    		} else {
                $($tds[1]).html("<a href='javascript:viewJs.moveUp(" + i + ")'>↑</a>&nbsp;<a href='javascript:viewJs.moveDown(" + i + ")'>↓</a>");
    		}
    	});
    },
    
    onList:function(data) {
    	this.refreshTableList();
    	
    	this.size = $("#orderProcessTB #listBody tr", viewJs.view).length;
    },
    
    onSaveOk:function(data) {
    	removeView(<%=view_id%>);
    },
    
    deleteMe:function(id) {
        $("#orderProcessTB #listBody #orderProcessRow_" + id, viewJs.view).remove();
    }
    
    
}) ;


</script>
    
<div id="view_<%=view_id%>" style="height: 480px;" class="FrameMain">

  <%--
  
  <div id="listDiv" style="display:none;">
  
    <DIV class=main_title>
      <B>公告列表</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0">
        <input name="orderProcessSO.pageIndex" id="orderProcessSO.pageIndex" value="1" type="hidden" />
        <input name="orderProcessSO.pageSize" id="orderProcessSO.pageSize" value="10" type="hidden" />
        <table width="100%" >
          <tr>
           <td style="width:60px;" >标题：</td>
           <td style="width:100px;">
             <input class=mg_r name="orderProcessSO.orderProcess_subject" value="" type="text" />
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
      <TABLE border=0 width="100%" id="orderProcessTB" title="通知列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
			<TH>标题</TH>
			<TH width="200px">发表时间</TH>
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
                    <td>{$T.orderProcess_subject}</td>
                    <td>{$T.orderProcess_timestamp}</td>
                  </tr>
              </textarea>
            </td>
          </tr>
        </tbody>

      </TABLE>
    </DIV>



    <%@include file="/WEB-INF/pages/frame/pagination.jsp" %>
    
  </div>
    --%>
  <div id="editDiv" >
    
    <DIV class=main_title>
      <B><%=business_name %>（??）业务流程配置</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.add();">增加</A>
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:removeView(<%=view_id%>);">返回</A>
      </DIV>
    </DIV>

    <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0">
        <input name="orderProcessSO.pageIndex" id="orderProcessSO.pageIndex" value="-2" type="hidden" />
        <input name="orderProcessSO.business_id" id="orderProcessSO.business_id" value="<%=business_id %>" type="hidden" />
    </form>

    <DIV class=main_list>
    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" >
      <input type="hidden" name="business_id" value="<%=business_id %>"/>
      <input type="hidden" name="orderProcess.version_id" id="orderProcess.version_id"/>
      
      <TABLE border=0 width="100%" id="orderProcessTB">
        <thead>
          <TR>
            <TH width="80px">处理顺序</TH>
            <TH width="80px">排序</TH>
            <TH>处理环节</TH>
            <TH>处理角色</TH>
            <TH width="80px">操作</TH>
          <TR>
        </thead>
        
        <tbody id="listBody" class="main_form">
        </tbody>
        
         <%--
        <tbody id="addBody" class="main_form">
          <tr >
            <td></td>
            <td></td>
            <td><input type="text" name="procs_step_name" id="procs_step_name" maxlength="50"/></td>
            <td>
              <input type="text" name="procs_staff_role_id" id="procs_staff_role_id" maxlength="50"/>
              <input type="button" value="增加" onclick="viewJs.add();"/>
            </td>
          </tr>
        </tbody>
          --%>
         
        <tbody style="display:none;visibility: false;" disabled="true">
          <tr>
            <td>
              <textarea id="templateBody" jTemplate="yes">
                  <tr id="orderProcessRow_{$T.procs_step_no}" >
                    <td>
                    </td>
                    <td></td>
                    <td>
                      <input type="hidden" name="orderProcesses[{$T.procs_step_no}].procs_step_no" id="orderProcesses.procs_step_no" />
                      <input type="hidden" name="orderProcesses[{$T.procs_step_no}].business_id" value="<%=business_id %>" />
                      <input type="text" name="orderProcesses[{$T.procs_step_no}].procs_step_name" value="{$T.procs_step_name}" maxlength="50"/>
                    </td>
                    <td><!--<input type="text" name="orderProcess.procs_staff_role_id" id="orderProcess.procs_staff_role_id" maxlength="50"/>-->
                      <select name="orderProcesses[{$T.procs_step_no}].procs_staff_role_id">
                        <option value="1" {#if $T.procs_staff_role_id == 1}selected="selected"{#/if}>Role-A</option>
                        <option value="2" {#if $T.procs_staff_role_id == 2}selected="selected"{#/if}>Role-B</option>
                      </select>
                    </td>
                    <td>
                      <a href="javascript:viewJs.deleteMe({$T.procs_step_no})">删除</a>
                    </td>
                  </tr>
              </textarea>
            </td>
          </tr>
        </tbody>

      </TABLE>
    </form>
    </DIV>
    
    
    
  </div>
</div>
