/*
 * 为jquery - ui 的扩展
 * */
(function( $ ) {
    $.widget( "ui.combobox", {
        _create: function() {
            var input,
                that = this,
                select = this.element.hide(),
                selected = select.children( ":selected" ),
                value = selected.val() ? selected.text() : "",
                wrapper = this.wrapper = $( "<span>" )
                    .addClass( "ui-combobox" )
                    .insertAfter( select );
                
            function removeIfInvalid(element) {
                var value = $( element ).val(),
                    matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( value ) + "$", "i" ),
                    valid = false;
                select.children( "option" ).each(function() {
                    if ( $( this ).text().match( matcher ) ) {
                        this.selected = valid = true;
                        return false;
                    }
                });
                if ( !valid ) {
                    // remove invalid value, as it didn't match anything
                    $( element )
                        .val( "" )
                        //.attr( "title", value + " didn't match any item" )
                        .attr( "title", value + " 未与任何选项匹配！" )
                        .tooltip( "open" );
                    select.val( "" );
                    setTimeout(function() {
                        input.tooltip( "close" ).attr( "title", "" );
                    }, 2500 );
                    input.data( "autocomplete" ).term = "";
                    return false;
                }
            }

            input = $( "<input type='search-text'/>" )
                .appendTo( wrapper )
                .val( value )
                .attr( "title", "" )
                .addClass( "ui-state-default ui-combobox-input" )
                .autocomplete({
                    delay: 200,
                    minLength: 0,
                    source: function( request, response ) {
                        var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
                        response( select.children( "option" ).map(function() {
                            var text = $( this ).text();
                            if ( this.value && ( !request.term || matcher.test(text) ) )
                                return {
                                    label: text.replace(
                                        new RegExp(
                                            "(?![^&;]+;)(?!<[^<>]*)(" +
                                            $.ui.autocomplete.escapeRegex(request.term) +
                                            ")(?![^<>]*>)(?![^&;]+;)", "gi"
                                        ), "<strong>$1</strong>" ),
                                    value: text,
                                    option: this
                                };
                        }) );
                    },
                    select: function( event, ui ) {
                        ui.item.option.selected = true;
                        if (select[0].onSelected) {
                        	select[0].onSelected(event, ui.item.option) ;
                        }
                        that._trigger( "selected", event, {
                            item: ui.item.option
                        });
                    },
                    change: function( event, ui ) {
                        if ( !ui.item )
                            return removeIfInvalid( this );
                    }
                })
                .addClass( "ui-widget ui-widget-content ui-corner-left" );

            input.data( "autocomplete" )._renderItem = function( ul, item ) {
                return $( "<li>" )
                    .data( "item.autocomplete", item )
                    .append( "<a>" + item.label + "</a>" )
                    .appendTo( ul );
            };


            input.data( "autocomplete" )._suggest= function( items ) {
        		var ul = this.menu.element
        			.empty()
        			.zIndex( this.element.zIndex() + 100 );
        		this._renderMenu( ul, items );
        		this.menu.refresh();

        		// size and position menu
        		ul.show();
        		this._resizeMenu();
        		ul.position( $.extend({
        			of: this.element
        		}, this.options.position ));

        		if ( this.options.autoFocus ) {
        			this.menu.next();
        		}
        	}

            
            $( "<a></a>" )
                .attr( "tabIndex", -1 )
                .attr( "title", "显示所有" )
                .tooltip()
                .button({
                    icons: {
                        primary: "ui-icon-triangle-1-s"
                    },
                    text: false
                })
                .appendTo( wrapper )
                .removeClass( "ui-corner-all" )
                .addClass( "ui-corner-right ui-combobox-toggle" )
                .click(function() {
                    // close if already visible
                    if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
                        input.autocomplete( "close" );
                        removeIfInvalid( input );
                        return;
                    }

                    // work around a bug (likely same cause as #5265)
                    $( this ).blur();

                    // pass empty string as value to search for, displaying all results
                    input.autocomplete( "search", "" );
                    input.focus();
                });

                input.tooltip({
                        position: {
                            of: this.button
                        },
                        tooltipClass: "ui-state-highlight"
                    });

                select[0].input = input[0] ;
                select[0].subType = "combox" ;
                select[0].setComboxValue = function () {
                	var selectedOption = $(this).children( ":selected" ) ;
                    $(this.input).val(selectedOption.text()) ;
              		if(select[0].onSelected) {
              			select[0].onSelected(null, selectedOption[0]) ;
            		}
                } ;
        },

        destroy: function() {
            this.wrapper.remove();
            this.element.show();
            $.Widget.prototype.destroy.call( this );
        }
    });
})( jQuery );

