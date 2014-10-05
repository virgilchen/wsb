<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String pagination_id = (String)request.getAttribute("pagination_id") ;
%>
<div class="page_wrap clearfix">
    <label id="paginationInfo">总记录数为0条，当前为第1页，共0页&nbsp;&nbsp;&nbsp;&nbsp;</label>
    
    <div class=paginator id='<%=(pagination_id==null)?"pagination":pagination_id %>'>
      <a href="javascript:viewJs.preview();">&lt;上一页</a>
      <span id="paginationNumbers"></span>
      <a href="javascript:viewJs.next();">下一页&gt;</a>
    </div>
</div>