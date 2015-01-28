
$(function() {
	__clientHeight = window.innerHeight||document.documentElement.clientHeight ;
    $("#adminSystem").height((__clientHeight - 120) + "px") ;

	//refreshPage(user) ;
	
	loadMenuLevel1(menu) ;

    $(document).each(function() {
        $(this).tooltip({ 
        	items:'#menu_area div',
        	content: function() {// alert(this.img_url);
                return $("<img/>").attr("src", $("img", this)[0].src); 
            },
            position: {
                my: "center top",
                at: "center bottom+5",
            }
        });
    }) ;
    
    
    $(window).resize(function () {
    	setFrameHeight();
    	reflowValidationForms();
    }) ;

	$("#viewContentDiv").scroll(function() {
		reflowValidationForms();
		
		if (typeof(viewJs.toolbarRelocate) != "undefined") {
			viewJs.toolbarRelocate($("#viewContentDiv").scrollTop());
		}
    });
	
    setFrameHeight();

});

function setFrameHeight() {
	var __clientHeight = window.innerHeight||document.documentElement.clientHeight ;
	var _height = (__clientHeight - 80) + "px";
    $("#menuDiv").height(_height) ;
    $("#viewContentDiv").height(_height) ;
}

function reflowValidationForms() {

	if (typeof(viewJs.validationForms) == "undefined") {
		return ;
	}
	for (var index in viewJs.validationForms) {
		viewJs.validationForms[index].reflow();
	}
}

var g$views = [] ;
var viewJs = null ;
var current = null ;

function openView(id, url_, title, params) {
	if (g$views[id]) {
		showView(id) ;
	} else {
		addView(id, url_, title, params) ;
	}
}

function addView(id, url_, title, params) {
	g$views[id] = {id:id, title:title} ;
	
    $("#viewContentDiv").append('<div id="view_'+id+'"><img src="'+root+'/images/loading.gif" /></div>') ;
    g$views[id].view="#view_" + id ;
    
    $("#myWorkSpace").append('<a href="javascript:showView('+id+');" id="a_'+id+'" tabindex="-1">'+title+'</a>'); 
    
    loadView(id, url_, params) ;
}

function loadView(id, url_, params) {
	
	if (!params) {
		params = {} ;
	}
	
	params.view_id=id ;
	
	$.ajax({
        type: "post",
        url: root + url_,
        dataType:"html",
        data:params,
        beforeSend: function(XMLHttpRequest){
            showLoading();
        },
        success: function(data, textStatus, jqXHR){
        	$(g$views[id].view).html(jqXHR.responseText );
            
            var reponse = jQuery(jqXHR.responseText);
            var reponseScript = reponse.filter("script");
            $.each(reponseScript, function(idx, val) { 
                try{
                    eval(val.text);
                }catch(e){
                    alert("Js Run Error-" + e.message+ "-\n" + val.text) ;
                } 
            });
            try{
            	//viewJs = eval("g$v"+id) ;
                g$views[id].viewJs = eval("g$v"+id) ;
                //g$views[id].viewJs.init(); // init is move to show function
            }catch(e){
            	alert("Init Error-" + e.message) ;
            }
            
        	showView(id) ;
            
        },
        complete: function(XMLHttpRequest, textStatus){
            hideLoading();
        },
        error: function(jqXHR, textStatus, errorThrown) {

            $(g$views[id].view).append( jqXHR.responseText );
            showView(id) ;
        }
    });
}

function showView(id) {
    if (viewJs){// hide current view 
        viewJs.onBeforeHide() ;    
        	
        $(viewJs.view).hide() ;
        
        viewJs.onAfterHide() ;   
    }


    viewJs = g$views[id].viewJs ;
    
    if (!viewJs.isInited) {
    	viewJs.init();;
    	viewJs.isInited = true ;
    }
    
    viewJs.onBeforeShow() ;
    
    $(g$views[id].view).show() ;
    current = id ;
    $("#sub_title_label").html("&gt;&nbsp;" + g$views[id].title) ;
    
    viewJs.onAfterShow() ;
    
    $("#viewContentDiv").scrollTop(0);
}