//---------------------------------------------------------------------------
//   combobox2
//---------------------------------------------------------------------------
(function( $ ) {
    $.widget( "ui.combobox2", {
        // default options
        options: {
        	url:null, 
        	textProperty:[], 
        	idProperty:"PK_ID", 
        	data:null, 
        	dataName:null, 
        	valueProperty:null, 
        	isMultiple:false, 
        	firstLabel:null, 
        	firstData:null, 
        	toggleCss:{},
        	remoteFilter:{url:null, from:null, preParams:null}
        },

        _create: function() {
            var that = this;
            var valueInput = this.element.hide() ;
            var value = valueInput.val();
            var wrapper = this.wrapper = $( "<span>" )
                    .addClass( "ui-combobox" )
                    .insertAfter( valueInput );

            function removeIfInvalid(element) {
            	/*
                var value = $( element ).val(),
                    matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( value ) + "$", "i" ),
                    valid = false;
                select.children( "option" ).each(function() {
                    if ( $( this ).text().match( matcher ) ) {
                        this.selected = valid = true;
                        return false;
                    }
                });*/

                var text = getTextByValue(valueInput.val()) ;
                input.val(text) ;
                
                var valid = (text!="") ;
                
                if ( !valid ) {
                    // remove invalid value, as it didn't match anything
                    $( element )
                        .val( "" )
                        .attr( "title", value + "  未与任何选项匹配！" )
                        .tooltip( "open" );
                    valueInput.val( "" );
                    setTimeout(function() {
                        input.tooltip( "close" ).attr( "title", "" );
                    }, 2500 );
                    input.data( "autocomplete" ).term = "";
                    return false;
                }
            }
            

        this.availableDatas = this.getDatas() ;
        
        function split( val ) {
            return val.split( /,\s*/ );
        }
        function extractLast( term ) {
            return split( term ).pop();
        }
        
        function getText(_item) {
        	  var ret = "" ;
        	  
            for (var i = 0 ; i < that.options.textProperty.length ; i ++) {
            	var txt = _item[that.options.textProperty[i]] ;
            	if (txt == null) {
            		txt = "" ;
            	}
            	
            	if (txt == "") {
            		continue ;
            	}
            	
                if (ret != "") {
                    ret += "/" ;
                }
                
                ret += txt ;
            }
            
            return ret ;
        }
        
        function getTextById(val) {
        	/*
        	if (availableDatas == null) {
        		return "" ;
        	}
            for (var i = 0 ; i < availableDatas.length ; i ++) {
                if (availableDatas[i][that.options.idProperty] == val) {
                    return getText(availableDatas[i]);
                }
            }
            
            return "" ;
            */
            var data = findData(that.options.idProperty, val);
            if (data == null) {
            	return "" ;
            }
            return getText(data) ;
        }
        
        function getTextByValue(val) {
        	/*
        	if (availableDatas == null) {
        		return val ;
        	}
            for (var i = 0 ; i < availableDatas.length ; i ++) {
                if (availableDatas[i][that.options.valueProperty] == val) {
                    return getText(availableDatas[i]);
                }
            }*/
            var data = findData(that.options.valueProperty, val);
            if (data == null) {
            	return val ;
            }
            return getText(data) ;
        }
        
        function findData(pro, val) {
        	if (that.availableDatas == null) {
        		return null ;
        	}
            for (var i = 0 ; i < that.availableDatas.length ; i ++) {
                if (that.availableDatas[i][pro] + "" == val + "") {
                    return that.availableDatas[i];
                }
            }
        	return null ;
        }
        
        function filter(array, term) {
            var matcher = new RegExp( $.ui.autocomplete.escapeRegex(term), "i" );
            return $.grep( array, function(value) {
                for (var i = 0 ; i < that.options.textProperty.length ; i ++) {
                	  if (matcher.test(value[that.options.textProperty[i]])) {
                	      return true ;
                	  }
                }
                
                return false ;
            });
        }
        
        var input = $( "<input type='search-text'/>" )
            .appendTo( wrapper )
            .val( value )
            .attr("title", "")
            .attr("id" , valueInput.attr("id") + ".text")
            .addClass( "ui-state-default ui-combobox-input" )
            .autocomplete({
                delay: 200,
                minLength: 0,
                source: function( request, response ) {
                	/*
                    var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
                    response( select.children( "option" ).map(function() {
                        var text = $( this ).text();
                        if ( this.value && ( !request.term || matcher.test(text) ) )
                            return {
                                label: text.replace(
                                    new RegExp(
                                        "(?![^&;]+;)(?!<[^<>]*)(" +
                                        $.ui.autocomplete.escapeRegex(request.term) +
                                        ")(?![^<>]*>)(?![^&;]+;)", "gi"
                                    ), "<strong>$1</strong>" ),
                                value: text,
                                option: this
                            };
                    }) );*/

                	var rtFilter = that.options.remoteFilter ;
                    if (rtFilter.url != null) {
                    	// remove current input value
                    	var _disabled = valueInput.attr("disabled") ;
                    	valueInput.attr("disabled", true) ;
                    	data = $(rtFilter.form).serialize() ;
                    	valueInput.attr("disabled", _disabled == null?false:_disabled) ;
                    	data += "&distinct_column=" + (typeof(rtFilter.distinctColumn) == "undefined"?valueInput.attr("name"):rtFilter.distinctColumn) ;
                    	
                    	if (rtFilter.preParams == data) {// 之前的一次参数一致，使用之前的
                            response(filter(rtFilter.rpDatas, that.options.isMultiple ? extractLast(request.term):request.term));
                    	} else {
                    		rtFilter.preParams = data ;
                            $.post( rtFilter.url, data, 
                            function (_data){
                            	//that.availableDatas=_data;
                            	var rpDatas = [] ;
                            	if (rtFilter.isUseRemoteData) {
                            		that.options.data = _data.list;
                            		rpDatas = that.getDatas();
                            		that.availableDatas = rpDatas;
                            	} else {
	                            	//rpDatas[rpDatas.length] = {} ;
	                            	//rpDatas[0][that.options.idProperty] = "" ;
	                            	//rpDatas[0][that.options.textProperty] = "Choose" ;
	                            	_data.list[_data.list.length] = {id:""} ;
	                            	
	                            	for (var i = 0 ; i < that.availableDatas.length ; i ++) {
	                            		var pk = that.availableDatas[i][that.options.idProperty] ;
	                            		for (var j = 0 ; j < _data.list.length ; j ++) {
	                            			if (_data.list[j].id == pk) {
	                            				rpDatas[rpDatas.length] = that.availableDatas[i] ;
	                            				break ;
	                            			}
	                            		}
	                            	}
                            	}
                            	//response(rpDatas);
                            	rtFilter.rpDatas = rpDatas ;
                                response(filter(rpDatas, that.options.isMultiple ? extractLast(request.term):request.term) );
                            } , 
                            "json");
                    	}
                    } else if (that.options.url == null) {
                        response(filter(that.availableDatas, that.options.isMultiple ? extractLast(request.term):request.term) );
                    } else {
                        //E$("countryLoading").show() ;
                        //"/system/dict_countryOptions.action"
                        $.post( that.options.url, {
                            term: extractLast( request.term )
                        }, function (_data){that.availableDatas=_data;response(_data);} , "json");
                    }
                },
                select: function( event, ui ) {
                	  var selectedValue = ui.item[that.options.valueProperty] ;
                	  if (that.options.isMultiple) {
		                    var terms = split( this.value );
		                    // remove the current input
		                    terms.pop();
		                    // add the selected item
		                    //terms.push( ui.item.value );
		                    //terms.push( ui.item.code );
		                    terms.push(selectedValue);
		                    // add placeholder to get the comma-and-space at the end
		                    terms.push( "" );
		                    this.value = selectedValue == ""?selectedValue:terms.join( ", " );
		                    valueInput.val(this.value);
                	  } else {
                		  valueInput.val(selectedValue) ;
                		  value = selectedValue ;
                	  	  this.value = getTextById(ui.item[that.options.idProperty]) ;
                	  }

                      if (valueInput[0].onSelected) {
                    	  valueInput[0].onSelected(event, ui.item) ;
                      }
	                  return false;
                },
                change: function( event, ui ) {
                    if ( !ui.item )
                        return removeIfInvalid( this );
                        //return false ;
                },
                focus: function( event, ui ) {
                    //$( "#project" ).val( ui.item.label );
                	if (that.options.isMultiple) {
                		return false ;
                	}
                    return true;
                }
            })
            .addClass( "ui-widget ui-widget-content ui-corner-left" );

            input.data( "autocomplete" )._renderItem = function( ul, item ) {
                return $( "<li>" )
                    .data( "item.autocomplete", item )
                    .append( "<a>" + getText(item) + "</a>" )
                    .appendTo( ul );
            };



            input.data( "autocomplete" )._suggest= function( items ) {
        		var ul = this.menu.element
        			.empty()
        			.zIndex( this.element.zIndex() + 100 );
        		this._renderMenu( ul, items );
        		this.menu.refresh();

        		// size and position menu
        		ul.show();
        		this._resizeMenu();
        		ul.position( $.extend({
        			of: this.element
        		}, this.options.position ));

        		if ( this.options.autoFocus ) {
        			this.menu.next();
        		}
        	};

        	var downer = null ;
            if (!this.options.isMultiple) {
            	downer = $( "<a></a>" )
	                .attr( "tabIndex", -1 )
	                .attr( "title", "显示所有" )
	                .tooltip()
	                .button({
	                    icons: {
	                        primary: "ui-icon-triangle-1-s"
	                    },
	                    text: false
	                })
	                .appendTo( wrapper )
	                .removeClass( "ui-corner-all" )
	                .addClass( "ui-corner-right ui-combobox-toggle" )
	                .css(this.options.toggleCss)
	                .click(function() {
	                    // close if already visible
	                    if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
	                        input.autocomplete( "close" );
	                        removeIfInvalid( input );
	                        return;
	                    }
	
	                    // work around a bug (likely same cause as #5265)
	                    $( this ).blur();
	
	                    // pass empty string as value to search for, displaying all results
	                    input.autocomplete( "search", "" );
	                    input.focus();
	                });
	            }

                input.tooltip({
                        position: {
                            of: this.button
                        },
                        tooltipClass: "ui-state-highlight"
                    });

                valueInput[0].input = input[0] ;
                valueInput[0].subType = "combox" ;
                valueInput[0].setComboxValue = function () {// get data from list
                	$(input).val(getTextByValue(valueInput.val())) ;
              		if(valueInput[0].onSelected) {
              			valueInput[0].onSelected(null, findData(that.options.valueProperty, valueInput.val())) ;
            		}
                } ;
                
                valueInput[0].setReadonly = function (isReadOnly, isDisable) {
                	if (isReadOnly) {
                		input.autocomplete( "disable" );
                	} else {
                		input.autocomplete( "enable" );
                	}
            		$(input).attr("readOnly", isReadOnly);
            		
            		if (typeof(isDisable) != "undefined") {
                		$(input).attr("disabled", isReadOnly);
            		}
                	
                	if (downer != null) {
                		if (isReadOnly) {                			
                			downer.hide() ;
                		} else {
                			downer.show() ;
                		}
                	}
                } ;
                
        },

        getDatas:function() {
            this.element.val("") ;
            $(this.element[0].input).val("");
            
            var availableDatas = null ;
            if (this.options.data != null) {
                availableDatas = this.options.data ;
            } else if (this.options.dataName != null) {
                availableDatas = g$dict.get(this.options.dataName) ;
            }
            
            if (availableDatas != null) {
            	if (this.options.firstLabel != null || this.options.firstData != null) {
            		var tmp = [] ;
            		var obj = this.options.firstData;
            		if (obj == null){
            		    obj = {} ;
                		obj[this.options.valueProperty] = "" ;
                		if ($.isArray(this.options.textProperty)) {
                			obj[this.options.textProperty[0]] = this.options.firstLabel ;
                		} else {
                			obj[this.options.textProperty] = this.options.firstLabel ;
                		}	
            		}
            		tmp[tmp.length] = obj ;
            		for (var i = 0 ;i < availableDatas.length ;i ++) {
            			tmp[tmp.length]= availableDatas[i] ;
            		}
            		availableDatas = tmp ;
            	}
            }
            
            return availableDatas ;
        },
        
        destroy: function() {
            this.wrapper.remove();
            this.element.show();
            $.Widget.prototype.destroy.call( this );
        },
 
        // _setOptions is called with a hash of all options that are changing
        // always refresh when changing options
        _setOptions: function() {
            // _super and _superApply handle keeping the right this-context
            this._superApply( arguments );
            //this._refresh();
        },

        // _setOption is called for each individual option that is changing
        _setOption: function( key, value ) {
            // prevent invalid color values
        	/*
            if ( /red|green|blue/.test(key) && (value < 0 || value > 255) ) {
                return;
            }*/
            this._super( key, value );
            
        	if (/dictName|data/.test(key)) {
        		this.availableDatas = this.getDatas() ;
        	}
        }
    });
    
    
    
})( jQuery );


