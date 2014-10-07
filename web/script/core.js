var i18n = {} ;

i18n.data = {} ;

i18n.data = {
"required":{cn:"请输入[{$T.p1}]", en:"[{$T.p1}] is required!"}, 
"requiredSelect":{cn:"请选择[{$T.p1}]", en:"[{$T.p1}] is required!"}, 
"paginationInfo":{cn:"总记录数为{$T.p1}条，当前为第{$T.p2}页，共{$T.p3}页&nbsp;&nbsp;&nbsp;&nbsp;", en:"Page {$T.p2} of {$T.p3}, Records {$T.p1}&nbsp;&nbsp;&nbsp;&nbsp;"}
};


i18n.msg=function (key, p1, p2, p3, p4, p5) {
	var v = i18n.data[key] ;
	if (typeof(v) == "undefined") {
		return null ;
	}

	var ret = null ;
	if (language == "en") {
		ret = v.en ;
	} else {
		ret = v.cn ;
	}
	
	if (typeof(p1) == "undefined") {
		return ret ;
	} else {
		var params = {p1:p1, p2:p2, p3:p3, p4:p4, p5:p5} ;
		return parse(ret, params) ;
	}
};

i18n.message = i18n.msg ;

i18n.text=function (textEN, textCN) {
	if (language == "en") {
		return textEN ;
	} else {
		return textCN ;
	}
};

String.prototype.replaceAll=function(s1,s2){   
    return this.replace(new RegExp(s1,"gm"),s2);   
};

String.prototype.capitalize=function(isCapFirst) {
	var s = this ;
	var ret = "" ;
	var isCap = (typeof(isCapFirst) == "undefined")?true:isCapFirst ;
	
	for (var i = 0 ; i < s.length ; i ++) {
		var c = s.charAt(i) ;
		if (c == "_") {
			isCap = true ;
			continue ;
		}
		
		if (isCap) {			
			ret += c.toUpperCase() ;
			isCap = false ;
		} else {
			ret += c ;
		}
	}
	return ret;
} ;

/*
$.tools.validator.localize("fi", {
	'*'			: 'Virheellinen arvo',
	':email'  	: 'Virheellinen s&auml;hk&ouml;postiosoite',
	':number' 	: 'Arvon on oltava numeerinen',
	':url' 		: 'Virheellinen URL',
	'[max]'	 	: 'Arvon on oltava pienempi, kuin $1',
	'[min]'		: 'Arvon on oltava suurempi, kuin $1',
	'[required]'	: 'Kent&auml;n arvo on annettava'
});
*/
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};

var fmt = {
  dd:function (_v) {
	  return (_v).toFixed(2) ;
  },
  maxlen:function(_v, _max) {
	  if (_v == null) {
		  return "" ;
	  } else {
		  if (_v.length > _max) {
			  return _v.substring(0, _max + 1) ;
		  } else {
			  return _v ;
		  }
	  }
  }
};

function enter(_element, _callback) {
	var $e = E$(_element) ;
	$e[0].enter = _callback ;
	/*
	$e.on("keydown", function (e) {
	    if (e.which == 13) {
		    _callback();
	    }
	});*/
}

function dVal(dictName, field, pk) {
	return find(g$dict.get(dictName), field, pk) ;
}

/**
 * 从列表中，根据主键查找元素后，返回对应字段的值
 * 
 * @param datas
 * @param field
 * @param pk
 * @returns
 */
function find(datas, field, pk) {

	if (!datas || datas == null) {
		return "" ;
	}
	
	for (var i = 0 ; i < datas.length ; i ++) {
		var isMe = true ;
		var row = datas[i] ;
		for (var key in pk) {
			if (row[key] != pk[key]) {
				isMe = false ;
				break; 
			}
		}
		
		if (isMe) {
			return row[field] ;
		}
	}
	return "" ;
}

/**
 * 对数据按条件进行过滤
 * 
 * @param datas
 * @param filterValues
 * @param filterOpers
 * @returns {Array}
 */
function filter(datas, filterValues, filterOpers) {
	var result = [] ;
	
	if (typeof(filterOpers) == "undefined") {
		filterOpers = {} ;
	}
	
	if (datas == undefined) {
		return result;
	}
	
	for (var p in filterValues) {
		if (!$.isArray(filterValues[p]) && !$.isFunction(filterOpers[p])) {
			filterValues[p] = [filterValues[p]] ;
		}
		
		if (typeof(filterOpers[p]) == "undefined") {
			filterOpers[p] = "in";
		}
	}
	
	for (var i = 0 ; i < datas.length ; i ++) {
		var data = datas[i] ;
		var isEqual = true ;
		for (var p in filterValues) {
			//if (data[p] != filterValues[p]) {
			var op = filterOpers[p] ;
			var val = filterValues[p] ;
			
			if (op == "in" && $.inArray(data[p], val) < 0){
				isEqual = false ;
				break ;
			} else if (op == "not in" && $.inArray(data[p], val) >= 0){
				isEqual = false ;
				break ;
			} else if ($.isFunction(op) && !op(data[p], val)) {
				isEqual = false ;
				break ;
			}
		}
		
		if (isEqual) {
			result[result.length] = data ;
		}
	}
	
	return result ;
}

/**
 * 读取元素的值
 * 
 */

function v(id, _value) {
	var e = document.getElementById(id) ;
	if (e == null) {
    	e = document.getElementsByName(id)[0];
	}
	if (typeof(_value)!="undefined") {
		e.value = _value ;
	}
	return e.value ;
}

function V(id, _value) {
	var e = E(id) ;
	
	if (typeof(_value)!="undefined") {
		e.value = _value ;
	}
	return e.value ;
}

function isNull(_var) {
    if (typeof(_var) == "undefined") {
    	return true ;
    }
    
    if (_var == null) {
    	return true ;
    }
}

/**
 * 取得元素对象
 */
function e(id){
	if (typeof(id)=="string") {
	    var ret = document.getElementById(id) ;
	    if (ret == null) {
	    	ret = document.getElementsByName(id);
	    	if (ret.length > 0) {
	    		ret = ret[0] ;
	    	}
	    }
	    return ret;
	}
    return id ;
}

function E(id){
	return E$(id)[0] ;
}

function E$(id){
	if (typeof(id)=="string") {		
		if (typeof(viewJs) == "undefined" || viewJs == null) {
			return $("#"+id.replaceAll("\\.", "\\\."));
		} else {		
			return $("#"+id.replaceAll("\\.", "\\\."), viewJs.view) ;
		}
    } 
	
	return id ;// return as object, not a string
}


var g$LS = {

        //5 window.localStorage.length;
        //6 window.localStorage.key( i );
        
	isEnabled : function () {
		var ret = (typeof window.localStorage != 'undefined') ;
		/*
		if (!ret) {
			alert("你的浏览器不支持本地缓存，建议使用更高版本的浏览器（如：chrome26+、IE8+)，\n以达到更好的体验效果！") ;
		}
		*/
		return ret ;
	},
	
	isReady:function () {
		return false ;
	},
    
	item:function (key, value) {
        if (!this.isEnabled()) {
            return ;
        }
        
        if (typeof(value) == "undefined") {
        	return this.getItem(key) ;
        } else {
            this.setItem(key, value) ;
        }
        
	},
	
    getItem:function (key ) {
    	if (!this.isEnabled()) {
    		return ;
    	}
    	
    	return JSON.parse(window.localStorage.getItem( key ));
    }, 
    
    get:function (key ) {
    	return this.getItem(key) ;
    },
    
    setItem:function (key, value) {
        if (!this.isEnabled()) {
            return ;
        }
        window.localStorage.setItem( key, JSON.stringify(value) );
    }, 
    
    put:function (key, value) {
        this.setItem(key, value) ;
    }, 
    
    removeItem:function (key ) {
        if (!this.isEnabled()) {
            return ;
        }
        window.localStorage.removeItem( key );
    },
    
    remove:function (key ) {
        this.removeItem( key );
    }, 
    
    clear:function () {
        if (!this.isEnabled()) {
            return ;
        }
        window.localStorage.clear();
    }
	
};