function removeView(id) {
	if (typeof(id) == "undefined" || id == null) {
		if (typeof(current) == "undefined") return ;
		if (current == null) return ;
		id = current ;
	}

	viewJs.destroy() ;
	$("#sub_title_label").html("");
    $(g$views[id].view).remove() ;
    $("#a_"+id).remove() ;
	//g$views[id] = false ;
	delete g$views[id] ;
	id = null;
	current = null;
	
	for (_id in g$views) {
		if (g$views[_id]) {
			showView(_id) ;
			break ;
		}
	}
}

function removeAll() {
	while(current != null) {
		removeView() ;
	}
}

function login() {
    ajax(
            root + "/system/user_login.action", 
            $("#loginForm").serialize(),
            function(data, textStatus){
                //alert(data.message) ;
                if (data.code == "0") {
                    $("#login_page").hide() ;
                    $("#admin_page").show() ;
                    user = data.data.user ;
                    refreshPage(user) ;
                }
            },
            false
        );
}

function refreshPage(user) {
	var msg = null ;
	if (user == null) {
	    msg = '请登录系统' ;
	} else {
		msg = '<span id="userName">'+user.name_cn+'</span>，欢迎使用系统<a href="javascript:logout();" id="btnLogout">退出</a><a href="javascript:changePassword()">修改密码</a>' ;
	}
    
	$("#userInfo_area").html(msg) ;

    if (user == null) {
        $("#login_page").show() ;
        $("#footerDiv").show() ;
        $("body").addClass("body_login");
        $("#admin_page").hide() ;
        $("#menu_area").hide() ;
    } else {
        $("#login_page").hide() ;
        $("#footerDiv").hide() ;
        $("body").removeClass("body_login");
        $("#admin_page").show() ;
        $("#menu_area").show() ;
    }
}
var menu = [] ;
var menu1 = [
            {id: 1, pro_menu_id:null, label:'我的订单'                           , url:'#C9E1F8'                               ,icon:'/model/css/i_my.png', level:1, pid:0, ext_s1:'', ext_s2:''}, 
            {id: 2, pro_menu_id:1   , label:'menu_110'                           , url:'#'                                     ,icon:'', level:2, pid:0, ext_s1:'', ext_s2:''}, 
            {id: 3, pro_menu_id:2   , label:'menu_112'                           , url:'#'                                     ,icon:'', level:3, pid:0, ext_s1:'', ext_s2:''}, 
            {id: 4, pro_menu_id:null, label:'配置管理'                               , url:'#C911F8'                               ,icon:'/model/css/i_report.png', level:1, pid:0, ext_s1:'', ext_s2:''}, 
            {id: 5, pro_menu_id:4   , label:'系统管理'                           , url:'#'                                     ,icon:'', level:2, pid:0, ext_s1:'', ext_s2:''}, 
            {id: 6, pro_menu_id:5   , label:'用户管理'                           , url:'/system/user_view.action'              ,icon:'', level:3, pid:0, ext_s1:'', ext_s2:''}, 
            {id: 7, pro_menu_id:5   , label:'角色管理'                           , url:'/system/role_view.action'              ,icon:'', level:3, pid:0, ext_s1:'', ext_s2:''},
            {id: 8, pro_menu_id:4   , label:'menu_480'                           , url:'#'                                     ,icon:'', level:2, pid:0, ext_s1:'', ext_s2:''}, 
            {id: 9, pro_menu_id:8   , label:'menu_489'                           , url:'#'                                     ,icon:'', level:3, pid:0, ext_s1:'', ext_s2:''}, 
            {id:10, pro_menu_id:8   , label:'menu_480'                           , url:'#'                                     ,icon:'', level:3, pid:0, ext_s1:'', ext_s2:''}, 
            {id:11, pro_menu_id:null, label:'下订单'                             , url:'#C9E111'                               ,icon:'/model/css/i_order.png', level:1, pid:0, ext_s1:'', ext_s2:''}, 
            {id:12, pro_menu_id:11  , label:'menu_810'                           , url:'#'                                     ,icon:'', level:2, pid:0, ext_s1:'', ext_s2:''}
           ] ;