//---------------------------------------------------------------------------
//     markit
//---------------------------------------------------------------------------
(function( $ ) {
    


    // the widget definition, where "custom" is the namespace,
    // "colorize" the widget name
    $.widget( "ui.markit", {
        // default options
        options: {
            color:"#000000",
            // callbacks
            add: null,
            save: null,
            remove: null,
            doDelete:null,
            marks:{},
            windowName:"markitWindow123456ju",
            refreshAllAfterSave:true
        },
 
          // the constructor
        _create: function() {
        	
            var container = this.container = $( "<span></span>" ) ;
            var wrapper = this.wrapper = $( "<ul style='display:inline-block;'></ul>" ) ;
            
            container.append(wrapper) ;
            this.element.append(container) ;
            container.addClass("gui-markit") ;
            
            $("<span style='display:inline-block;'></span>")
                .attr( "tabIndex", -1 )
                .attr( "title", "增加标签" )/*
                .tooltip()
                .button({
                    icons: {
                        primary: "ui-icon-plus"
                    },
                    text: false
                })*/
                .appendTo( container )
                //.removeClass( "ui-corner-all" )
                .addClass( "ui-icon ui-icon-plus" )
                .click(function() {
                    $markit.toEdit() ;
                });
            
            var datas = this.element.attr("datas") ;
            var $markit = this;
            
            this.datas = datas.split(",") ;
            
            this.refresh() ;
            
            this._window() ;
            
            if (this.options.refreshAllAfterSave) {
            	if (typeof (window.$markits) == "undefined") {
            		window.$markits = [] ;
            	}
            	
            	window.$markits[window.$markits.length] = this ;
            }
        },
       
        _window:function() {
        	if ($("#" + this.options.windowName).length > 0) {
        		return $("#" + this.options.windowName) ;
        	}
        	
        	var winHtml = "" ;
        	  winHtml += "<div id=\""+this.options.windowName+"\" style=\"display: none;\">";
       		  winHtml += "  <table style='width:100%;'></table>";
       		  winHtml += "  <textarea id=\"rowTemplate\" style=\"display: none;\">";
       		  winHtml += "    <tr id=\"row_{id}\">";
       		  winHtml += "      <td style='width:50%;'><input type=text value=\"{text}\" id=\"text\" style=\"background-color: {color};width:90%;\" onchange=\"$('#isChanged', $(this).parent()).val(1);\"/><input type=hidden value=\"{id}\" id=\"id\"/><input type=hidden value=\"0\" id=\"isChanged\"/></td>";
       		  winHtml += "      <td style='width:10%;'><input type=hidden value=\"{color}\" id=\"color\"/><div class=\"colorSelector\" row_id={id}><div style=\"background-color: {color}\"></div></div></td>";
       		  winHtml += "      <td style='width:20%;'><button onclick='$currentMarkit.save(\"{id}\")'>确定</button></td>";
       		  winHtml += "      <td style='width:20%;'><button onclick='$currentMarkit.doDelete(\"{id}\")' selectBtnDisplay>删除</button></td>";
       		  //winHtml += "      <td><a onclick='$currentMarkit.add(\"{id}\")' selectBtnDisplay>选择</a></td>"
       		  winHtml += "    </tr>";
       		  winHtml += "  </textarea>" ;
       		  winHtml += "</div>";

       		$("body").append(winHtml) ;
       		  
       		return $("#" + this.options.windowName) ;
        }, 
        
        _remove: function() {
            // trigger a callback/event
            this._trigger( "remove" );
        },
        
        doDelete:function(id) {
        	if (!window.confirm("你确认要删除标签配置吗？")) {
        		return ;
        	}

        	$markIt = this ;
        	
        	var $row = $("#" + this.options.windowName + " #row_" + id) ;        
            ajax(
                root + "/system/markIt_delete.action", 
                {"markIt.id":$("#id", $row).val()},
                function(data, textStatus){
                	if (data.code == "0") {
                        delete $markIt._marks()[id] ;
                        $markIt.refresh();
                    	$row.remove();

                    	if (typeof (window.$markits) != "undefined") {
                    		$(window.$markits).each(function(i, e){
                    			e.refresh() ;
                    		}) ;
                    	}
                	}
                }
            );
        },

        _marks:function () {
        	if($.isFunction(this.options.marks)) {
        		return this.options.marks() ;
        	} else {
        		return this.options.marks ;
        	}
        },
        
        add:function (v) {
        	this.addValue(v) ;
        	this.refresh() ;
            $("#" + this.options.windowName).dialog("close");
        },
 
        save:function (id) {
        	var $row = $("#" + this.options.windowName + " #row_" + id) ;
        	var itemValue = $("#id", $row).val() ;
            var $markit = this ;
        	// invoke ajax
        	if (itemValue == "" || $("#isChanged", $row).val() == "1") {
                ajax(
                        root + "/system/markIt_save.action", 
                        {
                			"markIt.id":itemValue, 
                			"markIt.text":$("#text", $row).val(), 
                			"markIt.color":$("#color", $row).val()
                	    },
                        function(data, textStatus){
                        	if (data.code == "0") {
                        	    itemValue = data.data.id ;
                        		$markit._marks()[itemValue] = {
                            			"id":itemValue, 
                            			"text":$("#text", $row).val(), 
                            			"color":$("#color", $row).val()
                            	    } ;
                        		$markit.add(itemValue) ;  
                        		$("#" + $markit.options.windowName).dialog("close");

                            	if (typeof (window.$markits) != "undefined") {
                            		$(window.$markits).each(function(i, e){
                            			e.refresh() ;
                            		}) ;
                            	}
                        	}
                        }
                    );
        	} else {        		
        		this.add(itemValue) ;  
        		$("#" + this.options.windowName).dialog("close");
        	}
        },
        
        toEdit:function () {
            var $table = $("#" + this.options.windowName + " table") ;
            var template = $("#" + this.options.windowName + " #rowTemplate").val() + "" ;        	
            $table.html("");
            for (var i in this._marks()) {
                var e = this._marks()[i] ;
            	var row = template.replaceAll("{id}", e.id) ;
                row = row.replaceAll("{text}", e.text) ;
                row = row.replaceAll("{color}", e.color) ;
                $table.append(row);
            }

            row = template.replaceAll("{id}", "") ;
            row = row.replaceAll("{text}", "") ;
            row = row.replaceAll("{color}", "") ;
            row = row.replaceAll("selectBtnDisplay", "style='display:none'") ;
            $table.append(row);
            
            $currentMarkit = this ;

            $(".colorSelector", $table).each(function (i, e) {
            	$(e).ColorPicker({
                       color: '#0000ff',
                       onShow: function (colpkr) {
                           $(colpkr).fadeIn(500);
                           return false;
                       },
                       onHide: function (colpkr) {
                           $(colpkr).fadeOut(500);
                           return false;
                       },
                       onChange: function (hsb, hex, rgb) {
                    	   var $row = $('#row_' + $(e).attr("row_id"), $table);
                    	   $("#isChanged", $row).val("1") ;
                    	   $("#text", $row).css('backgroundColor', '#' + hex);
                    	   $("#color", $row).val('#' + hex) ;
                           $('div', $(e)).css('backgroundColor', '#' + hex);
                       }
                   });
                
            }) ;
            
        	$("#" + this.options.windowName).dialog({title:"标签编辑", width:500,height:300,modal:true});
        },
 
        addValue:function (v) {
            for (var i = 0 ; i < this.datas.length ; i ++) {
                if (this.datas[i] == v) {
                    return ;
                }
            }
            
            this.datas[this.datas.length] = v ;
            
            if (this.options.add != null) {
                this.options.add(v, this.datas) ;
            }
        },
        
        removeValue:function (_id) {
            var _datas = [] ;
            for (var i = 0 ; i < this.datas.length ; i ++) {
                if (_id == this.datas[i]) {
                    continue ;
                }
                _datas[_datas.length] = this.datas[i];
            }
            this.datas = _datas ;
        },
        
        refresh:function () {
        	this.wrapper.html("") ;
        	var $markit = this;

            for(var i = this.datas.length - 1 ; i >= 0  ; i --) {
            	var _value = this.datas[i];
	            var mark = this._marks()[_value] ;
	            
	            if (typeof(mark) == "undefined") {
	                this.removeValue(_value) ;
	                continue ;
	            }
	            
	            
	            var _text = mark.text ;
	            var _color = mark.color ; 
	            
	            var item = $("<li>" + _text + "&nbsp;&nbsp;</li>");
	            
	            item.css( "background-color", _color) ;
	            item.attr("datas", _value) ;

	            this.wrapper.prepend(item) ;
	            
	            
	            $( "<span style='display:inline-block;'></span>" )
	                .attr( "tabIndex", -1 )
	                .attr( "title", "删除标签" )/*
	                .tooltip()
	                .button({
	                    icons: {
	                        primary: "ui-icon-closethick"
	                    },
	                    text: false
	                })*/
	                .appendTo( item )
	                //.removeClass( "ui-corner-all" )
	                .addClass( "ui-icon ui-icon-closethick" )
	                .click(function() {
	                    
	                    if (window.confirm("你确认要删除标签吗？")) {
		                	var itemValue = $(this).parent().attr("datas") ;
		                	$markit.removeValue(itemValue) ;
		                	$markit.refresh();
		                    
		                    // remove current mark
		                    if ($markit.options.remove) {
		                        $markit.options.remove(itemValue, $markit.datas) ;
		                    }
	                    }
	                	 
	                });
            }
            
        },
        
        
          // events bound via _on are removed automatically
          // revert other modifications here
          _destroy: function() {
              // remove generated elements
              this.wrapper.remove();
          },
     
          // _setOptions is called with a hash of all options that are changing
          _setOptions: function() {
              // _super and _superApply handle keeping the right this-context
              this._superApply( arguments );
          },
     
          // _setOption is called for each individual option that is changing
          _setOption: function( key, value ) {
              this._super( key, value );
          }
    });
})( jQuery );





