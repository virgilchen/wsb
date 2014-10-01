<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String view_id=request.getParameter("view_id");
%>

<script>

var g$v<%=view_id%> = $.extend(newView(), {
    view:document.getElementById("view_<%=view_id%>"), 
    id:<%=view_id%>,
    list_url:root+"/biz/notice_list.action" ,
    create_url:root + "/biz/notice_create.action" ,
    update_url:root + "/biz/notice_update.action" ,
    get_url:root + "/biz/notice_get.action" ,
    delete_url:root + "/biz/notice_delete.action" ,
    entityName:"notice",
    
    init:function (){
        //this.initSelect() ;
        this.pageIndex = E("noticeSO.pageIndex") ;
        
        //fillOptions({id:"notice.record_status", dictName:"CM.status", firstLabel:"请选择..."}) ;// 改为字典取值
        //fillOptions({id:"noticeSO.record_status", dictName:"CM.status", firstLabel:"全部"}) ;
        
        //this.initDataGrid("noticeTB", {height:"400px"}) ;
        
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
      <B>公告列表</B> 
      <DIV class="main_tt_right fr">
        <A class=blue href="javascript:viewJs.toAdd();">添加</A>
        <A class=blue href="javascript:viewJs.toEdit();">编辑</A>
        <A class=orange href="javascript:viewJs.toDelete();">删除</A>
      </DIV>
    </DIV>
  
    <DIV class="main_search">
      <form method="post" id="sForm" name="sForm" onsubmit="return false;" style="margin: 0">
        <input name="noticeSO.pageIndex" id="noticeSO.pageIndex" value="1" type="hidden" />
        <input name="noticeSO.pageSize" id="noticeSO.pageSize" value="50" type="hidden" />
        <table width="100%" >
          <tr>
           <td style="width:60px;" >标题：</td>
           <td style="width:100px;">
             <input class=mg_r name="noticeSO.notice_subject" value="" type="text" />
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
      <TABLE border=0 width="100%" id="noticeTB" title="通知列表">
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
                    <td>{$T.notice_subject}</td>
                    <td>{$T.notice_timestamp}</td>
                  </tr>
              </textarea>
            </td>
          </tr>
        </tbody>

      </TABLE>
    </DIV>





    <DIV class="page_wrap clearfix">
      <DIV class=paginator>
        <SPAN class=page-start>＜上一页</SPAN> 
        <SPAN class=page-this>1</SPAN> 
        <A href="#">2</A> 
        <A href="#">3</A> 
        <A href="#">4</A> 
        <A href="#">5</A> 
        <A href="#">6</A> 
        <A href="#">7</A> 
        <A href="#">8</A> 
        <SPAN>...</SPAN> 
        <A href="#">20</A> 
        <A class=page-next href="#">下一页＞</A> 
      </DIV>第1/3页，共30条记录 
    </DIV>

    
  </div>
    
  <div id="editDiv" style="display:none;" >
    
    <DIV class=main_title>
      <B>新增公告</B> 
      <DIV class="main_tt_right fr">
        <A class=orange href="javascript:viewJs.save();">保存</A>
        <A class=blue href="javascript:viewJs.toSearch();">取消</A>
      </DIV>
    </DIV>


    <form method="post" id="eForm" name="eForm" onsubmit="return false;" style="margin: 0" class="main_form">
      <input type="hidden" name="notice.id" id="notice.id"/>
      <input type="hidden" name="notice.version_id" id="notice.version_id"/>
  
      <table width="100%" border="0">
        <tr valign="top">
          <td valign="top" width="15%">
          </td>
          <td width="45%">
            <table width="100%" border="0">
              <tr>
                <th width="25%">公告编号：</th>
                <td><input type="text" name="notice.name_cn" id="notice.name_cn" maxlength="50"/></td>
              </tr>
              <tr>
                <th>标题：</th>
                <td><input type="text" name="notice.notice_subject" maxlength="50"/></td>
              </tr>
              <tr>
                <th>内容：</th>
                <td>
                  <textarea name="notice.notice_content" required="required"  style="width: 100%;height: 80px;"></textarea>
                </td>
              </tr>
              <tr>
                <th>发表时间：</th>
                <td><input type="text" name="notice.notice_timestamp" required="required" maxlength="12"/></td>
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
