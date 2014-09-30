$(function() {
	
	g$keyboard = {   
		C:$.extend({
            UP: 38,
            F1:112,
            F2:113,
            F3:114,
            F4:115,
            F5:116,
            F6:117,
            F7:118,
            F8:119,
            F9:120,
            F10:121,
            F11:122,
            F12:123,
            ALT:18,
            CTRL:17,
            SHIFT:16,
            D:68,
            S:83,
            L:17,
            CAPS_LOCK:20
        }, $.ui.keyCode) ,//.ESCAPE
        
		isShield:false ,// CAPS_LOCK 解锁
		isDebug:false,
		view:null,
		
		onKey:function(event) {
			var _key=this.C ;
			var that = g$keyboard ;
			
			if (_key.CAPS_LOCK == event.keyCode) {
				this.isShield = ! this.isShield ;
			}
			
			/*
			if (event.ctrlKey) {
			    switch (event.keyCode) {
		        case _key.D:
		            break;

                case _key.L:
                    this.isShield = ! this.isShield ;
                	break ;
			    }
				
			}*/
			
			var isExe = false ;
			
			if (this.isShield) {
				return ;
			}
			
			function exeKeyFunction (funName, event) {
                if (that.view != null) {
                    if (that.view[funName]) {
                    	isExe = that.view[funName](event) ;
                        //isExe = true ;
                    }
                }
                
                if (!isExe && that[funName]) {
                	isExe = that[funName](event) ;
                    //isExe = true ;
                }
			}
			
			if (event.keyCode >= _key.F1 && event.keyCode <= _key.F12) {// function key process
				
				var func = event.altKey?"ALT_":"" ;
                if (event.ctrlKey) {                            
                    func = "CTRL_" ;
                }
                if (event.shiftKey) {                            
                    func = "SHIFT_" ;
                }
                func = func + 'F' + (event.keyCode - _key.F1 + 1) ;

                exeKeyFunction(func);
			} else if (event.keyCode == _key.BACKSPACE 
					|| event.keyCode == _key.LEFT 
					|| event.keyCode == _key.RIGHT) {
				var e = event.srcElement || event.target;
				var n = e.nodeName;
				var doFunc = true ;
				if (n == 'INPUT' || n == 'TEXTAREA' || $(e).attr("contenteditable") == "true") {
					if (!e.readOnly && !e.disabled ) {
						doFunc = false ;
					}
			    }
				
				if (doFunc) {
					if (event.keyCode == _key.BACKSPACE) {						
						exeKeyFunction('BACKSPACE',event);
					} else if (event.keyCode == _key.LEFT) {
						exeKeyFunction('LEFT',event);
					} else if (event.keyCode == _key.RIGHT) {
						exeKeyFunction('RIGHT',event);
					}
				}
			}
			/*
			for(_k in _key) {
				if (this.isShield) {
					break ;
				}
				if ( (_k.length == 2 || _k.length == 3) && _k.charAt(0)=='F') {// function key process
					//alert (_k.keyCode + "-" + event.keyCode);
					if (_key[_k] != event.keyCode) {
						continue ;
					}
					
					var func = event.altKey?"ALT_":"" ;
                    if (event.ctrlKey) {                            
                        func = "CTRL_" ;
                    }
                    if (event.shiftKey) {                            
                        func = "SHIFT_" ;
                    }
                    func = func + _k ;

                    if (this.view != null) {
                        if (this.view[func]) {
                        	this.view[func]() ;
                            isExe = true ;
                        }
                    }
                    
                    if (!isExe && this[func]) {
                    	this[func]() ;
                        isExe = true ;
                    }
				}
			}*/
			
			if (isExe) {
                event.keyCode=0;   
                event.returnValue=false;    
			}
		},
        
        F1:function() {
        	if (g$keyboard.isDebug) {        		
        		alert("F1") ;
        	}
        	return false ;
        },
        
        F2:function() {
        	if (g$keyboard.isDebug) { 
                alert("F2") ;
        	}
        	return false ;
        },
        
        F3:function() {
        	if (g$keyboard.isDebug) { 
                alert("F3") ;
        	}
        	return false ;
        },
        
        F4:function() {
        	if (g$keyboard.isDebug) { 
                alert("F4") ;
        	}
        	return false ;
        },
        
        F5:function() {
        	if (g$keyboard.isDebug) { 
                alert("F5") ;
        	}
        	return false ;
        },
        
        F6:function() {
        	if (g$keyboard.isDebug) { 
                alert("F6") ;
        	}
        	return false ;
        },
        
        F7:function() {
        	if (g$keyboard.isDebug) { 
                alert("F7") ;
        	}
        	return false ;
        },
        
        F8:function() {
        	if (g$keyboard.isDebug) { 
                alert("F8") ;
        	}
        	return false ;
        },
        
        F9:function() {
        	if (g$keyboard.isDebug) { 
                alert("F9") ;
        	}
        	return false ;
        },
        
        F10:function() {
        	if (g$keyboard.isDebug) { 
                alert("F10") ;
        	}
        	return false ;
        },
        
        F11:function() {
        	if (g$keyboard.isDebug) { 
                alert("F11") ;
        	}
        	return false ;
        },
        
        F12:function() {
        	if (g$keyboard.isDebug) { 
                alert("F12") ;
        	}
        	return false ;
        },
        
        BACKSPACE:function(event) {
        	if (g$keyboard.isDebug) { 
                alert("BACKSPACE") ;
        	}
        	if ($.browser.mozilla) {
        		event.preventDefault();
        	}
        	return true ;
        },

        LEFT:function(event) {return false;},
        RIGHT:function(event) {return false;},
        
        isNumeric:function(keyCode) {
            return ((keyCode >= 48 && keyCode <= 57) || keyCode == 8 || keyCode == 189 || keyCode == 32 || (keyCode >= 96 && keyCode <= 105)) ;
        }, 

        isAlpha:function(keyCode) {
            return ((keyCode >= 65 && keyCode <= 90) || keyCode == 8 || keyCode == 32 || keyCode == 190) ;
        }
	}
	

	
    document.onkeydown=function (e) {
		var keyEvent = e ;
		if ($.msie) {
			keyEvent = window.event ;
		} else if (typeof(event) != "undefined") {
			keyEvent = event ;
		}
		g$keyboard.onKey(keyEvent);
		
	}  ;
});