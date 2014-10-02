<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String pagination_id = (String)request.getAttribute("pagination_id") ;
%>
<div class="page_wrap clearfix">
    <label id="paginationInfo"><%=i18n.text("Page 1 of 0, Records 0&nbsp;&nbsp;&nbsp;&nbsp;", "总记录数为0条，当前为第1页，共0页") %>&nbsp;&nbsp;&nbsp;&nbsp;</label>
    
    <div class=paginator id='<%=(pagination_id==null)?"pagination":pagination_id %>'>
      <%--
      <a href="javascript:viewJs.first();"><%=i18n.text("First", "第一页") %></a>
      --%>
      <a href="javascript:viewJs.preview();"><%=i18n.text("Previous", "<上一页") %></a>
      <span id="paginationNumbers"></span>
      <a href="javascript:viewJs.next();"><%=i18n.text("Next", "下一页>") %></a>
      <%--
      <a href="javascript:viewJs.last();"><%=i18n.text("Last", "尾页") %></a>
      --%>
    </div>
</div>
<%--
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
     --%>