function validateAll(_ids) {
	for (var i = 0 ; i < _ids.length ; i ++) {
		if (!validate(_ids[i])) {
			return false;
		}
	}
	return true ;
}
function validate(_id, parent, _label) {
	var obj = typeof(_id) != "string"?_id:$("#"+_id.replaceAll("\\.", "\\\."), parent);
	var isRequired = obj.attr("required") ;
	var label = typeof (_label) == "undefined" ? obj.attr("label"): _label ;

	if (label == null) {
		label = obj.attr("placeholder") ;
	}
	if (label == null) {
		label = obj.attr("title") ;
	}
	
	if (isRequired == null) {
		isRequired = false ;
	} 
	
	if (isRequired != false) {
		if (obj.val() == "") {
			if (obj.is("select")) {
		 		//alert("请选择" + label) ;
				alert(i18n.msg("required", label)) ;
		 		obj.focus() ;
		 		return false ;
			} else {
		 		//alert("请输入" + label) ;
				alert(i18n.msg("required", label)) ;
		 		obj.focus() ;
		 		return false ;
			}	
		}
	}
	
	return true ;
}

function parse(template, params) {
	return $.processTemplateToText($.createTemplate(template), params, null) ;
}

function copy(_src, _properties, _desc) {
	var d = _desc?_desc:{} ;
	for (var i = 0 ; i < _properties.length ; i ++){
		var p = _properties[i] ;
		d[p] = _src[p];
	}
	return d ;
}

function newView() {
	return $.extend({}, baseView_af24332idihy00p2jww) ;
}


function logout() {
    ajax(
            root + "/system/user_logout.action", 
            null,
            function(data, textStatus){
                if (data.code == "0") {
                	user = null ;
                	//refreshPage(user) ;
                	window.location=root+"/index.jsp" ;
                }
            }
        );
}