(function( $ ) {
	$.widget( "ui.autocomplete4tree", $.ui.autocomplete, {
		
		widget: function( content ) {
			return this.menu.element ;
		},
		menuselect: function( event, item ) {

			// back compat for _renderItem using item.autocomplete, via #7810
			// TODO remove the fallback, see #8156
			var previous = this.previous;

			// only trigger when focus was lost (click on menu)
			if ( this.element[0] !== this.document[0].activeElement ) {
				this.element.focus();
				this.previous = previous;
				// #6109 - IE triggers two focus events and the second
				// is asynchronous, so we need to reset the previous
				// term synchronously and asynchronously :-(
				this._delay(function() {
					this.previous = previous;
					this.selectedItem = item;
				});
			}

			if ( false !== this._trigger( "select", event, { item: item } ) ) {
				this._value( item.value );
			}
			// reset the term after the select event
			// this allows custom select handling to work properly
			this.term = this._value();

			this.close( event );
			this.selectedItem = item;
			
			return this ;
		},
		
		_create: function() {

			var that = this ;
			
			this._superApply( arguments );
			
			this._off(this.menu.element);
			
			this._on( this.menu.element, {
				mousedown: function( event ) {
					// prevent moving focus out of the text field
					event.preventDefault();

					// IE doesn't prevent moving focus even with event.preventDefault()
					// so we set a flag to know when we should ignore the blur event
					this.cancelBlur = true;
				},
				menufocus: function( event, ui ) {
					// #7024 - Prevent accidental activation of menu items in Firefox
					if ( this.isNewMenu ) {
						this.isNewMenu = false;
						if ( event.originalEvent && /^mouse/.test( event.originalEvent.type ) ) {
							this.menu.blur();

							this.document.one( "mousemove", function() {
								$( event.target ).trigger( event.originalEvent );
							});

							return;
						}
					}

					// back compat for _renderItem using item.autocomplete, via #7810
					// TODO remove the fallback, see #8156
					var item = ui.item.data( "ui-autocomplete-item" ) || ui.item.data( "item.autocomplete" );
					if ( false !== this._trigger( "focus", event, { item: item } ) ) {
						// use value to match what will end up in the input, if it was a key event
						if ( event.originalEvent && /^key/.test( event.originalEvent.type ) ) {
							this._value( item.value );
						}
					} else {
						// Normally the input is populated with the item's value as the
						// menu is navigated, causing screen readers to notice a change and
						// announce the item. Since the focus event was canceled, this doesn't
						// happen, so we update the live region so that screen readers can
						// still notice the change and announce it.
						this.liveRegion.text( item.value );
					}
				}
			});
			
			
            var setting = this.options.treeSetting ;

    		setting.callback = {
				onClick: function (event, treeId, treeNode) {that.menuselect( event, treeNode);}
			};
    		
    		var $treeDiv = $("<div></div>");
    		this.menu.element.css("max-height", "200px");
    		this.menu.element.css("overflow", "hidden");
    		this.menu.element.append($treeDiv);
    		
    		this.zTree = $.fn.zTree.init($treeDiv, setting, []);
    		this.menu.element.removeClass("ui-menu");
    		$treeDiv.addClass("ztree");
    		$treeDiv.css("max-height", "190px");
    		$treeDiv.css("overflow", "auto");
		},
		
		reloadTree:function(items) {
			var root = $.fn.zTree._z.data.getRoot(this.zTree);
			var setting = this.zTree.setting;
			setting.treeObj.empty();
			childKey = setting.data.key.children;
			root[childKey] = this.zTree.transformTozTreeNodes(items);
			$.fn.zTree._z.view.createNodes(setting, 0, root[setting.data.key.children]);
		},
		
		_destroy:function() {
			this.zTree.destroy();
			this._superApply( arguments );
		}
	});
    $.widget( "ui.combotree", {
        // default options
        options: {
        	url:null, 
        	textProperty:[], 
        	idProperty:"PK_ID", 
        	data:null, 
        	dataName:null, 
        	valueProperty:null, 
        	isMultiple:false, 
        	firstLabel:null, 
        	firstData:null, 
        	toggleCss:{},
        	remoteFilter:{url:null, from:null, preParams:null}
        },

        _create: function() {
            var that = this;
            var valueInput = this.element.hide() ;
            var value = valueInput.val();
            var wrapper = this.wrapper = $( "<span>" )
                    .addClass( "ui-combobox" )
                    .insertAfter( valueInput );

            function removeIfInvalid(element) {
                var text = getTextByValue(valueInput.val()) ;
                input.val(text) ;
                
                var valid = (text!="") ;
                
                if ( !valid ) {
                    // remove invalid value, as it didn't match anything
                    $( element )
                        .val( "" )
                        .attr( "title", value + "  未与任何选项匹配！" )
                        .tooltip( "open" );
                    valueInput.val( "" );
                    setTimeout(function() {
                        input.tooltip( "close" ).attr( "title", "" );
                    }, 2500 );
                    input.data( "autocomplete" ).term = "";
                    return false;
                }
            }
            

        this.availableDatas = this.getDatas() ;
        
        function split( val ) {
            return val.split( /,\s*/ );
        }
        function extractLast( term ) {
            return split( term ).pop();
        }
        
        function getText(_item) {
        	  var ret = "" ;
        	  
            for (var i = 0 ; i < that.options.textProperty.length ; i ++) {
            	var txt = _item[that.options.textProperty[i]] ;
            	if (txt == null) {
            		txt = "" ;
            	}
            	
            	if (txt == "") {
            		continue ;
            	}
            	
                if (ret != "") {
                    ret += "/" ;
                }
                
                ret += txt ;
            }
            
            return ret ;
        }
        
        function getTextById(val) {
            var data = findData(that.options.idProperty, val);
            if (data == null) {
            	return "" ;
            }
            return getText(data) ;
        }
        
        function getTextByValue(val) {
            var data = findData(that.options.valueProperty, val);
            if (data == null) {
            	return val ;
            }
            return getText(data) ;
        }
        
        function findData(pro, val) {
        	if (that.availableDatas == null) {
        		return null ;
        	}
            for (var i = 0 ; i < that.availableDatas.length ; i ++) {
                if (that.availableDatas[i][pro] + "" == val + "") {
                    return that.availableDatas[i];
                }
            }
        	return null ;
        }
        
        function filter(array, term) {
            var matcher = new RegExp( $.ui.autocomplete.escapeRegex(term), "i" );
            return $.grep( array, function(value) {
                for (var i = 0 ; i < that.options.textProperty.length ; i ++) {
                	  if (matcher.test(value[that.options.textProperty[i]])) {
                	      return true ;
                	  }
                }
                
                return false ;
            });
        }
        
        var input = $( "<input type='search-text'/>" )
            .appendTo( wrapper )
            .val( value )
            .attr("title", "")
            .attr("id" , valueInput.attr("id") + ".text")
            .addClass( "ui-state-default ui-combobox-input" )
            .autocomplete4tree({
            	treeSetting:this.options.treeSetting,
                delay: 200,
                minLength: 0,
                source: function( request, response ) {

                	var rtFilter = that.options.remoteFilter ;
                    if (rtFilter.url != null) {
                    	// remove current input value
                    	var _disabled = valueInput.attr("disabled") ;
                    	valueInput.attr("disabled", true) ;
                    	data = $(rtFilter.form).serialize() ;
                    	valueInput.attr("disabled", _disabled == null?false:_disabled) ;
                    	data += "&distinct_column=" + (typeof(rtFilter.distinctColumn) == "undefined"?valueInput.attr("name"):rtFilter.distinctColumn) ;
                    	
                    	if (rtFilter.preParams == data) {// 之前的一次参数一致，使用之前的
                            response(filter(rtFilter.rpDatas, that.options.isMultiple ? extractLast(request.term):request.term));
                    	} else {
                    		rtFilter.preParams = data ;
                            $.post( rtFilter.url, data, 
                            function (_data){
                            	//that.availableDatas=_data;
                            	var rpDatas = [] ;
                            	if (rtFilter.isUseRemoteData) {
                            		that.options.data = _data.list;
                            		rpDatas = that.getDatas();
                            		that.availableDatas = rpDatas;
                            	} else {
	                            	//rpDatas[rpDatas.length] = {} ;
	                            	//rpDatas[0][that.options.idProperty] = "" ;
	                            	//rpDatas[0][that.options.textProperty] = "Choose" ;
	                            	_data.list[_data.list.length] = {id:""} ;
	                            	
	                            	for (var i = 0 ; i < that.availableDatas.length ; i ++) {
	                            		var pk = that.availableDatas[i][that.options.idProperty] ;
	                            		for (var j = 0 ; j < _data.list.length ; j ++) {
	                            			if (_data.list[j].id == pk) {
	                            				rpDatas[rpDatas.length] = that.availableDatas[i] ;
	                            				break ;
	                            			}
	                            		}
	                            	}
                            	}
                            	//response(rpDatas);
                            	rtFilter.rpDatas = rpDatas ;
                                response(filter(rpDatas, that.options.isMultiple ? extractLast(request.term):request.term) );
                            } , 
                            "json");
                    	}
                    } else if (that.options.url == null) {
                        response(filter(that.availableDatas, that.options.isMultiple ? extractLast(request.term):request.term) );
                    } else {
                        $.post( that.options.url, {
                            term: extractLast( request.term )
                        }, function (_data){that.availableDatas=_data;response(_data);} , "json");
                    }
                },
                select: function( event, ui ) {
                	  var _input = input[0];
                	  var selectedValue = ui.item[that.options.valueProperty] ;
                	  if (that.options.isMultiple) {
		                    var terms = split( _input.value );
		                    // remove the current input
		                    terms.pop();
		                    // add the selected item
		                    //terms.push( ui.item.value );
		                    //terms.push( ui.item.code );
		                    terms.push(selectedValue);
		                    // add placeholder to get the comma-and-space at the end
		                    terms.push( "" );
		                    _input.value = selectedValue == ""?selectedValue:terms.join( ", " );
		                    valueInput.val(_input.value);
                	  } else {
                		  valueInput.val(selectedValue) ;
                		  value = selectedValue ;
                		  _input.value = getTextById(ui.item[that.options.idProperty]) ;
                	  }

                      if (valueInput[0].onSelected) {
                    	  valueInput[0].onSelected(event, ui.item) ;
                      }
	                  return false;
                },
                change: function( event, ui ) {
                    if ( !ui.item )
                        return removeIfInvalid( this );
                        //return false ;
                },
                focus: function( event, ui ) {
                    //$( "#project" ).val( ui.item.label );
                	if (that.options.isMultiple) {
                		return false ;
                	}
                    return true;
                }
            })
            .addClass( "ui-widget ui-widget-content ui-corner-left" );

        
            input.data( "autocomplete4tree" )._renderMenu= function( ul, items ) {
            	input.autocomplete4tree("reloadTree", items);
        		//$treeDiv.width("280px");
        	};



            input.data( "autocomplete4tree" )._suggest= function( items ) {
        		var ul = this.menu.element
        			//.empty()
        			.zIndex( this.element.zIndex() + 100 );
        		this._renderMenu( ul, items );
        		//this.menu.refresh();

        		// size and position menu
        		ul.show();
        		this._resizeMenu();
        		ul.position( $.extend({
        			of: this.element
        		}, this.options.position ));

        		if ( this.options.autoFocus ) {
        			this.menu.next();
        		}
        	};

        	var downer = null ;
            if (!this.options.isMultiple) {
            	downer = $( "<a></a>" )
	                .attr( "tabIndex", -1 )
	                .attr( "title", "显示所有" )
	                .tooltip()
	                .button({
	                    icons: {
	                        primary: "ui-icon-triangle-1-s"
	                    },
	                    text: false
	                })
	                .appendTo( wrapper )
	                .removeClass( "ui-corner-all" )
	                .addClass( "ui-corner-right ui-combobox-toggle" )
	                .css(this.options.toggleCss)
	                .click(function() {
	                    // close if already visible
	                    if ( input.autocomplete4tree( "widget" ).is( ":visible" ) ) {
	                        input.autocomplete4tree( "close" );
	                        removeIfInvalid( input );
	                        return;
	                    }
	
	                    // work around a bug (likely same cause as #5265)
	                    $( this ).blur();
	
	                    // pass empty string as value to search for, displaying all results
	                    input.autocomplete4tree( "search", "" );
	                    input.focus();
	                });
	            }

                input.tooltip({
                        position: {
                            of: this.button
                        },
                        tooltipClass: "ui-state-highlight"
                    });

                valueInput[0].input = input[0] ;
                valueInput[0].subType = "combox" ;
                valueInput[0].setComboxValue = function () {// get data from list
                	$(input).val(getTextByValue(valueInput.val())) ;
              		if(valueInput[0].onSelected) {
              			valueInput[0].onSelected(null, findData(that.options.valueProperty, valueInput.val())) ;
            		}
                } ;
                
                valueInput[0].setReadonly = function (isReadOnly, isDisable) {
                	if (isReadOnly) {
                		input.autocomplete4tree( "disable" );
                	} else {
                		input.autocomplete4tree( "enable" );
                	}
            		$(input).attr("readOnly", isReadOnly);
            		
            		if (typeof(isDisable) != "undefined") {
                		$(input).attr("disabled", isReadOnly);
            		}
                	
                	if (downer != null) {
                		if (isReadOnly) {                			
                			downer.hide() ;
                		} else {
                			downer.show() ;
                		}
                	}
                } ;
                
        },

        getDatas:function() {
            this.element.val("") ;
            $(this.element[0].input).val("");
            
            var availableDatas = null ;
            if (this.options.data != null) {
                availableDatas = this.options.data ;
            } else if (this.options.dataName != null) {
                availableDatas = g$dict.get(this.options.dataName) ;
            }
            
            if (availableDatas != null) {
            	if (this.options.firstLabel != null || this.options.firstData != null) {
            		var tmp = [] ;
            		var obj = this.options.firstData;
            		if (obj == null){
            		    obj = {} ;
                		obj[this.options.valueProperty] = "" ;
                		if ($.isArray(this.options.textProperty)) {
                			obj[this.options.textProperty[0]] = this.options.firstLabel ;
                		} else {
                			obj[this.options.textProperty] = this.options.firstLabel ;
                		}	
            		}
            		tmp[tmp.length] = obj ;
            		for (var i = 0 ;i < availableDatas.length ;i ++) {
            			tmp[tmp.length]= availableDatas[i] ;
            		}
            		availableDatas = tmp ;
            	}
            }
            
            return availableDatas ;
        },
        
        destroy: function() {
            this.wrapper.remove();
            this.element.show();
            $.Widget.prototype.destroy.call( this );
        },
 
        // _setOptions is called with a hash of all options that are changing
        // always refresh when changing options
        _setOptions: function() {
            // _super and _superApply handle keeping the right this-context
            this._superApply( arguments );
            //this._refresh();
        },

        // _setOption is called for each individual option that is changing
        _setOption: function( key, value ) {
            // prevent invalid color values
        	/*
            if ( /red|green|blue/.test(key) && (value < 0 || value > 255) ) {
                return;
            }*/
            this._super( key, value );
            
        	if (/dictName|data/.test(key)) {
        		this.availableDatas = this.getDatas() ;
        	}
        }
    });
    
    
    
})( jQuery );





