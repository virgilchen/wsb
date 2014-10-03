<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head_session_check.jsp" %>
<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/business_list.action" ,
    create_url:root + "/biz/business_create.action" ,
    update_url:root + "/biz/business_update.action" ,
    get_url:root + "/biz/business_get.action" ,
    delete_url:root + "/biz/business_delete.action" ,
    entityName:"business",
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("businessSO.pageIndex") ;
        
        //fillOptions({id:"business.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"businessSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("businessTB", {height:"400px"}) ;

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
      <B>业务列表</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0">
        <input name="businessSO.pageIndex" id="businessSO.pageIndex" value="1" type="hidden" />
        <input name="businessSO.pageSize" id="businessSO.pageSize" value="10" type="hidden" />
        <table width="100%" >
          <tr>
           <td style="width:100px;" >业务名称：</td>
           <td style="width:100px;">
             <input class=mg_r name="businessSO.business_name" value="" type="text" />
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
      <TABLE border=0 width="100%" id="businessTB" title="通知列表">
        <thead>
          <TR>
			<TH width="20px"></TH>
			<TH>业务名称</TH>
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
                    <td style="text-align: left;">{$T.business_name}</td>
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
      <B>新增业务</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
    </DIV>


    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="business.id" id="business.id"/>
      <input type="hidden" name="business.version_id" id="business.version_id"/>
  
      <table width="100%" border="0">
        <tr valign="top">
          <td valign="top" width="15%">
          </td>
          <td width="45%">
            <table width="100%" border="0">
              <tr>
                <th width="25%">业务名称：</th>
                <td><input type="text" name="business.business_name" id="business.name_cn" maxlength="50"/></td>
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