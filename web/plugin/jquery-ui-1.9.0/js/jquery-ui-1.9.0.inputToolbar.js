$(function() {
    // the widget definition, where "custom" is the namespace,
    // "colorize" the widget name
$.widget( "g.inputToolbar", {
    // default options
    options: {
        template:null,
        saveBtnId:"saveBtn",
        save:function (that) {alert("save");that.save();} ,
        cancelBtnId:"cancelBtn",
        position:{},
        cascadeElements:[]
    },

    // the constructor
    _create: function() {
    
    	this.$toolbar = this.options.template.clone() ;
    	this.$toolbar.attr("id", this.element.attr("id") + "_toolbar");
    	this.$toolbar.css("zIndex", 300) ;
    	//this.element.after(this.$toolbar) ;
    	$("body").append(this.$toolbar) ;
    	
    	var that = this ;
        this.element.focus(function () {
            that.showToolbar() ;
        });
        
        var cascadeElements = this.options.cascadeElements ;
        
        $(cascadeElements).each(function (i, elem) {
        	elem.focus(function () {
                that.showToolbar() ;
            });
        }) ;
        
        this.element.change(function () {
            that.showToolbar() ;
        });
        
        $(cascadeElements).each(function (i, elem) {
        	elem.change(function () {
                that.showToolbar() ;
            });
        }) ;
        
        this.element.blur(function () {
            //if (that.old_value == that.element.val()) {
            	that.hideToolbar() ;
            //}
        });
        
        $(cascadeElements).each(function (i, elem) {
        	elem.blur(function () {
                that.hideToolbar() ;
            });
        }) ;

        $("#" + this.options.saveBtnId, this.$toolbar).click(function () {
            that.options.save(that) ;
        }) ;
        
        $("#" + this.options.cancelBtnId, this.$toolbar).click(function () {
            that.cancel() ;
        }) ;
    },

    showToolbar:function () {
    	this.$toolbar.show();
    	
    	this.$toolbar.position($.extend({
	        my: "left+25 middle",
	        at: "left+25 middle",
	        of: this.element,
	        collision:"fit"
	    }, this.options.position));
	    
    	if (this.old_value == null) {    		
    	    this.old_value = this.element.val();
    	}
    	
    	$(this.options.cascadeElements).each(function (i, elem) {
        	if (elem.attr("old_value") == null) {
        		elem.attr("old_value", elem.val()) ;
        	}
        });
	},

  
    hideToolbar:function() {
    	if (this.old_value != null && this.old_value != this.element.val()) {
    		return ;
    	}

    	var cascadeElements = this.options.cascadeElements ;;
    	for (var i = 0 ; i < cascadeElements.length ; i ++) {
    		var elem = cascadeElements[i] ;
        	if (elem.attr("old_value") != null && elem.attr("old_value") != elem.val()) {
        		return ;
        	}
        }
    	
    	this.$toolbar.hide();
    },

	save:function() {
        this.old_value = null ;

        $(this.options.cascadeElements).each(function (i, elem) {
        	elem.attr("old_value", null) ;
        });

        this.hideToolbar() ;
    },

    cancel:function () {
        if (this.old_value != this.element.val()) {
        	if (!window.confirm("是否放弃对修改的内容进行保存？")) {
        		return ;
        	}
        }
        this.element.val(this.old_value) ;
        
        $(this.options.cascadeElements).each(function (i, elem) {
        	elem.val(elem.attr("old_value")) ;
        });
    	
        this.hideToolbar() ;
    },
  
    // events bound via _on are removed automatically
    // revert other modifications here
     _destroy: function() {
        // remove generated elements
    	this.$toolbar.remove() ;
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