var baseView_af24332idihy00p2jww = {
	view:null,
	state:"s",//state(s:Search;a:Add;e:Edit;d:Delete)
	preState:null,
	lastIndex:1,
	pageIndex:null,
    entityName:null,
    convertors:null,
    selected:null,
    selecteds:null,
    eFormInitData:{},
    proIdName:null,// 父标识名字，用于树型列表
    idName:null,
    idsName:null,
    entity:null,

    /**
     * 页面元素及切换的控制描述
     */
	states:{
	    blocks:['listDiv','pagination','editDiv'],
	    s:{
	    	blocks:['listDiv','pagination'],
	    	to:{},// 转移限制
	    	onShow:function(_view, _state) {
	            _view.focusSearchForm() ;
	    	},
	    	onHide:function(_view, _state) {
	    		_view.resetSearchForm();
	    	}
	    },
	    
	    a:{
	    	blocks:['editDiv'],
	    	to:{
	    		e:function() {
	    			alert("请先选择要编辑的记录！") ;
	    			return false ;
	    		},

	    		d:function() {
	    			alert("新增记录不能被删除！");
	    			return false ;
	    		}
	    	},// 转移限制
	    	onShow:function(_view, _state) {
	    		if (this.onBeforeShow) {
	    			this.onBeforeShow(_view, _state) ;
	    		}
	            V(_view.entityName+".id", "") ;  
	            _view.focusEditForm() ;
	    	},
	    	onHide:function(_view, _state) {
	    		_view.resetEditForm();
	    	}
	    },
	    
	    e:{
	    	blocks:['editDiv'],
	    	to:{
	    		a:function() {
	    			return confirm("是否放弃修改的内容，进行新建操作？") ;
	    		}
	    	},// 转移限制
	    	onShow:function(_view, _state) {
	    		if (this.onBeforeShow) {
	    			this.onBeforeShow(_view, _state) ;
	    		}
	            _view.focusEditForm() ;
	    	    //_view.get(_view.selected.value) ;
	    	},
	    	onHide:function(_view, _state) {
	    		_view.resetEditForm();
	    	}
	    },
	    
	    d:{
	    	blocks:false,
	    	to:{
	    	},// 转移限制
	    	onShow:function(_view, _state) {
	    	    //_view.get(-1) ;
	    	},
	    	onHide:function(_view, _state) {
	    		//_view.resetEditForm();
	    	}
	    }
	},
	
	/**
	 * 主要负责页面元素的控制，不涉及业务数据处理
	 * 
	 * @param _NewState
	 * @returns {Boolean}
	 */
	toPage:function(_NewState) {
		if (this.state == _NewState) {
			return false;
		}
		// state is enterable?
		var _old = this.states[this.state] ;
		var enterable = _old.to[_NewState] ;
		
		if (enterable) {
			if ($.isFunction(enterable)) {
				enterable = enterable();
			}
			
			if (!enterable) {
				return false;
			}
		}
		
		// -- render page
		var _new = this.states[_NewState] ;
		if (_new.blocks) {// 如果没有定义（如：删除），无需要切换显示块
			var isShoweds = [] ;
			for (var i = 0 ; i < _new.blocks.length ;  i ++) {
				if (!E$(_new.blocks[i]).is(":hidden")) {
					isShoweds[_new.blocks[i]] = true ;
				}
			}
			
			// hide all block
			for (var i = 0 ; i < this.states.blocks.length ;  i ++) {
				var isShowed = isShoweds[this.states.blocks[i]] ;
				if (typeof(isShowed)!= "undefined" && isShowed) {
					continue ;
				}
				E$(this.states.blocks[i]).hide();
			}
			
			// show block
			for (var i = 0 ; i < _new.blocks.length ;  i ++) {
				var isShowed = isShoweds[_new.blocks[i]] ;
				if (typeof(isShowed)!= "undefined" && isShowed) {
					continue ;
				}
				E$(_new.blocks[i]).show();
			}
		}
		
		// -- state 
		// old state on hide
		_old.onHide(this, _old) ;
		
		// new state on show
		_new.onShow(this, _new) ;
		
		// show tab name
		this.showBlock(_NewState) ;
		
		return true;
	},
	
	
	/**
	 * 显示页面中的一个块
	 * 
	 * @param tagId_
	 */
    showBlock : function(tagId_) {

    	this.preState = this.state ;
    	this.state = tagId_ ;

        $("#horNav li", this.view).each(function () {
            if ($("span", this).attr("tag") == tagId_) {
                $(this).addClass('active') ;
            } else {
                $(this).removeClass('active') ;
            }
        }) ;

    },
    
    initDataGrid:function (tableName, opts) {
    	if (typeof(opts.height) != "string") {
    		E$(tableName).dataTable(opts) ;
    		return ;
    	}

        var height = opts.height ;
        var width = opts.width ;
        
        if (!height) {
            height = '400px' ;
        }
        
        if (!width) {
            width = 'auto' ;
        }

        var $table = E$(tableName) ;
        var $body = $('<div style="overflow:auto;height:'+height+';width:'+width+';"></div>') ;
        var $newTable = $table.clone() ;
        $body.append($newTable) ;
        $table.parent().append($body) ;
        $("tbody", $table).remove() ;
        // remove thead-tr from second row
        $("thead tr", $newTable).each(function (i, elem){
        	if (i != 0) {
        		$(elem).remove();
        	}
        }) ;
        
        $("th", $newTable).each(function (){
            $(this).css("padding", "0") ;
            $(this).html("") ;
        }) ;
        $table[0].id = $table[0].id + "_head" ;
        

        var $listBody = $("tbody[id='listBody']", $newTable) ;
        
        $listBody.delegate('tr','mouseover mouseleave click', function(e) {
            if (e.type == 'mouseover') {
                $(this).toggleClass("active_row");
            } else if (e.type == 'mouseleave') {
                $(this).removeClass("active_row");
            } else if (e.type == 'click') {
                $(this).toggleClass("selected_row");
                var $checkbox = $("input[type='checkbox']", this) ;
                if ($checkbox.length > 0) {
                    $checkbox[0].checked = $(this).hasClass("selected_row") ;
                }
            }
        });
    },
    
    initSelect:function() {
        var selectPreOutHtml = 
        	 '<div style="width:95%; border:1px solid #8ABADD; overflow:hidden;">'
            +'  <p style="padding:0px; margin:0; overflow:hidden; border:1px solid #ffffff">';
        var selectPostOutHtml =
        	 '  </p>'
            +'</div>' ;
            
    	$("select", this.view).each(function () {
    		if (this.init && this.init+"" == "yes") {
    			return ;
    		}
    		var $newSelect = $(this) ;
            $newSelect.css("width", "101%") ;
            $newSelect.css("margin-left", "-2px") ;
            $newSelect.css("margin-top", "-4px") ;
            $newSelect.css("margin-bottom", "-2px") ;
            $newSelect.css("font-size", "16px") ;
    		$(this).parent().html(selectPreOutHtml + $(this).parent().html() + selectPostOutHtml) ;
    	}) ;
    	               
    	 
    	
    },

    toSearch:function () {
    	this.toPage('s') ;
    },

    toAdd:function () {
    	if (this.toPage('a')) {
    		this.get(-1) ;
    	}
    },
    
    toEdit:function (selectInput) {
    	this.selected = null ;
    	
    	if (selectInput) {
    		this.selected = selectInput ;
    	} else {
	    	var sels = $("#"+this.entityName+"TB input:checked", this.view) ;
	    	if (sels.length == 0) {
	    		alert("请先选择要编辑的记录！") ;
	    		return ;
	    	}
	    	this.selected = sels[0] ;
    	}
    	
    	if (this.toPage('e')) {
        	this.get(this.selected.value) ;
    	}
    	
    },
    
    toDelete:function () {
    	if (this.toPage('d')) {
            this.doDelete() ;
    	} 
    },
    
    get:function(id, prefun, postfun) {
    	if (id == -1) {
             viewJs.entity = this.eFormInitData;
    		 formDeserialize("eForm", this.eFormInitData, {}) ;// reset form;
    		 return ;
    	}
    	var idProperty = (this.idName==null?"id":this.idName) ;
    	var params = {} ;
    	params[idProperty] = id ;
        ajax(
            this.get_url, 
            params,
            function(data, textStatus){
                viewJs.entity = data;
                if(prefun != undefined && prefun) prefun(data);
                formDeserialize("eForm", data, {}) ;
                if(postfun != undefined && postfun) postfun(data);
                //if(viewJs.postGet != undefined && viewJs.postGet) viewJs.postGet(data);
            }
        );
    },
    
    list:function() {
    	if (E("selectMe") != null) {
    		E("selectMe").checked = false ;
    	}
        ajax(
            this.list_url, 
            E$("sForm").serialize(),
            function(data, textStatus){
                viewJs.addRows(viewJs.entityName+"TB", data.list) ;
                var $pageSize = E$(viewJs.entityName + "SO.pageSize") ;
                if ($pageSize.length <= 0) {
                	$pageSize = E$("pageSize") ;
                }
                viewJs.lastIndex = Math.ceil(data.total/parseInt($pageSize.val()));
                viewJs.setPaginationInfo(data.total, data.pageIndex, viewJs.lastIndex) ;
                
                if (viewJs.onList) {
                	viewJs.onList(data) ;
                }
            }
        );
    },
    save:function () {
    	var frm = E("eForm") ;
        if(typeof(frm.checkValidity) != "undefined" && !frm.checkValidity()) {
            alert("请正确填写表单！") ;
            if (typeof(frm.setErrorFocus) != "undefined") {
            	frm.setErrorFocus() ;
            }
            return ;
        }
        
        if (!this.checkEditForm(frm)) {
        	return ;
        }
        
        if (!window.confirm("是否确定要保存？")) {
            return ;
        }
        
        var $id = E$(this.entityName+".id") ;
        if ($id.length > 0) {// 优先是否有id可以作判断
        	if ($id.val() == "") {
        		_url = this.create_url ;
        	} else {
        		_url = this.update_url ;
        	}
        } else {
        	if (this.state=='a') {
        		_url = this.create_url ;
        	} else {
        		_url = this.update_url ;
        	}
        }
        
        ajax(
            _url, 
            E$("eForm").serialize(),
            function(data, textStatus){
                if (data.code == "0") {
                    if (viewJs.onSaveOk) {
                    	viewJs.onSaveOk(data.data);
                        return;
                    }
                    //viewJs.toSearchView();
                	viewJs.toPage('s') ;
                    viewJs.list() ;
                }
            }
        );
    },
    doDelete:function (isFixState) {
    	if(isNull(isFixState)) {
    		isFixState = false ;
    	}
    	var idProperty = (this.idName==null?"id":this.idName) ;
    	var idsProperty = (this.idsName==null?"ids":this.idsName) ;
    	
    	var isSearchState = isFixState?this.state=='s':this.preState=='s';
    	
    	var ids = "";
    	
    	if (isSearchState) {
    		ids = $("#"+this.entityName+"TB input:checked", this.view).serialize();
    	} else {
    		var idVal = E(this.entityName+"." + idProperty).value ;
    		if (idVal != "") {    			
    			ids = idsProperty + "=" + idVal ;
    		}
    	}
    	
        if (ids == "") {
        	alert("请先选择要删除的记录！") ;
        	if (!isFixState) {        		
        		this.showBlock(this.preState);
        	}
            return ;
        }
        
        if (!window.confirm("是否确定要删除？")) {
        	if (!isFixState) {        		
        		this.showBlock(this.preState);
        	}
            return ;
        }
        
        ajax(
            this.delete_url, 
            ids,
            function(data, textStatus){
                if (data.code == "0") {
                	//viewJs.toSearchView();
                	viewJs.toPage("s") ;
                	viewJs.list() ;
                } else {
                	if (!isFixState) {
                	    viewJs.showBlock(viewJs.preState);
                	}
                }
            }
        );
    },
    
    selectAll:function (isChecked) {
    	$("#"+this.entityName+"TB input[id='ids']", this.view).each(function(i, elem){
    		elem.checked = isChecked ;
    		if ($(elem).parent().parent().hasClass("selected_row") ^ isChecked) {// 不一致
    			$(elem).parent().parent().toggleClass("selected_row");
    		}
    	}) ;
    },
    
    checkEditForm:function(_form) {
    	return true ;
    },
    
    addRows:function (tableName, datas, conf) {

    	if (!conf) {
    		conf = {} ;
    	}
    	
    	var listBody = $("#" + tableName + " #listBody", viewJs.view) ;
        var template = $("#" + tableName + " #templateBody", viewJs.view) ;
        var isJTemplate= false ;
        
        if (template[0].nodeName == 'TEXTAREA') {
        	isJTemplate = template.attr("jTemplate") != null ;
        	template.attr("disabled", true) ;
        	template = template.val() ;
        } else {
        	template = template.html() ;
        }
        
        var deep = typeof(conf.deep) == "undefined"?null:conf.deep ;
        
        if (deep == null || deep == 0) {
            listBody.html("") ;
        }
        
        
        var pro_id_name = conf.pro_id_name ;
        
        if (!pro_id_name) {
        	pro_id_name = this.proIdName ;
        }

        $(datas).each(function(i, domEle){    
            
            if (deep != null) {
                domEle['_deep'] = deep ;
            }
            
            var mapping = viewJs.convertors ;
            if(mapping != null) {
            	for (var p_rep in mapping) {
            		var p = mapping[p_rep][0] ;
            		var convertor = mapping[p_rep][1] ;
            		try {            			
            			domEle[p_rep] = convertor(domEle[p], domEle) ;// 算出转换值
            		}catch (e) {
            			alert(p_rep + ":" + e) ;
            		}
            //        row = row.replaceAll("{"+p_rep+"}", domEle[p_rep]) ;
            	}
            }  
            
            var row = null ;
            
            if (isJTemplate) {
            	row = parse(template, domEle) ;
            } else {
                row = template ;
                for (var p in domEle) {
                	var value = domEle[p] ;
                    if (typeof(value) == "undefined" || value == null) {
                		value = "" ;
                	}
                    row = row.replaceAll("{"+p+"}", ""+value) ;
                }
            }
            
            row = $(row);
            
            if ($("input:checked", row).length > 0) {
            	if (!$(row).hasClass("selected_row")) {            		
            		$(row).toggleClass("selected_row") ;
            	}
            } else if ($(row).hasClass("selected_row")) {    		
        		$(row).toggleClass("selected_row") ;
            }

            if (pro_id_name) {
            	var pro_id_value = domEle[pro_id_name] ;
            	row.attr(pro_id_name, pro_id_value) ;
            	if (pro_id_value == null || pro_id_value == 0) {
            		$(row).addClass("even_row") ;
            	}
            	var $tr = $("tr[" + pro_id_name + "=" + pro_id_value + "]:last", listBody);
            	if ($tr.length == 0) {
            		$tr = $("#" + pro_id_value, listBody);
            	}
            	if ($tr.length > 0) {// has parent
            		$tr.after(row) ;
            	} else {
                    listBody.append(row);
            	}
            } else {
            	var rowClass = domEle['row_class'] ;

        		//alert(rowClass);
            	if (typeof (rowClass) == "undefined") {
                    if (i%2==1) {
                    	$(row).addClass("even_row") ;
                    }
            	} else {
            		$(row).addClass(rowClass) ;
            	}
                
                listBody.append(row);
            }       

            if (deep != null && domEle.items) {
                conf.deep ++ ;
                viewJs.addRows(tableName, domEle.items, conf) ;
                conf.deep = deep ;
            }  
        });
    },
    
    first:function() {
    	this.pageIndex.value = 1 ;
    	this.list() ;
    },
    
    preview:function() {
    	var index = parseInt(this.pageIndex.value) ;
        if (index <= 1) {
            return ;
        }
    	index = index - 1 ;
        
        if (index < 1) {
        	index = 1 ;
        }
        this.pageIndex.value = index ;
        this.list() ;
    },
    
    next:function() {
        var index = parseInt(this.pageIndex.value) ;
    	if (index >= this.lastIndex) {
    		return ;
    	}
    	index = index + 1 ;
        if (index > this.lastIndex) {
        	index = this.lastIndex ;
        }
        this.pageIndex.value = index ;
        this.list() ;
    },
    
    last:function() {
        var index = parseInt(this.pageIndex.value) ;
        if (index >= this.lastIndex) {
            return ;
        }
    	this.pageIndex.value = this.lastIndex ;
        this.list() ;
    },
    
    toPageList:function(pageIndex) {
    	this.pageIndex.value = pageIndex;
        this.list() ;
    },
    
    changePageSize:function() {
    	V("pageSize", V("pagination.pageSize")) ;
        this.first() ;
    },
    
    setPaginationInfo:function(total, index, last, pager) {
    	if (typeof(total) == "undefined") {
    		total = 0 ;
    		index = 1 ;
    		last = 0 ;
    	}
    	//E$("paginationInfo").html("总记录数为"+total+"条，当前为第"+index+"页，共"+last+"页&nbsp;&nbsp;&nbsp;&nbsp;") ;
    	if (typeof(pager) == "undefined") {
    		pager = E$("paginationInfo") ; 
    	}
    	pager.html(i18n.msg("paginationInfo", total, index, last)) ;
    	
    	var paginationNumbers = $("#paginationNumbers");
    	
    	paginationNumbers.html("");
    	if (paginationNumbers.length > 0) {
    		var start = 1;
    		var end = last;
    		if (last > 5) {
    			if (last - index < 3) {
    				start = last - 5;
    			} else if (index - start < 3){
    				end = start + 5;
    			} else {
    				start = index - 2;
    				end = index + 2;
    			} 
    		}
    		
    		if (start != 1) {
    			paginationNumbers.append("<a href='javascript:viewJs.toPageList(1)'>1</a>");
    			if (start != 2) {    				
    				paginationNumbers.append("<SPAN>...</SPAN>");
    			}
    		}
    		 
    		while (start <= end) {
    			if (start == index) {
        			paginationNumbers.append("<a class='page-this'>" + start + "</a>");
    			} else {
        			paginationNumbers.append("<a href='javascript:viewJs.toPageList(" + start + ")'>" + start + "</a>");	
    			}
    			start ++ ;
    		}

    		if (end != last) {
    			if (end != last - 1) {    				
    				paginationNumbers.append("<SPAN>...</SPAN>");
    			}
    			paginationNumbers.append("<a href='javascript:viewJs.toPageList(" + last + ")'>" + last + "</a>");	
    		}
    	}
    },
    
    resetForm:function(formName) {
    	var f = E(formName) ;
    	if (f && f.resetForm) {
    		f.resetForm() ;    		
    	}
    },
    
    resetEditForm:function() {
    	this.resetForm("eForm") ;
    }, 
    
    resetSearchForm:function() {
    	this.resetForm("sForm") ;
    },
    
    focusForm:function(formName) {
    	var f = E(formName) ;
    	if (f && f.setFirstFocus) {
    		f.setFirstFocus() ;    		
    	}
    }, 
    
    focusEditForm:function() {
    	this.focusForm("eForm") ;
    }, 
    
    focusSearchForm:function() {
    	this.focusForm("sForm") ;
    },
    
    onBeforeShow:function() {

    },
    
    onAfterShow:function() {
    	if (this.state == 's') {
        	this.focusSearchForm() ;
    	} else {
        	this.focusEditForm() ;
    	}
    },
    
    onBeforeHide:function() {
    	this.resetEditForm() ;
    	this.resetSearchForm() ;
    },
    
    onAfterHide:function() {
    	
    },
    
    destroy:function () {
    	this.resetEditForm() ;
    	this.resetSearchForm() ;
    }
};