/*
(function( $ ) {
	
	
	
	
var requestIndex = 0;

$.widget( "ui.dropdowntree", {
	version: "1.9.0",
	defaultElement: "<input>",
	options: {
		appendTo: "body",
		autoFocus: false,
		delay: 300,
		minLength: 1,
		position: {
			my: "left top",
			at: "left bottom",
			collision: "none"
		},
		source: null,

		// callbacks
		change: null,
		close: null,
		focus: null,
		open: null,
		response: null,
		search: null,
		select: null
	},

	pending: 0,

	_create: function() {
		// Some browsers only repeat keydown events, not keypress events,
		// so we use the suppressKeyPress flag to determine if we've already
		// handled the keydown event. #7269
		// Unfortunately the code for & in keypress is the same as the up arrow,
		// so we use the suppressKeyPressRepeat flag to avoid handling keypress
		// events when we know the keydown event was used to modify the
		// search term. #7799
		var suppressKeyPress, suppressKeyPressRepeat, suppressInput;

		var that = this ;
		
		this.isMultiLine = this._isMultiLine();
		this.valueMethod = this.element[ this.element.is( "input,textarea" ) ? "val" : "text" ];
		this.isNewMenu = true;


		var setting = {
				data: {
					key: {
						name: "business_name"
					},
					simpleData: {
						enable: true,
						idKey: "PK_ID",
						pIdKey: "business_id_upper",
						rootPId: null
					}
				},
				callback: {
					onClick: function (event, treeId, treeNode) {
						alert(treeNode.PK_ID);
						that._value(treeNode.PK_ID);
						alert(that.element.val());
					}
				}
			};
		that.options.textProperty = ["business_name"];
        function filter(array, term) {
            var matcher = new RegExp( $.ui.autocomplete.escapeRegex(term), "i" );
            return $.grep( array, function(value) {
                for (var i = 0 ; i < that.options.textProperty.length ; i ++) {
                	  if (matcher.test(value[that.options.textProperty[i]])) {
                	      return true ;
                	  }
                }
                
                return false ;
            });
        }
		
		this.options.source = function( request, response ) {
			response(filter(that.options.data, request.term));
			 //response(that.options.data);
		};
		
		this.element
			.addClass( "ui-autocomplete-input" )
			.attr( "autocomplete", "off" );

		this._on({
			keydown: function( event ) {
				if ( this.element.prop( "readOnly" ) ) {
					suppressKeyPress = true;
					suppressInput = true;
					suppressKeyPressRepeat = true;
					return;
				}

				suppressKeyPress = false;
				suppressInput = false;
				suppressKeyPressRepeat = false;
				var keyCode = $.ui.keyCode;
				switch( event.keyCode ) {
				case keyCode.PAGE_UP:
					suppressKeyPress = true;
					this._move( "previousPage", event );
					break;
				case keyCode.PAGE_DOWN:
					suppressKeyPress = true;
					this._move( "nextPage", event );
					break;
				case keyCode.UP:
					suppressKeyPress = true;
					this._keyEvent( "previous", event );
					break;
				case keyCode.DOWN:
					suppressKeyPress = true;
					this._keyEvent( "next", event );
					break;
				case keyCode.ENTER:
				case keyCode.NUMPAD_ENTER:
					// when menu is open and has focus
					if ( this.menu.active ) {
						// #6055 - Opera still allows the keypress to occur
						// which causes forms to submit
						suppressKeyPress = true;
						event.preventDefault();
						this.menu.select( event );
					}
					break;
				case keyCode.TAB:
					if ( this.menu.active ) {
						this.menu.select( event );
					}
					break;
				case keyCode.ESCAPE:
					if ( this.menu.element.is( ":visible" ) ) {
						this._value( this.term );
						this.close( event );
						// Different browsers have different default behavior for escape
						// Single press can mean undo or clear
						// Double press in IE means clear the whole form
						event.preventDefault();
					}
					break;
				default:
					suppressKeyPressRepeat = true;
					// search timeout should be triggered before the input value is changed
					this._searchTimeout( event );
					break;
				}
			},
			keypress: function( event ) {
				if ( suppressKeyPress ) {
					suppressKeyPress = false;
					event.preventDefault();
					return;
				}
				if ( suppressKeyPressRepeat ) {
					return;
				}

				// replicate some key handlers to allow them to repeat in Firefox and Opera
				var keyCode = $.ui.keyCode;
				switch( event.keyCode ) {
				case keyCode.PAGE_UP:
					this._move( "previousPage", event );
					break;
				case keyCode.PAGE_DOWN:
					this._move( "nextPage", event );
					break;
				case keyCode.UP:
					this._keyEvent( "previous", event );
					break;
				case keyCode.DOWN:
					this._keyEvent( "next", event );
					break;
				}
			},
			input: function( event ) {
				if ( suppressInput ) {
					suppressInput = false;
					event.preventDefault();
					return;
				}
				this._searchTimeout( event );
			},
			focus: function() {
				this.selectedItem = null;
				this.previous = this._value();
			},
			blur: function( event ) {
				if ( this.cancelBlur ) {
					delete this.cancelBlur;
					return;
				}

				clearTimeout( this.searching );
				this.close( event );
				this._change( event );
			}
		});

		this._initSource();
		this.menu = $( "<div>" )
			.appendTo( this.document.find( this.options.appendTo || "body" )[ 0 ] )
			.addClass("ztree")
			.zIndex( this.element.zIndex() + 1 )
			.hide();
		

		
		
		this.zTree = $.fn.zTree.init(this.menu, setting, this.options.data);

		
		this._on( this.menu.element, {
			mousedown: function( event ) {
				// prevent moving focus out of the text field
				event.preventDefault();

				// IE doesn't prevent moving focus even with event.preventDefault()
				// so we set a flag to know when we should ignore the blur event
				this.cancelBlur = true;
				this._delay(function() {
					delete this.cancelBlur;
				});

				// clicking on the scrollbar causes focus to shift to the body
				// but we can't detect a mouseup or a click immediately afterward
				// so we have to track the next mousedown and close the menu if
				// the user clicks somewhere outside of the autocomplete
				var menuElement = this.menu.element[ 0 ];
				if ( !$( event.target ).closest( ".ui-menu-item" ).length ) {
					this._delay(function() {
						var that = this;
						this.document.one( "mousedown", function( event ) {
							if ( event.target !== that.element[ 0 ] &&
									event.target !== menuElement &&
									!$.contains( menuElement, event.target ) ) {
								that.close();
							}
						});
					});
				}
			},
			menufocus: function( event, ui ) {
				// #7024 - Prevent accidental activation of menu items in Firefox
				if ( this.isNewMenu ) {
					this.isNewMenu = false;
					if ( event.originalEvent && /^mouse/.test( event.originalEvent.type ) ) {
						this.menu.blur();

						this.document.one( "mousemove", function() {
							$( event.target ).trigger( event.originalEvent );
						});

						return;
					}
				}

				// back compat for _renderItem using item.autocomplete, via #7810
				// TODO remove the fallback, see #8156
				var item = ui.item.data( "ui-autocomplete-item" ) || ui.item.data( "item.autocomplete" );
				if ( false !== this._trigger( "focus", event, { item: item } ) ) {
					// use value to match what will end up in the input, if it was a key event
					if ( event.originalEvent && /^key/.test( event.originalEvent.type ) ) {
						this._value( item.value );
					}
				} else {
					// Normally the input is populated with the item's value as the
					// menu is navigated, causing screen readers to notice a change and
					// announce the item. Since the focus event was canceled, this doesn't
					// happen, so we update the live region so that screen readers can
					// still notice the change and announce it.
					this.liveRegion.text( item.value );
				}
			},
			menuselect: function( event, ui ) {
				// back compat for _renderItem using item.autocomplete, via #7810
				// TODO remove the fallback, see #8156
				var item = ui.item.data( "ui-autocomplete-item" ) || ui.item.data( "item.autocomplete" ),
					previous = this.previous;

				// only trigger when focus was lost (click on menu)
				if ( this.element[0] !== this.document[0].activeElement ) {
					this.element.focus();
					this.previous = previous;
					// #6109 - IE triggers two focus events and the second
					// is asynchronous, so we need to reset the previous
					// term synchronously and asynchronously :-(
					this._delay(function() {
						this.previous = previous;
						this.selectedItem = item;
					});
				}

				if ( false !== this._trigger( "select", event, { item: item } ) ) {
					this._value( item.value );
				}
				// reset the term after the select event
				// this allows custom select handling to work properly
				this.term = this._value();

				this.close( event );
				this.selectedItem = item;
			}
		});
		

		this.liveRegion = $( "<span>", {
				role: "status",
				"aria-live": "polite"
			})
			.addClass( "ui-helper-hidden-accessible" )
			.insertAfter( this.element );

		if ( $.fn.bgiframe ) {
			 this.menu.bgiframe();
		}

		// turning off autocomplete prevents the browser from remembering the
		// value when navigating through history, so we re-enable autocomplete
		// if the page is unloaded before the widget is destroyed. #7790
		this._on( this.window, {
			beforeunload: function() {
				this.element.removeAttr( "autocomplete" );
			}
		});
	},

	_destroy: function() {
		clearTimeout( this.searching );
		this.element
			.removeClass( "ui-autocomplete-input" )
			.removeAttr( "autocomplete" );
		this.zTree.destroy();
		this.liveRegion.remove();
	},

	_setOption: function( key, value ) {
		this._super( key, value );
		if ( key === "source" ) {
			this._initSource();
		}
		if ( key === "appendTo" ) {
			this.menu.element.appendTo( this.document.find( value || "body" )[0] );
		}
		if ( key === "disabled" && value && this.xhr ) {
			this.xhr.abort();
		}
	},

	_isMultiLine: function() {
		// Textareas are always multi-line
		if ( this.element.is( "textarea" ) ) {
			return true;
		}
		// Inputs are always single-line, even if inside a contentEditable element
		// IE also treats inputs as contentEditable
		if ( this.element.is( "input" ) ) {
			return false;
		}
		// All other element types are determined by whether or not they're contentEditable
		return this.element.prop( "isContentEditable" );
	},

	_initSource: function() {
		var array, url,
			that = this;
		if ( $.isArray(this.options.source) ) {
			array = this.options.source;
			this.source = function( request, response ) {
				response( $.ui.autocomplete.filter( array, request.term ) );
			};
		} else if ( typeof this.options.source === "string" ) {
			url = this.options.source;
			this.source = function( request, response ) {
				if ( that.xhr ) {
					that.xhr.abort();
				}
				that.xhr = $.ajax({
					url: url,
					data: request,
					dataType: "json",
					success: function( data, status ) {
						response( data );
					},
					error: function() {
						response( [] );
					}
				});
			};
		} else {
			this.source = this.options.source;
		}
	},

	_searchTimeout: function( event ) {
		clearTimeout( this.searching );
		this.searching = this._delay(function() {
			// only search if the value has changed
			if ( this.term !== this._value() ) {
				this.selectedItem = null;
				this.search( null, event );
			}
		}, this.options.delay );
	},

	search: function( value, event ) {
		value = value != null ? value : this._value();

		// always save the actual value, not the one passed as an argument
		this.term = this._value();

		if ( value.length < this.options.minLength ) {
			return this.close( event );
		}

		if ( this._trigger( "search", event ) === false ) {
			return;
		}

		return this._search( value );
	},

	_search: function( value ) {
		this.pending++;
		this.element.addClass( "ui-autocomplete-loading" );
		this.cancelSearch = false;

		this.source( { term: value }, this._response() );
	},

	_response: function() {
		var that = this,
			index = ++requestIndex;

		return function( content ) {
			if ( index === requestIndex ) {
				that.__response( content );
			}

			that.pending--;
			if ( !that.pending ) {
				that.element.removeClass( "ui-autocomplete-loading" );
			}
		};
	},

	__response: function( content ) {
		if ( content ) {
			content = this._normalize( content );
		}
		this._trigger( "response", null, { content: content } );
		if ( !this.options.disabled && content && content.length && !this.cancelSearch ) {
			this._suggest( content );
			this._trigger( "open" );
		} else {
			// use ._close() instead of .close() so we don't cancel future searches
			this._close();
		}
	},

	close: function( event ) {
		this.cancelSearch = true;
		this._close( event );
	},

	_close: function( event ) {
		if ( this.menu.is( ":visible" ) ) {
			this.menu.hide();
			this.menu.blur();
			this.isNewMenu = true;
			this._trigger( "close", event );
		}
	},

	_change: function( event ) {
		if ( this.previous !== this._value() ) {
			this._trigger( "change", event, { item: this.selectedItem } );
		}
	},

	_normalize: function( items ) {
		// assume all items have the right format when the first item is complete
		if ( items.length && items[0].label && items[0].value ) {
			return items;
		}
		return $.map( items, function( item ) {
			if ( typeof item === "string" ) {
				return {
					label: item,
					value: item
				};
			}
			return $.extend({
				label: item.label || item.value,
				value: item.value || item.label
			}, item );
		});
	},

	_suggest: function( items ) {
		var ul = this.menu
			.zIndex( this.element.zIndex() + 1 );
		this._renderMenu( ul, items );
		
		var root = $.fn.zTree._z.data.getRoot(this.zTree);
		var setting = this.zTree.setting;
		setting.treeObj.empty();
		childKey = setting.data.key.children;
		root[childKey] = this.zTree.transformTozTreeNodes(items);
		$.fn.zTree._z.view.createNodes(setting, 0, root[setting.data.key.children]);

		// size and position menu
		ul.show();
		this._resizeMenu();
		ul.position( $.extend({
			of: this.element
		}, this.options.position ));

		if ( this.options.autoFocus ) {
			this.menu.next();
		}
		
		$.zTree.reloadDatas = function (zNodes) {

			this.setting.treeObj.empty();
			var root = data.getRoot(setting),
			childKey = setting.data.key.children;
			zNodes = zNodes ? tools.clone(tools.isArray(zNodes)? zNodes : [zNodes]) : root[setting.data.key.children];
			if (setting.data.simpleData.enable) {
				root[childKey] = data.transformTozTreeFormat(setting, zNodes);
			} else {
				root[childKey] = zNodes;
			}

			data.initRoot(setting);
			root[setting.data.key.children] = nodes;
			data.initCache(setting);
			view.createNodes(setting, 0, root[setting.data.key.children]);
		};
		
		
		this.zTree.reloadDatas(items);

	},

	_resizeMenu: function() {
		var ul = this.menu;
		ul.outerWidth( Math.max(
			// Firefox wraps long text (possibly a rounding bug)
			// so we add 1px to avoid the wrapping (#7513)
			ul.width( "" ).outerWidth() + 1,
			this.element.outerWidth()
		) );
	},

	_renderMenu: function( ul, items ) {
		
		var that = this;
		$.each( items, function( index, item ) {
			that._renderItemData( ul, item );
		});
		this.zTree.refresh();
		 
	},

	_renderItemData: function( ul, item ) {
		return this._renderItem( ul, item ).data( "ui-autocomplete-item", item );
	},

	_renderItem: function( ul, item ) {
		return $( "<li>" )
			.append( $( "<a>" ).text( item.label ) )
			.appendTo( ul );
	},

	_move: function( direction, event ) {
		if ( !this.menu.is( ":visible" ) ) {
			this.search( null, event );
			return;
		}
		if ( this.menu.isFirstItem() && /^previous/.test( direction ) ||
				this.menu.isLastItem() && /^next/.test( direction ) ) {
			this._value( this.term );
			this.menu.blur();
			return;
		}
		this.menu[ direction ]( event );
	},

	widget: function() {
		return this.menu;
	},

	_value: function( value ) {
		return this.valueMethod.apply( this.element, arguments );
	},

	_keyEvent: function( keyEvent, event ) {
		if ( !this.isMultiLine || this.menu.is( ":visible" ) ) {
			this._move( keyEvent, event );

			// prevents moving cursor to beginning/end of the text field in some browsers
			event.preventDefault();
		}
	}
});

})( jQuery );

*/




