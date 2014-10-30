<%@page import="com.globalwave.system.entity.SessionUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%SessionUser sessionUser = (SessionUser)session.getAttribute(SessionUser.SESSION_PK) ;%>
<div class="frame_top">
    <ul>
        <li class="user">
            <p>欢迎您，<span><%=sessionUser.getStaff().getStaff_name() %></span>!</p>
        </li>
        <!-- 
        <li class="top_news"><a href="#" title="消息">消息</a><span>4</span></li>
         -->
        <li class="top_key"><a href="javascript:openView(9120002, '/biz/staff_changePasswordView.action', '修改密码');" target="mainFrame" title="修改密码">修改密码</a></li>
        <li class="top_logout"><a href="javascript:logout();" title="退出登录">退出登录</a></li>
    </ul>
    
    <div class="top_notice"><b>公告：</b><!-- <span><a href="公告.html" target="mainFrame">关于福利事业</a>[2014/08/26]</span> --></div>
</div>