function ajax(_url, _data, handleOnSuccess, isShowMessage, isShowProgress) {
    if (typeof(isShowProgress) == "undefined") {
        isShowProgress = true ;	
    }

    $.ajax({
        type: "post",
        url: _url,
        data:_data,
        dataType:"json",
        beforeSend: function(XMLHttpRequest){
        	  if (isShowProgress) {
        	      showLoading();
            }

        },
        success: function(data, textStatus){
        	handleOnSuccess(data, textStatus) ;
        	if (typeof(isShowMessage) == "undefined" || isShowMessage) {
        		if (typeof(data.message) != "undefined") {
                    alert(data.message) ;
                	if (1000==data.code) {// 过期session
                		if (relogin) {
                			relogin();
                		}else{
                    		window.location = root + "/logon.jsp";	
                		}
                	}
        	    }
        		
        		if (typeof(data['g$dict']) != "undefined") {
        			//alert("reload script" + data['g$dict']) ;
        			$.extend(g$dict, data['g$dict']) ;
        			g$LS.put("g$dict", g$dict) ;
        		}
            }
        	
        },
        complete: function(XMLHttpRequest, textStatus){
        	  if (isShowProgress) {
        	      hideLoading();
        	  }
        },
        error: function(){
            alert("远端服务不可用，请稍后再试!") ;
        }
    });
}

