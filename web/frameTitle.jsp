<%@page import="com.globalwave.system.entity.SessionUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%SessionUser sessionUser = (SessionUser)session.getAttribute(SessionUser.SESSION_PK) ;%>

<script>
$(function() {
	
ajax(
    root + "/biz/notice_getNoticesByDate.action", 
    {},
    function(data){
    	var notices = "";
    	
    	noticeMap = data.list ;
    	
    	$(data.list).each(function(i, elem) {
    		noticeMap[elem.id] = elem ;
    		notices += "<a href='javascript:showNoticeDetail(" + elem.id + ");'>" + fmt.maxlen(elem.notice_timestamp,10) + ":" + elem.notice_subject + "</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    	});

    	$("#noticesDiv").html(notices);
    }
);


});

var noticeMaps = [];

function showNoticeDetail(id) {
	var elem = noticeMap[id];
    $("#subjectTd").html(elem.notice_subject + '&nbsp;&nbsp;' + elem.notice_timestamp);
    $("#contentTd").html(elem.notice_content);
    
    //alert(noticeMap[id].notice_subject);
	$("#noticeDetailWindowDiv").dialog({
        height: 360,
        width:500,
        modal: true
    });
	
	return true ;
}


</script>

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
    
    <div class="top_notice">
      <span style="height: 20px;line-height: 20px;" ><b>公告：</b></span>
      <span>
        <marquee scrolldelay="5" scrollamount="2" style="width: 400px;height: 20px;line-height: 20px;" direction="left" loop="-1" onmousemove="this.stop()" onmouseout="this.start()" id="noticesDiv"></marquee>
      </span>
    </div>
</div>

<div id="noticeDetailWindowDiv" style="display: none;" title="公告详细">
  <div class="main_userinfo" >
	  <div class="title" id="subjectTd">
	  </div>
	  <div id="contentTd" style="min-height: 200px;margin: 10px;">
	  </div>
  </div>
</div>