var menuLevel2 = null ;
var menuLevel3 = null ;
function loadMenuLevel1(items) {
    menuLevel2 = [] ;
    menuLevel3 = [] ;
    
    $("#menu_area").html("") ;
    
    if (isNull(items)) {
        return ;
    }
    
    for (var i = 0 ; i < items.length ; i ++) {
        var item = items[i] ;
        if (item.level == 1) {
            $("#menu_area").append(
                    '<div style="background-color: '+item.url+';padding-left: 20px;padding-right: 30px" onclick="loadMenu('+item.id+');">'
                  + '  <img src="<%=root %>'+item.icon+'" style="display:none"/><span>'+item.label+'</span>'
                  + '</div>'
            ) ;
            continue ;
        }

        if (item.level == 2) {
        	if (!menuLevel2[item.pro_menu_id]) {
        		menuLevel2[item.pro_menu_id] = [] ;
        	}
        	var ml2 = menuLevel2[item.pro_menu_id] ;
        	ml2[ml2.length] = item;
            continue ;
        }

        if (!menuLevel3[item.pro_menu_id]) {
        	menuLevel3[item.pro_menu_id] = [] ;
        }
        var ml3 = menuLevel3[item.pro_menu_id] ;
        ml3[ml3.length] = item;
    }
    
}

function loadMenu(id) {
	
	$("#adminNav li").each(function (i, e) {
		if (i == 0 || i == 1) {
			return ;
		}
		
		$(e).remove() ;
	}) ;
	
	var items = menuLevel2[id] ;
    
    if (isNull(items)) {
        return ;
    }
	
    for (var i = 0 ; i < items.length ; i ++) {
        var item = items[i] ;       

        var div_html = "" ;
        var items_3 = menuLevel3[item.id] ;
        
        if (items_3 != null) {
            for (var j = 0 ; j < items_3.length ; j ++) {
                var item_3 = items_3[j] ;
                div_html += '<a href="javascript:openView('+item_3.id+', \''+item_3.url+'\', \''+item_3.label+'\')" tabindex="-1">'+item_3.label+'</a>' ;
            }
        }
        
        $("#adminNav").append('<li><span href="#">'+item.label+'</span><div>' + div_html + '</div></li>') ;
    }
}


var isMax = false ;
var headerHeight = 0 ;
var navWidth = 0 ;
function maximize() {
	if (headerHeight == 0) {
		headerHeight = $("#header_area").height();
		navWidth = $("#admin_nav_area").width() ;
	}
    if (isMax) {
        $("#adminSystem").height($("#adminSystem").height() - headerHeight) ;
        $("#header_area").show() ;
        $("#admin_nav_area").show() ;
        $("#admin_action_area .admin_action_inner").css("marginLeft", navWidth) ;
        $("#maxBtn").addClass("ui-icon-arrowthickstop-1-w") ;
        $("#maxBtn").removeClass("ui-icon-arrowthickstop-1-e") ;
    } else {
        $("#adminSystem").height($("#adminSystem").height() + headerHeight) ;
        $("#header_area").hide() ;
        $("#admin_nav_area").hide() ;
        $("#admin_action_area .admin_action_inner").css("marginLeft", 0) ;
        $("#maxBtn").addClass("ui-icon-arrowthickstop-1-e") ;
        $("#maxBtn").removeClass("ui-icon-arrowthickstop-1-w") ;
    }
    isMax = !isMax ;
} ;






baseView_af24332idihy00p2jww.toolbarRelocate=function(topOffset) {
	E$("toolbarAutoScroll").css("top", topOffset);
};