var loadingIndex = 0;
function showLoading() {
	if ($.messager == null) {
		//$.blockUI();
		loadingIndex ++ ;
		if (loadingIndex != 1) {
			return ;
		}
		
		var loadingDiv = $( "#loadingDiv" ) ;
		if (loadingDiv.length == 0) {
			addLoadingLayout() ;	
			loadingDiv = $( "#loadingDiv" ) ;
		}
		loadingDiv.dialog({
            height: 160,
            width:300,
            modal: true,
            closeOnEscape: false,
            resizable:false,
            dialogClass: "noTitleStuff"
        });
	} else {
		$.messager.progress({title:'Please waiting', msg:'Loading data...'});
	}
}

function hideLoading() {
	if ($.messager == null) {
		loadingIndex -- ;
		if (loadingIndex > 0) {
			return ;
		}
		loadingIndex = 0 ;//避免多次hide时，出问题。
		
	    //$.unblockUI();
		var loadingDiv = $( "#loadingDiv" ) ;
		if (loadingDiv.length != 0) {
			try{
				loadingDiv.dialog( "close" );
			} catch (e){
				alert(e);
			};
		}
	} else {
	    $.messager.progress('close');
	}
}

function addRows(tableName, datas) {

	var jTableName = "#" + tableName ;
	var listBody = $(jTableName + " > tbody[id='listBody']") ;
    var template = $(jTableName + " > textarea[id='templateBody']").val() ;
    
    if (template == null) {
    	template = $(jTableName + " > tbody[id='templateBody']").html() ;
    }
    listBody.html("") ;

    $(datas).each(function(i, domEle){
        var row = template ;
        for (p in domEle) {
        	var value = domEle[p] ;
            if (typeof(value) == "undefined" || value == null) {
        		value = "" ;
        	}
            row = row.replaceAll("{"+p+"}", ""+value) ;
        }
        listBody.append(row);
    });
}


function addList(template, listBody, datas, convertors) { 
	
    if (template[0].nodeName == 'TEXTAREA') {
    	template.attr("disabled", true) ;
    	template = template.val() ;
    } else {
    	template = template.html() ;
    }
    
    listBody.html("");
    $(datas).each(function(i, domEle){
        var row = template ;
        for (p in domEle) {
        	var value = domEle[p] ;
        	
            if (typeof(value) == "undefined" || value == null) {
        		value = "" ;
        	}
            row = row.replaceAll("{"+p+"}", ""+value) ;
        }
        
        if(convertors != undefined && convertors != null) {
            for (p_rep in convertors) {
            	var p = convertors[p_rep][0] ;
            	var convertor = convertors[p_rep][1] ;
                row = row.replaceAll("{"+p_rep+"}", convertor(domEle[p], domEle)) ;
            }
        }
            
        listBody.append(row);
    });
}


/**
 * 
 * @param params {
 *                  id:elementId, 
 *                  dictName:nameOfDict, 
 *                  data:dataOfDict, 
 *                  value:selectedOptionValue, 
 *                  firstLabel:firstOptionLable,
 *                  textProperty:thePropertyNameOfText,
 *                  titleProperty:thePropertyNameOfTitle,
 *                  valueProperty:thePropertyNameOfValue,
 *                  value:currentValue
 *               }
 */
function fillOptions(params, limits){
	var sel_source = E(params.id);
    if (sel_source == undefined) {
      return;
    }
    
	var pv = function (propertyValue, defaultValue) {

		if (typeof(propertyValue) == "undefined") {
			return defaultValue ;
		} else {
			return propertyValue ;
		}
	} ;
	

	var url = pv(params.url, null);
	
	if (url != null) {
	    ajax(
            url, 
            params.data,
            function(data, textStatus){
	        	params.url = null;
	        	params.data = data.list ;
	        	fillOptions(params, limits) ;
            }
        );
	    return ;
	}
	
	var _dictName = pv(params.dictName, null);
	var _value = pv(params.value, null) ;
	var textProperty = pv(params.textProperty, "name_") ;
	var valueProperty = pv(params.valueProperty, "PK_ID") ;
	var titleProperty = pv(params.titleProperty, "desc_") ;
	var datas = _dictName == null? params.data:g$dict.get(_dictName) ;
	var filter = pv(params.filter, null);
	var filterConditionProperty = pv(params.filterConditionProperty, null);
    var filterConditionValue = pv(params.filterConditionValue, null);

	removeOptions(sel_source) ;
	
	if (typeof(params.firstLabel) != "undefined") {
		sel_source.options.add(new Option(params.firstLabel, ""));
	}

	if (datas == null || typeof(datas.length)=="undefined") {
		return ;
	}
	
	for(var i = 0;i < datas.length;i++){
		var dataValue = datas[i][valueProperty];
		
		if (filterConditionProperty != null && filterConditionValue != null) {
		  if (datas[i][filterConditionProperty] != filterConditionValue) {
		    continue;
		  }
		}

		if (filter != null && !filter[dataValue]) {// 值需要过滤
			continue ;
		}
		
	    var found = false;
	    if (typeof(limits) != "undefined") {
	      for (var j = 0; j < limits.length; j++){
	        if (datas[i][valueProperty] == limits[j]){
	          found = true;
	          break;
	        }
	      }
	    }
	    if (limits != undefined && !found) {
	      continue;
	    }
	    
		var item = new Option(datas[i][textProperty], datas[i][valueProperty]);
		item.title = datas[i][titleProperty];
		if (_value != null && _value==datas[i][valueProperty]) {
			item.selected = true ;
		}
		sel_source.options.add(item);
	}
}


function fillRadios(params){

	var pv = function (propertyValue, defaultValue) {

		if (typeof(propertyValue) == "undefined") {
			return defaultValue ;
		} else {
			return propertyValue ;
		}
	} ;
	
	var sel_source = E(params.id + "_radios");
	var _dictName = pv(params.dictName, null);
	var _value = pv(params.value, null) ;
	var textProperty = pv(params.textProperty, "name_") ;
	var valueProperty = pv(params.valueProperty, "PK_ID") ;
	var titleProperty = pv(params.titleProperty, "desc_") ;
	var separator = pv(params.separator, "") ;
	var datas = _dictName == null? params.data:g$dict.get(_dictName) ;
	var position = pv(params.position, "left");
	var type = pv(params.type, "radio");
	var columns = pv(params.columns, 8);
	var filter = pv(params.filter, null);

	if (datas == null || typeof(datas.length)=="undefined") {
		return ;
	}
	
	var html = "" ;
	var _values = null;
	if (_value != null && _value != "") {
		_values = _value.split(",");
	}
	for(var i = 0;i < datas.length;i++){
		var dataValue = datas[i][valueProperty];
		if (filter != null && !filter[dataValue]) {// 值需要过滤
			continue ;
		}
		if (html != "") {
			html += separator;
		}
		
		if (i != 0 && i%columns == 0) {
			html += "<br/>";
		}
		
		var checkFlag = "";
		if  (_values != null) {
			for (var j=0; j<_values.length; j++) {
				if (_values[j] == dataValue) {
					checkFlag = "checked";
					break;
				}
			}
		}
		var radio = '<input type="' + type + '" ' + checkFlag + ' value="' + dataValue + '" id="' + params.id + '_' + dataValue + '" name="' + params.id + '"/>' ;
		if (position == "left") {
			html += radio ;
		}
		html += '<label for="' + params.id + '_' + dataValue + '">' + datas[i][textProperty] + '</label>' ;
		if (position != "left") {
			html += radio ;
		}
		
	}

	$(sel_source).html(html) ;


}