$(function() {
    // the widget definition, where "custom" is the namespace,
    // "colorize" the widget name
$.widget( "g.dataTable", {
    // default options
    options: {
    	height:null,
    	width:null,
    	table:null
    },

    // the constructor
    _create: function() {

        var height = this.options.height ;
        var width = this.options.width ;
        var scrollBarWidth = this.getScrollBarWidth() ;
        
        if (height == null) {
            height = 400 ;
        }
        
        if (width == null) {
            width = 1000 ;
        }

        var $table = this.element ;
        $table.css({float: "left"}) ;

        $("th", $table).each(function (){
            $(this).css("padding", "8px 0px") ;
        }) ;
        
        height = (window.innerHeight||document.documentElement.clientHeight) - $table.offset().top - 60 ;
        
        var id = $table.attr("id") ; 
        var innerWidth = $table.outerWidth() + scrollBarWidth ;

        var $container = $("<div></div>") ;
        $container.attr("id", id + "_container_div") ;
        $container.css({position: "relative"}) ;
        $container.css({width: "100%"}) ;
        $container.css({height: height + "px", border:'1px solid #83BBD9'}) ;
        $table.parent().append($container) ;
        
        if ($container.width() <= innerWidth) {//alert($container.width()) ;
        	innerWidth = $container.width() ;
        }
        
        var $headDiv =  $("<div></div>") ;
        $container.append($headDiv) ;
        $headDiv.attr("id", id + "_head_div") ;
        $headDiv.css({position: "absolute", top: "0px", overflow: "hidden", width:(innerWidth-scrollBarWidth) + "px"}) ;
        
        var $bodyDiv =  $("<div></div>") ;
        $container.append($bodyDiv) ;
        $bodyDiv.attr("id", id + "_body_div") ;
        $bodyDiv.css({position: "absolute", overflow: "auto", width:(innerWidth) + "px"}) ;
        $bodyDiv.on("scroll", function(){
        	$headDiv.scrollLeft($bodyDiv.scrollLeft());
        }) ;
        
        var $body = $bodyDiv;//$('<div style="overflow:auto;height:'+height+';width:'+width+';"></div>') ;
        var $newTable = $table.clone() ;
        $body.append($newTable) ;
        //$table.parent().append($body) ;
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
        $table.attr("id", id + "_head") ;
        $headDiv.height($table.height()) ;
        $bodyDiv.css("top", $table.height() + "px") ;
        $bodyDiv.css("height",(height - $table.height()) + "px") ;

        $headDiv.append($table.clone()) ;
        $table.remove() ;
        

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
            }/* else if (e.type == 'dblclick') {
                if (!$(this).hasClass("selected_row")) {
                    $(this).toggleClass("selected_row");
                    var $checkbox = $("#ids", this) ;
                    if ($checkbox.length > 0) {
                        $checkbox[0].checked = true ;
                    }
                }
            }*/
        });
    },

    getScrollBarWidth:function(){ 
        // call after document is finished loading
    	/*
        var el= document.createElement('div');
        el.style.display= 'hidden';
        el.style.overflow= 'scroll';
        document.body.appendChild(el);
        var h= el.offsetHeight-el.clientHeight;
        document.body.removeChild(el);
        return h;*/

		var width = -1;
	
		if(width == -1) {
		    var parent = $('<div style="width:50px;height:50px;overflow:auto"><div/></div>').appendTo('body');
		    var child=parent.children();
		    width=child.innerWidth()-child.height(99).innerWidth();
		    parent.remove();
		}
	
		return width;
    },
    
    
    // events bound via _on are removed automatically
    // revert other modifications here
     _destroy: function() {
        // remove generated elements
    	//this.$toolbar.remove() ;
    },

    // _setOptions is called with a hash of all options that are changing
    // always refresh when changing options
    _setOptions: function() {
       // _super and _superApply handle keeping the right this-context
       this._superApply( arguments );
    },

    // _setOption is called for each individual option that is changing
    _setOption: function( key, value ) {
       // prevent invalid color values
       this._super( key, value );
    }
});


});


























