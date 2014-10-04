<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<script type="text/javascript">
  $(function(){
      
      var dlhead = $("dl").has("li"); 
      //dlhead.children("dd").hide();
      dlhead.find(".open").hide();
      dlhead.eq(2).children("dd").hide();
      dlhead.eq(2).find(".close").hide();
      dlhead.eq(2).find(".open").show();
      dlhead.children("dt").click(function(){
          $(this).find(".close").toggle();
          $(this).find(".open").toggle(); 
          $(this).next("dd").toggle();
      });
  
      openView(110003, '/biz/product_view.action', '基础商品管理');
  })

</script>
<!--[if IE 6]><script src="images/iepng.js" type="text/javascript"></script> <script type="text/javascript">
        DD_belatedPNG.fix('div, ul, img, li, input , a,span');
    </script><![endif]-->


<div class="FrameMenu" style="width: 180px;">
    <dl>
        <dt><a href="FrameMain.html" target="mainFrame"><img src="images/menu_home.png" />首页</a></dt>
    </dl>
    <div class="menu_line"></div>
    
    <dl>
        <dt><a href="#"><span class="open">展开</span><span class="close">收缩</span><img src="images/menu_ordermanege.png" />业务单管理</a></dt>
        <dd>
            <ul>
                <li><a href="列表页.html" target="mainFrame">业务单处理<b><span>32</span></b></a></li>
                <li><a href="#">业务单流查看</a></li>
            </ul>
        </dd>
    </dl>
    <div class="menu_line"></div>
    
    <dl>
        <dt><a href="#" target="mainFrame"><img src="images/menu_clientmanege.png" />客户管理</a></dt>
    </dl>
    <div class="menu_line"></div>
    
    <dl>
        <dt><a href="#"><span class="open">展开</span><span class="close">收缩</span><img src="images/menu_business.png" />业务管理</a></dt>
        <dd>
            <ul>
                <li><a href="javascript:removeAll();openView(110001, '/biz/business_view.action', '公告管理');">业务管理</a></li>
                <li><a href="javascript:removeAll();openView(110003, '/biz/product_view.action', '基础商品管理');">基础商品管理</a></li>
                <li><a href="#">商品包销售管理</a></li>
            </ul>
        </dd>
    </dl>
    <div class="menu_line"></div>
    
    <dl>
        <dt><a href="#" target="mainFrame"><img src="images/menu_information.png" />决策信息管理</a></dt>
    </dl>
    <div class="menu_line"></div>
    
    <dl>
        <dt><a href="#"><span class="open">展开</span><span class="close">收缩</span><img src="images/menu_setup.png" />系统设置</a></dt>
        <dd>
            <ul>
                <li><a href="#" target="mainFrame">组织结构管理</a></li>
                <li><a href="#" target="mainFrame">角色配置</a></li>
                <li><a href="#">员工管理</a></li>
                <li><a href="#" target="mainFrame">客户资料字段配置</a></li>
                <li><a href="javascript:removeAll();openView(120001, '/biz/notice_view.action', '公告管理');">公告管理</a></li>
            </ul>
        </dd>
    </dl>
    <div class="menu_line"></div>
</div>