function removeOptions(sel_source){

	sel_source = e(sel_source);
	
	if (sel_source.selectedIndex==-1) {
		return;
    }
    
	for(var i = 0;i < sel_source.options.length;i++){
		//if(sel_source.options[i].selected){
			sel_source.options.remove(i--);
		//}
	}
}

function formDeserialize(formName, jsonObj, ignoreProperties) {
	
	if (typeof(ignoreProperties)=="undefined") {
		ignoreProperties = {} ;
	}
	
	E$(formName).find("input,select,textarea").each(function(i, domEle){
		if (domEle["name"] == "") {
			return ;
		}
        var index =domEle["name"].indexOf(".") + 1;
        var p = index > 0 ? domEle["name"].substring(index):domEle["name"] ;
        if (typeof(ignoreProperties[p]) != "undefined") {
            return ;
        }
        
        var _value = typeof(jsonObj[p])=="undefined"||jsonObj[p]==null?"":jsonObj[p] ;
        if (typeof(_value) == "string" && typeof(domEle.maxLength) != "undefined" ) {
        	var len = domEle.maxLength ;
        	if (len > 0 && _value.length > len) {
        		_value = _value.substring(0, len) ;
        	}
        }
        setInputValue(domEle, _value) ;
    }) ;
}

function formDeserializeText(formName, type, jsonObj, ignoreProperties) {
	
	if (typeof(ignoreProperties)=="undefined") {
		ignoreProperties = {} ;
	}
	
	E$(formName).find(type).each(function(i, domEle){
		if (domEle["id"] == undefined || domEle["id"] == "") {
			return ;
		}
        var index =domEle["id"].indexOf(".") + 1;
        var p = index > 0 ? domEle["id"].substring(index):domEle["id"] ;
        if (typeof(ignoreProperties[p]) != "undefined" && ignoreProperties[p]) {
            return ;
        }

        var _value = typeof(jsonObj[p])=="undefined"||jsonObj[p]==null?"":jsonObj[p] ;
        if (typeof(_value) == "string" && $(domEle).attr("maxLength") != null ) {
        	var len = parseInt($(domEle).attr("maxLength")) ;
        	if (len > 0 && _value.length > len) {
        		_value = _value.substring(0, len) + "...";
        	}
        }
        //domEle.innerHTML = _value;
        $(domEle).text(_value);
    }) ;
}

/**
 * 设置表单form中元素inputName值为value
 */
function setFormValue(form, inputName, value) {
	setInputsValue(form[inputName], value) ;
}

function setInputsValue(inputObj, value) {
    var inputObjs = [] ;
    
    if (typeof(inputObj.length) == "undefined") {
        inputObjs[0] = inputObj ; 
    } else {
		inputObjs = inputObj ;    
    }
    
    for (var i = 0 ; i < inputObjs.length ; i ++) {
    	setInputValue(inputObjs[i], value) ;
    }
}
function setInputValue(inputObj, value) {
    //debugger;
    if (inputObj.type == "radio" || inputObj.type == "checkbox") {
        inputObj.checked = (inputObj.value == value);
    } else if (inputObj.type == "select-multiple"||inputObj.type == "select-one"){
    	setSelectValue(inputObj, value) ;
    }/* else if (inputObj.type == "select-multiple"||inputObj.type == "select-one"){
    	setSelectValue(inputObj, value) ;
    }*/ else {
        inputObj.value = value ;
        if (inputObj.subType && inputObj.subType == "combox") {
        	inputObj.setComboxValue() ;
    	}
    }
}


function setSelectValue(selectObj, _value) {
	var count=selectObj.options.length;

	var values = $.isArray(_value)? _value:[_value] ;// to array
	
    for(var i=0;i<count;i++) {
    	var o = selectObj.options[i] ;
    	var selected=false ;
    	for (var j = 0 ; j < values.length ; j++) {
    		if(values[j] == o.value) {
    			selected = true ; 
    			break ;
    		}
    	}
    	
    	o.selected = selected; 
    } 
	
	if (selectObj.subType && selectObj.subType == "combox") {
		selectObj.setComboxValue() ;
		/*
		if(selectObj.onSelected) {
			selectObj.onSelected() ;
		}*/
	} else {
		$(selectObj).trigger("change") ;
	}
}

/**
 * 取得表单form中元素input的值
 */
function getFormValue(form, inputName) {
    var inputObj = form[inputName] ;
    var inputObjs = [] ;
    
    if (typeof(inputObj) == "undefined") {
        return null ;
    }
    
    if (typeof(inputObj.length) == "undefined") {
        inputObjs[0] = inputObj ; 
    } else {
		inputObjs = inputObj ;    
    }
    
    var result = null ;
    
    for (var i = 0 ; i < inputObjs.length ; i ++) {
        inputObj = inputObjs[i] ;
	    if (inputObj.type == "radio") {
	        if (inputObj.checked) {
	            return inputObj.value ;
	        }
	    } else if (inputObj.type == "checkbox") {
	        if (inputObj.checked) {
		        if (result == null) {
		            result = [] ;
		        }
		        result[result.length] = inputObj.value ;
		    }
	    } else {
	        return inputObj.value ;
	    }
    }
    
    return result ;
}

/**
 * 设定元素集的readOnly状态 
 * 
 * example:setReadonly("sForm", ["userSO.name_cn"]) ;
 * 
 * 
 * @param _form 表单名或对象（也可某个元素，不一定表单）
 * @param _ids 指定的ID数组，也可用"*"表示全部
 * @param isReadOnly 是否readOnly
 * @param isDisable 是否disable
 * @param className readOnly时的class
 */
function setReadonly(_form, _ids, _ignorIds, isReadOnly, isDisable, className) {
	var form$ = null ;
	if (typeof(_form) == "string") {
		form$ = E$(_form) ;
	} else {
		form$ = $(_form) ;
	}
	
	if (typeof(isReadOnly) == "undefined") {
		isReadOnly = true ;
	}

	if (typeof(isDisable) == "undefined") {
		isDisable = isReadOnly ;
	}
	
	var ids = {} ;
	var ignorIds = {};
	
	if ($.isArray(_ids)) {
		for (var i = 0 ; i < _ids.length ;i ++) {
			ids[_ids[i]] = true ;
		}
	} else {
		ids[_ids] = true ;
	}
	
	if ($.isArray(_ignorIds)) {
		for (var i = 0 ; i < _ignorIds.length ;i ++) {
			ignorIds[_ignorIds[i]] = true ;
		}
	} else {
		ignorIds[_ignorIds] = true ;
	}
	
	var inputs = $("input,select,textarea", form$) ;
	
	for (var i = 0 ;i < inputs.length ;i ++) {
		var input = inputs[i] ;
		if ($(input).attr("type") == "search-text") {
		    continue ;
		}
		if ((ids["*"] || ids[input.name]) && (ignorIds[input.name] == undefined)) {

			if (input.subType && (input.subType == "combox" || input.subType == "multiselect")) {
				input.setReadonly(isReadOnly, isDisable) ;
				continue ;
			}
			$(input).attr("readonly", isReadOnly);
			$(input).attr("disabled", isDisable);
			if (isReadOnly) {
				if (className) {
					$(input).addClass(className) ;
				} else {
					//$(input).css("background","transparent") ;
				}
			} else {
				if (className) {
					$(input).removeClass(className) ;
				} else {
				}
			}
		}
	}
}

