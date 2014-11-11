<%@page import="com.globalwave.system.entity.SessionUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%!
public String showOrNot(SessionUser sessionUser, short[] pids) {
	if (sessionUser.hasPrivilege(pids)) {
		return "";
	} else {
		return " style=\"display:none;\"";
	}
}
%>
<script type="text/javascript">
  $(function(){
      
      var dlhead = $("dl").has("li"); 
      //dlhead.children("dd").hide();
      dlhead.find(".open").hide();
      dlhead.eq(2).children("dd").hide();
      dlhead.eq(2).find(".close").hide();
      dlhead.eq(2).find(".open").show();
	  
      dlhead.eq(3).children("dd").hide();
      dlhead.eq(3).find(".close").hide();
      dlhead.eq(3).find(".open").show();
      dlhead.children("dt").click(function(){
          $(this).find(".close").toggle();
          $(this).find(".open").toggle(); 
          $(this).next("dd").toggle();
      });
  
      openView(100001, '/biz/order_myTasksView.action', '业务单处理');
  });

</script>
<!--[if IE 6]><script src="images/iepng.js" type="text/javascript"></script> <script type="text/javascript">
        DD_belatedPNG.fix('div, ul, img, li, input , a,span');
    </script><![endif]-->


<div class="FrameMenu">
    <dl>
        <dt><a href="index.jsp"><img src="images/menu_home.png" />首页</a></dt>
    </dl>
    <div class="menu_line"></div>
    
    <dl>
        <dt><a href="#"><span class="open">展开</span><span class="close">收缩</span><img src="images/menu_ordermanege.png" />业务单管理</a></dt>
        <dd>
            <ul>
                <li <%=showOrNot(sessionUser, new short[]{(short)10001}) %>><a href="javascript:removeAll();openView(10001, '/biz/order_myTasksView.action', '业务单处理');">业务单处理<!-- <b><span>32</span></b> --></a></li>
                <!-- 
                <li <%=showOrNot(sessionUser, new short[]{(short)1}) %>><a href="javascript:removeAll();openView(100003, '/biz/order_openView.action', '业务发起');">业务发起</a></li>
                 -->
                <li <%=showOrNot(sessionUser, new short[]{(short)10004}) %>><a href="javascript:removeAll();openView(10004, '/biz/order_searchView.action', '业务单流查看');">业务单流查看</a></li>
            </ul>
        </dd>
    </dl>
    <div class="menu_line"></div>
    
    <dl>
        <dt><a href="#"><span class="open">展开</span><span class="close">收缩</span><img src="images/menu_clientmanege.png" />客户管理</a></dt>
        <dd>
            <ul>
                <li <%=showOrNot(sessionUser, new short[]{(short)11001}) %>><a href="javascript:removeAll();openView(11001, '/biz/customer_view.action', '客户列表');">客户列表</a></li>
                <li <%=showOrNot(sessionUser, new short[]{(short)11005}) %>><a href="javascript:removeAll();openView(11005, '/biz/member_view.action', '会员列表');">会员列表</a></li>
            </ul>
        </dd>
    </dl>
    <div class="menu_line"></div>
    
    <dl>
        <dt><a href="#"><span class="open">展开</span><span class="close">收缩</span><img src="images/menu_business.png" />业务管理</a></dt>
        <dd>
            <ul>
                <li <%=showOrNot(sessionUser, new short[]{(short)12001}) %>><a href="javascript:removeAll();openView(12001, '/biz/business_view.action', '业务管理');">业务管理</a></li>
                <li <%=showOrNot(sessionUser, new short[]{(short)12003}) %>><a href="javascript:removeAll();openView(12003, '/biz/product_view.action', '基础商品管理');">基础商品管理</a></li>
                <li <%=showOrNot(sessionUser, new short[]{(short)12004}) %>><a href="javascript:removeAll();openView(12004, '/biz/productPack_view.action', '商品包管理');">商品包管理</a></li>
            </ul>
        </dd>
    </dl>
    <div class="menu_line"></div>
    
    <dl <%=showOrNot(sessionUser, new short[]{(short)14001}) %>>
        <dt><a href="javascript:removeAll();openView(14001, '/biz/recmdtInventory_view.action', '决策信息管理');"><img src="images/menu_information.png" />决策信息管理</a></dt>
    </dl>
    <div <%=showOrNot(sessionUser, new short[]{(short)14001}) %> class="menu_line"></div>
    
    <dl <%=showOrNot(sessionUser, new short[]{(short)19002, (short)19003, (short)19004}) %>>
        <dt><a href="#"><span class="open">展开</span><span class="close">收缩</span><img src="images/menu_setup.png" />系统设置</a></dt>
        <dd>
            <ul>
                <li <%=showOrNot(sessionUser, new short[]{(short)19002}) %>><a href="javascript:removeAll();openView(19002, '/biz/companyOrg_view.action', '组织结构管理');">组织结构管理</a></li>
                <li <%=showOrNot(sessionUser, new short[]{(short)19003}) %>><a href="javascript:removeAll();openView(19003, '/biz/staffRole_view.action', '角色配置');">角色配置</a></li>
                <li <%=showOrNot(sessionUser, new short[]{(short)19004}) %>><a href="javascript:removeAll();openView(19004, '/biz/staff_view.action', '员工管理');">员工管理</a></li>
                <!-- 
                <li><a href="#" target="mainFrame">客户资料字段配置</a></li>
                 -->
                <li <%=showOrNot(sessionUser, new short[]{(short)20001}) %>><a href="javascript:removeAll();openView(20001, '/biz/notice_view.action', '公告管理');">公告管理</a></li>
            </ul>
        </dd>
    </dl>
    <div <%=showOrNot(sessionUser, new short[]{(short)19002, (short)19003, (short)19004}) %> class="menu_line"></div>
</div>