$(function() {
	$.datepicker.regional['zh-CN'] = {  
        closeText: '关闭',  
        prevText: '<上月',  
        nextText: '下月>',  
        currentText: '今天',  
        monthNames: ['一月','二月','三月','四月','五月','六月',  
        '七月','八月','九月','十月','十一月','十二月'],  
        monthNamesShort: ['一','二','三','四','五','六',  
        '七','八','九','十','十一','十二'],  
        dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
        dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
        dayNamesMin: ['日','一','二','三','四','五','六'],  
        weekHeader: '周',  
        dateFormat: 'yy-mm-dd',
        changeMonth: true,
        changeYear: true,
        firstDay: 1,  
        isRTL: false,  
        showMonthAfterYear: true,  
        yearSuffix: '年',
        showButtonPanel: true,
        beforeShow: function(input) {
            $(input).css({
                "position": "relative",
                "z-index": 99
            });
        }
	};  
    $.datepicker.setDefaults( $.datepicker.regional[ "zh-CN" ] );
    
	//wrap up the redraw function with our new shiz
	var org_genHtml_func = $.datepicker._generateHTML; //record the original
	$.datepicker._generateHTML = function(inst){
		var thishtml = $( org_genHtml_func.call($.datepicker, inst) ); //call the original

		$('.ui-datepicker-current, .ui-datepicker-close', thishtml).hide() ;
		
		//todayBtn$.removeClass("ui-priority-secondary") ;
		//todayBtn$.addClass("ui-priority-primary") ;
		
		thishtml = $('<div />').append(thishtml); //add a wrapper div for jQuery context
		
		//locate the button panel and add our button - with a custom css class.
		$('.ui-datepicker-buttonpane', thishtml).append(
			$('<button class="\
				ui-datepicker-clear ui-state-default ui-priority-primary ui-corner-all\
				"\>清除</button>'
			).click(function(){
				inst.input.attr('value', '');
				inst.input.datepicker('hide');
			})
		);
		
		thishtml = thishtml.children(); //remove the wrapper div
		
		return thishtml; //assume okay to return a jQuery
	};
	
	
});