/**
 * 重新加载指定的Script元素
 * 
 * @param id
 */
function reloadScript(id) 
{
    var oldjs = document.getElementById(id); 
    
    var scriptObj = document.createElement("script"); 
    scriptObj.src = oldjs.src; 
    scriptObj.type = "text/javascript"; 
    scriptObj.id = id; 
    
    oldjs.parentNode.removeChild(oldjs); 
    
    document.getElementsByTagName("head")[0].appendChild(scriptObj); 
}


function isUndefined(elem) {
	return (typeof(elem) == "undefined") ;
}
function isDefined(elem) {
	return (typeof(elem) != "undefined") ;
}

/**
 * 
 * @param targetDivName 不用"#"开头
 * @param _url
 * @param _data
 * @param _callback
 * @returns
 */
function load(targetDivName, _url, _data, _callback, containerDivName, isHidden) {
	
	targetDivName.replaceAll(".", "\\.") ;
	
	var targetDiv = $("#" + targetDivName) ;
	if (targetDiv.length == 0) {
	    $(typeof(containerDivName) == "undefined"?"body":("#" + containerDivName)).append('<div id="' + targetDivName + '"></div>') ;
	    targetDiv = $("#" + targetDivName) ;
	    if (typeof(isHidden) == "undefined" || isHidden) {	    	
	    	targetDiv.hide() ;
	    }

	    showLoading();
	      
	    targetDiv.load(_url, _data, function (responseText, textStatus, XMLHttpRequest) {
	    	if (_callback) {
	    		_callback(responseText, textStatus, XMLHttpRequest) ;
	    	}

  	        hideLoading();
	    });
	} else {
    	if (_callback) {
    		_callback() ;
    	}
    }
	
	
	return targetDiv ;
}

function addLoadingLayout() {
	var html  = '<div id="loadingDiv" style="display: none;padding-top: 50px;">';
	    //html += '<br/><br/>';
		html += '<h1 style="font-size: 30px;">&nbsp;&nbsp;&nbsp;&nbsp;<img src="'+root+'/images/loading.gif" />&nbsp;&nbsp;&nbsp;&nbsp;请稍候...</h1>';
		html += '</div>';
		
    $("body").append(html) ;
}


function loadHtmlEditorJs() {
		var jsScript = $( "#cleditor" ) ;
		if (jsScript.length == 0) {
		    $("body").append('<link rel="stylesheet" type="text/css" href="' + root + '/plugin/CLEditor1_3_0/jquery.cleditor.css" />') ;
		    $("body").append('<script id="cleditor" type="text/javascript" src="' + root + '/plugin/CLEditor1_3_0/jquery.cleditor.js"></script>') ;
		}
}

function log(msg) {
	var e = E("logDiv");
	if (e == null) {		
		var html  = '<div id="logDiv" style="display: none;"></div>';
		
		$("body").append(html) ;
		
		e = E("logDiv");
	}
	
	/*
	if ($(e).html().length > 1000) ; {
		$(e).html("") ;
	}*/
	
	$(e).append("<p>" + msg + "</p>") ;
	
	$(e).dialog({
	    title: "Console",
	    autoOpen: true,
	    width: 300,
	    height: 100,
	    modal: false,
	    resizable: true,
	    autoResize: true,
	    overlay: {
	        opacity: 0.5,
	        background: "black"
	    },
	    position: { my: "left top", at: "right bottom", of: window },
	    resizable:true
	});
}



function getCommonForm(elements) {
	var f = E("commonForm_0342372384204820") ;
	if (f == null) {
		var html  = '<div style="display:none;"><form id="commonForm_0342372384204820" method="post">';
		    html += '  <input type="text" id="jsonObject"/>';
			html += '</form></div>';
			
	    $("body").append(html) ;
	    f = E("commonForm_0342372384204820") ;
	}
	/*
	$("#jsonObject", f).val(jsonObject) ;
	 */
	
	var f$ = $(f) ;
	
	f$.html("") ;

	if ($.isPlainObject(elements)) {
		for (var p in elements) {
			f$.append("<input type='text' name='" + p + "' value='" + (elements[p] == null?"":elements[p]) + "'/>") ;
		}
	} else {		
		elements.each(function (i, e) {
			f$.append(e.outerHTML) ;
		}) ;
	}
	
	return f ;
}

var DEFAULT_WIN_WIDTH = 780 ;
var DEFAULT_WIN_HEIGHT = 800 ;
var DEFAULT_WIN_LEFT = 0 ;
var DEFAULT_WIN_TOP = 0 ;


/**
 * 提交页面表单并打开默认的全屏窗口显示结果
 * 参数： String，提交的url
 *       Object，Form对象或Form的名字
 *       String, 新窗口名字
 * 返回：Object, 新窗口对象
 *           
 * e.g. :  submitPage("http://localhost:9080/4GPOS/welcome.do", "LoginForm")
 *		   submitPage("http://localhost:9080/4GPOS/welcome.do", "LoginForm", "newWin")
 * 
function submitPage(_url, _form, _winName) {
	return submitDialog(_url, _form, _winName, DEFAULT_WIN_WIDTH, DEFAULT_WIN_HEIGHT, DEFAULT_WIN_LEFT, DEFAULT_WIN_TOP);
}
 */


/**
 * 提交页面表单到打开默认的Dialog窗口显示结果
 * 参数： String，提交的url
 *       Object，Form对象或Form的名字
 *       String, 新窗口名字
 *       int,    窗口宽度
 *       int，   窗口长度
 *       int，   窗口左部起始位置
 *       int，   窗口顶部起始位置
 * 返回：Object, 新窗口对象
 *           
 * e.g. :  submitDialog("http://localhost:9080/4GPOS/welcome.do", "LoginForm")
 *		   submitDialog("http://localhost:9080/4GPOS/welcome.do", "LoginForm", "newWin")
 * 
 */
function submitDialog(_url, _form, _winName, _width, _height, _left, _top) {
	if ("string" == typeof(_form)) {
		_form = document.getElementById(_form);
	}
	if (!_winName) _winName = _form.name + "_Window";

	var _tmpTarget = _form.target;
	var _tmpAction = _form.action;

	var _win = showDialog("", _winName, _width, _height, _left, _top);
	_form.target = _winName;
	_form.action = _url;
	_form.submit();
	
    _form.target = _tmpTarget;
	_form.action = _tmpAction;

	return _win;
}

/**
 * 打开普通的窗口
 * 参数： String，指定url
 *       String，新窗口名字
 *       int,    窗口宽度
 *       int，   窗口长度
 *       int，   窗口左部起始位置
 *       int，   窗口顶部起始位置
 * 返回：Object, 新窗口对象
 *           
 * e.g. :  showDialog("http://localhost:9080/4GPOS/welcome.do")
 *         showDialog("http://localhost:9080/4GPOS/welcome.do", null, 1024, 768, 0, 0)
 * 
 */
function showDialog(_url, _winName, _width, _height, _left, _top) {

	//showLoading(); //开启进度提示层

	if (!_winName) _winName = "";

	if (isNaN(parseInt(_width))) _width = DEFAULT_DIALOG_WIDTH;
    if (isNaN(parseInt(_height))) _height = DEFAULT_DIALOG_HEIGHT;
    if (isNaN(parseInt(_left))) _left = (screen.availWidth-_width)/2;
    if (isNaN(parseInt(_top))) _top = (screen.availHeight-_height)/2;

	var _newWin = window.open(
		        _url,
		        _winName,
		        "height="+_height+",width="+_width+",left="+_left+",top="+_top+",fullscreen=no,menubar=no,resizable=no,titlebar=no,toolbar=no,location=no,scrollbars=no,status=no");
    //_newWin.resizeTo(_width,_height);
    //_newWin.moveTo(_left, _top);
    
    return _newWin;
}





//-- 以下function暂不建议用
/**
 * 设置redio的style
 */
function selectAll(_targets, _isChecked) {
    if ("string" == typeof(_targets)) {
        _targets = document.getElementsByName(_targets);
    }
    
    if (!_targets || _targets == null) {
        return ;
    }

    if (typeof(_targets.length) == "undefined") {
        var temp = _targets ;
        _targets = [] ;
        _targets[0] = _targets ;
    }
    
    var isChecked = true ;
    if ("undefined" != typeof(_isChecked)) {
        isChecked = _isChecked;
    }
    
    for (var i = 0 ; i < _targets.length ; i ++){
        _target = _targets[i] ;

        if (_target.type == "radio" || _target.type == "checkbox") {
            _target.checked = isChecked ;
        } else if (_target.type == "select-multiple"){
			for(var j = 0;j < _target.options.length; j++){	
				_target.options[j].selected = true;
			}
        }
    }
}

function removeSelectedItems(sel_source){

	sel_source = e(sel_source);
	
	if (sel_source.selectedIndex==-1) {
		return;
    }
    
	for(var i = 0;i < sel_source.options.length;i++){
		if(sel_source.options[i].selected){
			sel_source.options.remove(i--);
		}
	}
}


function getRadioValue(__radio) {
	_radio = document.all(__radio);
	
	if (_radio) {
	    if (typeof(_radio.length) == "undefined") {
	        _radio = [_radio] ;
	    }
	
		for (var i = 0; i < _radio.length; i++) {
			if (_radio[i].checked) {
				return _radio[i].value;
			}
		}
	}
	
	return null;
}


// open window
function openWindow(params) {

	if (top == null) {
        $.blockUI();
        //$.messager.progress({title:'Please waiting', msg:'Loading data...'});
	} else {
		top.$.blockUI();
		//top.$.messager.progress({title:'Please waiting', msg:'Loading data...'});
	}
    
	var _url = params.url ;
	var _winName = params.name ;
	var _width = params.width ;
    var _height = params.height ;
	var _left = params.left ;
    var _top = params.top ;
    
	//showProcess(true); //开启进度提示层

	if (!_winName) _winName = "";

	if (isNaN(parseInt(_width))) _width = 1024;//DEFAULT_DIALOG_WIDTH;
    if (isNaN(parseInt(_height))) _height = 740;//DEFAULT_DIALOG_HEIGHT;
    if (isNaN(parseInt(_left))) _left = (screen.availWidth-_width)/2;
    if (isNaN(parseInt(_top))) _top = (screen.availHeight-_height)/2;

    var params = null ;
    params  = "height=" + _height ;
    params += ",width=" + _width ;
    params += ",left=" + _left ;
    params += ",top=" + _top ;
    params += ",fullscreen=no,menubar=no,resizable=no" ;
    params += ",titlebar=no,toolbar=no,location=no,scrollbars=no,status=no" ;
    
	var _newWin = window.open(_url, _winName, params);
	
	//_newWin.resizeTo(_width,_height);
    //_newWin.moveTo(_left, _top);
    
    return _newWin;
}

function closeWindow() {
	
	$.unblockUI();
	//$.messager.progress('close');

	window.close();
}

function unblock() {
    if (opener != null) {
        if (opener.top != null) {
        	opener.top.$.unblockUI();
        	//opener.top.$.messager.progress('close');

        } else {
            opener.$.unblockUI();
            //opener.$.messager.progress('close');

        }
    }
}

// --- form function ----


/**
 * 显示进度提示并屏蔽键盘操作（一般用于页面提交时）
 * 参数：boolean，是否显示遮挡层
 *           
 * e.g. :  showProcess() // 显示提示层
 *         showProcess(true) // 显示遮挡层及提示层
 * 
 */
function showProcess(_showCover) {
	if (_showCover) {
		showElement(true, "coveringDIV");
	}
    showElement(true, "processDIV");
    
    //setKeyControl(null, true); // 页面键盘控制失效
}

/**
 * 恢复键盘操作隐藏进度提示
 * 参数：无
 *           
 * e.g. :  hideProcess() 
 * 
 */
function hideProcess() {
	//initKeyControl(); // 恢复页面键盘控制设置
	
	showElement(false, "processDIV");
    showElement(false, "coveringDIV");
}


/*
function v_(inputName, form) {

    if (typeof(form) == "undefined") form = document.forms[0] ;
    if (typeof(form) == "string") form = e_(form) ;
    
    var inputObjs = form[inputName] ;
    
    if (typeof(inputObjs.length) == "undefined") {
        inputObjs[0] = inputObjs ; 
    } 
    
    var result = null ;
    
    var inputObj = null ;
    for (var i = 0 ; i < inputObjs.length ; i ++) {
        inputObj = inputObjs[i] ;
	    if (inputObj.type == "radio") {
	        if (inputObj.checked) {
	            return inputObj.value ;
	        }
	    } else if (inputObj.type == "checkbox") {
	        if (inputObj.checked) {
		        if (result == null) {
		            result = [] ;
		        }
		        result[result.length] = inputObj.value ;
		    }
	    } else {
	        return inputObj.value ;
	    }
    }
    
    return result ;
}
*/
function openWindow2(windowName, windowTitle, url, params, buttonCalls, windowWidth, windowHeight) {

	setWindowParams(windowName, params) ;
	
	var iframe = '<iframe src="' + url + '" name="' + windowName + '" id="' + windowName + '" frameborder="0" width="100%" height="100%"></iframe>' ;
    var divFrame = '<div style="width:100%;heigth:100%;">' + iframe + '</div>' ; 

    if (typeof(windowWidth) == "undefined") {
    	windowWidth = 910 ;
    }
    
    if (typeof(windowHeight) == "undefined") {
    	windowHeight = 760 ;
    }
    
	var windowMe = top.$(divFrame).dialog({
	    title: windowTitle,
	    autoOpen: true,
	    width: windowWidth,
	    height: windowHeight,
	    modal: true,
	    resizable: true,
	    autoResize: true,
	    overlay: {
	        opacity: 0.5,
	        background: "black"
	    },
	    resizable:false,
        buttons: buttonCalls
	});
	
	params.windowMe = windowMe ;
	
	return windowMe ;
}

function getWindowParams(windowName) {
	if (typeof(top.windowParams) == "undefined") {
		top.windowParams = [] ;
	}
	
	var params = top.windowParams[windowName] ;
	
	if (params == null || typeof(params) == "undefined") {
		return params ;
	} else {
		top.windowParams[windowName] = null ;
		return params ;
	}
}

function setWindowParams(windowName, params) {
	if (typeof(top.windowParams) == "undefined") {
		top.windowParams = [] ;
	}

	top.windowParams[windowName] = params ;
}