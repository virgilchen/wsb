
var uploader = $.extend(uploader_parent, {
	
	documentsDiv:null ,
	files:null,
	channel:"html",// swf/drag/html 
	url:"/biz/document",
	form:null,
	$form:null,
	
	show:function (biz_id, document_type, documentsDivId, isSingleFile, isRemarkEnable) {
	    //E$("document.document_file").attr("required", true);
	    //E$("document.document_name").attr("required", true);
		var _this = this ;
		
		this.$form = $("#uploadEditForm") ;
		this.form = this.$form[0] ;
		this.form.reset();
		this.form.action= root + this.url + "_create.action" ;
		this.$form.validator();
		
	    $("#document\\.biz_id", this.$form).val(biz_id) ;

	    $("#document\\.document_type", this.$form).val(document_type) ;
	    
	    if (typeof(isRemarkEnable) == "undefined" || !isRemarkEnable) {
	    	$("#remarkTD", this.$form).hide();
	    }
	    else {
	    	$("#remarkTD", this.$form).show();
	    }

	    if (typeof(isSingleFile) != "undefined" && isSingleFile) {
	        this.changeHtmlChannel();
	    }
	    
	    this.documentsDiv = E(documentsDivId) ;
	    
	    $("#uploadFormDiv").dialog({
	        title: "文件上传",
	        autoOpen: true,
	        width: 650,
	        height: 450,
	        modal: true,
	        resizable: true,
	        autoResize: true,
	        overlay: {
	            opacity: 0.5,
	            background: "black"
	        },
	        resizable:true,
	        beforeClose: function( event, ui ) {
	        	_this.form.resetForm() ;
	            //E$("document.document_file").attr("required", false);
	            //E$("document.document_name").attr("required", false);
	        }
	    });
	
	    swfUploadHandler.init();
	},
	
	renderList:function(divId, datas, document_type) {
		var div$ = E$(divId) ;
		div$.html("") ;
		
		$(datas).each(function (i, data) {
			if (document_type != data.document_type) {
				return ;
			}
	        div$.append(uploader.getHtml(data)) ;
		}) ;
	},
	
	setDocumentName:function (fileInput) {
		var fileName = $(fileInput).val();
		
		var i = fileName.lastIndexOf("/") ;
	
		if (i < 0) {
			i = fileName.lastIndexOf("\\") ;
		}
	
		if (i > 0) {		
		    $('#document\\.document_name', this.$form).val(fileName.substring(i + 1)) ;
		} else {
	        $('#document\\.document_name', this.$form).val(fileName) ;
		}
		
	}, 
	
	download:function(id) {
		if ($("#save_iframe", this.$form).length == 0) {
			this.$form.append("<iframe id='save_iframe' style='display:none;'></iframe>");
		}

		$("#save_iframe", this.$form).src = root + this.url + "_download.action?id="  + id;
	},
	
	save:function() {
	
		if (uploader.channel == "swf") {
		    swfUploadHandler.save();
		    return ;
		} else if (uploader.channel == "drag") {
			dragDropHandler.save();
		    return ;
		}
		
		if(!this.form.checkValidity()) {
	        alert("请正确填写表单！") ;
	        this.form.setErrorFocus() ;
	        return ;
	    }
	    
	    if ($("#document\\.document_file", this.$form).val() == "") {
	        alert("请选择要上传的文件！") ;
	    	return ;
	    }
	    /*
	    if (E$("document.company_id").val() == "") {
	        alert("附件未能关联到公司，请检查订单已否已经填写公司信息！") ;
	    	return ;
	    }*/
	    /*
	    if (!window.confirm("是否确定要保存？")) {
	        return ;
	    }*/
	
	    var isAdd = $("#document\\.id", this.$form).val()=="" ;
	    
		//var _url = isAdd?"<%=root %>/biz/document_create.action":"";
	
		if (isAdd) {
			showLoading();
			this.form.submit() ;
		} else {
		    ajax(
		        root + this.url + "_update.action" , 
		        this.$form.serialize(),
		        function(data, textStatus){
		            //alert(data.message) ;
		            if (data.code == "0") {
		                list() ;
		            }
		        }
		    );	
		}
	},
	
	uploadOnReturn:function(msg) { 
		// iframe.contentWindow.document - for IE<7
		//var iframe = $("#save_iframe")[0] ;
	    //var msg = iframe.contentDocument ? iframe.msg: iframe.contentWindow.msg;
		
		if (msg) {
			hideLoading();
			if (msg.data) {
				$(this.documentsDiv).append(this.getHtml(msg.data)) ;
				
				$( "#uploadFormDiv" ).dialog( "close" );
			}
		    alert(msg.message) ;
		}
	},
	
	doDelete:function (id, linkObj) {
		
		if (!window.confirm("是否确定要删除？")) {
			return ;
		}
	
	    ajax(
	    	root + this.url + "_delete.action", 
	    	"document.id="+id,//$("#documentTB input:checked")[0].value,
	        function(data, textStatus){
	            //alert(data.message) ;
	            if (data.code == "0") {
	                //list() ;
	            	$(linkObj).parent().remove() ;
	            }
	        }
	    );
	},
	
	downloadList:function(document_type) {
	    ajax(
	        root + "/system/eventLog_list.action", 
	        {"eventLogSO.biz_id":V("order.id"),
	            "eventLogSO.ext_c3":document_type,
	            "eventLogSO.desc_":"File Download",
	            "eventLogSO.pageIndex":-2},
	        function(data, textStatus){
	             
	             $("#downloadListDiv").html(parse($("#downloadListTemplate").val(), data)) ;
	             
	             $("#downloadListDiv").dialog({
	                 title: "下载明细",
	                 autoOpen: true,
	                 width: 550,
	                 height: 800,
	                 modal: true,
	                 resizable: true,
	                 autoResize: true,
	                 overlay: {
	                     opacity: 0.5,
	                     background: "black"
	                 },
	                // position: {my: "left+15 bottom"},
	                 resizable:true}) ;
	        }
	    );
	},
	
	getFormData:function (isFormData) {
		
		if (typeof(isFormData) == "undefined") {
			isFormData = false ;
		}
	
		var ret = isFormData?new FormData():{} ;
	
		$("input[type!=file]", this.$form).each(function(i, e) {
			if (isFormData) {
				ret.append(e.name, e.value);
			} else {
		        ret[e.name]=e.value ;	
			}
		}) ;
		
		return ret ;
	},
	
	changeChannel:function (_btn) {
		if (this.channel == "html") {
	        $("#sFileDiv1, #sFileDiv2").hide();
	        $("#mFileDiv1, #mFileDiv2").show();
	        //$("#document.document_name").removeAttr("required");
	        $(_btn).attr("title", "切换至单文件模式") ;
	        if(dragDropHandler.files != null && dragDropHandler.files.length > 0) {
	            this.channel = "drag" ;
	        } else {
	        	this.channel = "swf" ;
	        }
		} else {
	        $("#sFileDiv1, #sFileDiv2").show();
	        $("#mFileDiv1, #mFileDiv2").hide();
	        //$("#document.document_name").attr("required", true);
	        $(_btn).attr("title", "切换至多文件模式") ;
			this.channel = "html" ;
		}
		//$("#uploadEditForm")[0].checkValidity();
	},
	
	changeHtmlChannel:function (_btn) {
        $("#sFileDiv1, #sFileDiv2").show();
        $("#mFileDiv1, #mFileDiv2").hide();
        //$("#document.document_name").attr("required", true);
        if (typeof(_btn) == "undefined") {        	
        	$(_btn).attr("title", "切换至多文件模式") ;
        }
		this.channel = "html" ;
	},
	
	rowCount:0,
	
	createStatusbar:function()
	{
	     this.rowCount++;
	     
	     var row="odd";
	     if(this.rowCount%2 == 0){
	         row ="even"; 
	     }
	     
	     this.statusbar = $("<div class='statusbar "+row+"'></div>");
	     this.filename = $("<div class='filename'></div>").appendTo(this.statusbar);
	     this.size = $("<div class='filesize'></div>").appendTo(this.statusbar);
	     this.progressBar = $("<div class='progressBar'><div></div></div>").appendTo(this.statusbar);
	     this.abort = $("<div class='abort'>Abort</div>").appendTo(this.statusbar);
	     this.cancel = $("<div>Canceled</div>").appendTo(this.statusbar).hide();
	     
	     $("#status1").append(this.statusbar);
	 
	    this.setFileNameSize = function(name,size)
	    {
	        var sizeStr="";
	        var sizeKB = size/1024;
	        if(parseInt(sizeKB) > 1024)
	        {
	            var sizeMB = sizeKB/1024;
	            sizeStr = sizeMB.toFixed(2)+" MB";
	        }
	        else
	        {
	            sizeStr = sizeKB.toFixed(2)+" KB";
	        }
	 
	        this.filename.html(name);
	        this.size.html(sizeStr);
	    }
	    this.setProgress = function(progress)
	    {       
	        var progressBarWidth =progress*this.progressBar.width()/ 100;  
	        this.progressBar.find('div').animate({ width: progressBarWidth }, 10).html(progress + "% ");
	        if(parseInt(progress) >= 100)
	        {
	            this.abort.hide();
	        }
	    }
	    this.setAbort = function(jqxhr)
	    {
	        var sb = this.statusbar;
	        this.abort.click(function()
	        {
	            jqxhr.abort();
	            sb.hide();
	        });
	    }
	    
	
	    this.setCancel = function(_swf)
	    {
	        var sb = this.statusbar;
	        this.abort.click(function()
	        {
	            _swf.cancel(sb) ;
	        });
	    }
	}

});// end uploader

var dragDropHandler = {
		
	_instance:null,

	init:function() {
	    
		this._instance = $("#dragAndDropHandler");
	    
	    var isShow = false ;
	    try{
	        isShow = (new FormData() != null) ;
	    } catch(e) {
	        //return false ;
	    }

	    if (isShow) {
	    	this._instance.show();
	        this.initEvent();
	    } else {
	    	this._instance.hide();    
	    }
	    
	    $("#status1").html("");
	    this.files = null;
	},


	initEvent:function() {
		var _this = this ;
	    var obj = this._instance ;
		if (this.isEventInited) {
			this.isEventInited = true ;
		}
		
		obj.on('dragenter', function (e) 
	    {
	        e.stopPropagation();
	        e.preventDefault();
	        $(this).css('border', '2px solid #0B85A1');
	    });
		
		obj.on('dragover', function (e) 
	    {
	         e.stopPropagation();
	         e.preventDefault();
	    });
		
		obj.on('drop', function (e) 
	    {
	         $(this).css('border', '2px dotted #0B85A1');
	         e.preventDefault();
	         _this.files = e.originalEvent.dataTransfer.files;
	     
	         //We need to send dropped files to Server
	         _this.handleFileUpload(_this.files, obj);
	    });
		
	    $(document).on('dragenter', function (e) 
	    {
	        e.stopPropagation();
	        e.preventDefault();
	    });
	    
	    $(document).on('dragover', function (e) 
	    {
	      e.stopPropagation();
	      e.preventDefault();
	      obj.css('border', '2px dotted #0B85A1');
	    });
	    
	    $(document).on('drop', function (e) 
	    {
	        e.stopPropagation();
	        e.preventDefault();
	    });
	    
	},
	

	handleFileUpload:function (files,obj)
	{
	    $("#status1").html("");

        uploader.channel = "drag" ;
        
	    for (var i = 0; i < files.length; i++) 
	    {
	    	var f = files[i] ;
	        var fd = uploader.getFormData(true);//new FormData();
	        fd.append('document.document_file', f);
	        fd.append("document.document_name", f.name);

	        var status = new uploader.createStatusbar(obj); //Using this we can set progress.
	        status.setFileNameSize(f.name, f.size);
	        f.__status = status;
	        f.__formData = fd;
	    }
	},
	
	sendFileToServer:function(formData,status)
	{
		var _this = this ;
		
	    var uploadURL = root + uploader.url + "_createByAjax.action"; //Upload URL
	    var extraData ={}; //Extra Data.
	    var jqXHR=$.ajax({
	            xhr: function() {
	            var xhrobj = $.ajaxSettings.xhr();
	            if (xhrobj.upload) {
                    xhrobj.upload.addEventListener('progress', function(event) {
                        var percent = 0;
                        var position = event.loaded || event.position;
                        var total = event.total;
                        if (event.lengthComputable) {
                            percent = Math.ceil(position / total * 100);
                        }
                        //Set progress
                        status.setProgress(percent);
                    }, false);
                }
	            return xhrobj;
	        },
	    url: uploadURL,
	    type: "POST",
	    contentType:false,
	    processData: false,
	        cache: false,
	        data: formData,
	        beforeSend: function(XMLHttpRequest){
	            showLoading();
	        },
	        success: function(serverData){

	            if (serverData.data) {
	                status.setProgress(100);
	                status.uploaded = true ;
	                $(uploader.documentsDiv).append(uploader.getHtml(serverData.data)) ;
	            }
                if (serverData.code != 0) {
                    alert(serverData.message) ;
                }
	            //$("#status1").append("File upload Done<br>");         
	        },
	        complete: function(XMLHttpRequest, textStatus){
	            hideLoading();

	            for (var i = 0; i < _this.files.length; i++) 
	            {
	            	if (!_this.files[i].__status.uploaded){
	                    return ;	
	            	}
	            }

                $("#uploadFormDiv").dialog( "close" );
	        }
	    }); 
	 
	    status.setAbort(jqXHR);
	},
	
	save:function () {

		if (this.files == null || this.files.length <= 0) {
			alert("请选择要上传的文件！");
			return ;
		}
        for (var i = 0; i < this.files.length; i++) 
        {
        	var f = this.files[i] ;
            this.sendFileToServer(f.__formData, f.__status);
        }
	}
};


 


var swfUploadHandler = {
	swfu:null,
	files:null,
	formData:null,
	
	init:function() {

		this.formData = uploader.getFormData(false);

	    // --- swf upload
	    if (this.swfu == null) {
	        this.swfu = new SWFUpload({ 
	            //提交路径  
	            upload_url: root +  uploader.url + "_createByAjax.action",  
	            //向后台传递额外的参数  
	            post_params: $("#uploadEditForm").serialize(),  
	            //上传文件的名称  
	            file_post_name: "document.document_file",  
	              
	            // 下面自己按照字面意思理解  
	            file_size_limit : "10400", // 10MB  
	            file_types : "*.*",  
	            file_types_description : "All Files",  
	            file_upload_limit : "20",  
	            file_queue_limit : "0",  
	    
	            // The event handler functions are defined in handlers.js
	            file_queued_handler : swfUploadHandler.fileQueued,
	            file_queue_error_handler : fileQueueError,
                file_dialog_start_handler: swfUploadHandler.fileDialogStart,
	            file_dialog_complete_handler : swfUploadHandler.fileDialogComplete,
	            upload_start_handler : swfUploadHandler.uploadStart,
	            upload_progress_handler : swfUploadHandler.uploadProgress,
	            upload_error_handler : uploadError,
	            upload_success_handler : swfUploadHandler.uploadSuccess,
	            upload_complete_handler : swfUploadHandler.uploadComplete,
	            queue_complete_handler : queueComplete,
	    
	    
	            // 按钮的处理  
	            button_image_url : root + "/plugin/SWFUpload/FullyTransparent_65x29.png",  
	            button_placeholder_id : "spanButtonPlaceholder1",  
	            button_text : "<button type=\"button\">选择多个文件</button>",  
	            button_width: 100,  
	            button_height: 30,  
	            //button_text_style : ".redText { color: #FF0000; }",  
	            button_text_left_padding : 3,  
	            button_text_top_padding : 2,  
	            button_action : SWFUpload.BUTTON_ACTION.SELECT_FILES,  
	            button_disabled : false,  
	            button_cursor : SWFUpload.CURSOR.HAND,  
	            button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
	              
	            // Flash Settings  
	            flash_url : root + "/plugin/SWFUpload/Flash/swfupload.swf",  

	            custom_settings : {  
	                //progressTarget : "fsUploadProgress1",  
	                cancelButtonId : "btnCancel1"  
	            },  
	              
	            // Debug Settings  
	            debug: false  
	        });  
	    }
        
        dragDropHandler.init();
	},
	
	fileQueued:function(file) {
	    try {
	        // You might include code here that prevents the form from being submitted while the upload is in
	        // progress.  Then you'll want to put code in the Queue Complete handler to "unblock" the form
	        var status = new uploader.createStatusbar(); //Using this we can set progress.
	        status.setFileNameSize(file.name, file.size);
	        status.setCancel(swfUploadHandler);
	        //file.__status = status;
	        swfUploadHandler.files[file.id] = {status:status};
	        uploader.channel = "swf" ;
            //files[i].__formData = fd;
	    } catch (ex) {
	        this.debug(ex);
	    }

	},

	fileDialogComplete:function(numFilesSelected, numFilesQueued) {
	    try {
	        if (this.getStats().files_queued > 0) {
	            //document.getElementById(this.customSettings.cancelButtonId).disabled = false;
	        }
	        
	        /* I want auto start and I can do that here */
	        //this.startUpload();
	    } catch (ex)  {
	        this.debug(ex);
	    }
	},
	
	fileDialogStart:function() {
	    /* I don't need to do anything here */
		swfUploadHandler.cancelQueue();
        $("#status1").html("");
        swfUploadHandler.files = [];
	},
	
	uploadStart:function (file) {
	    try {
	    	swfUploadHandler.formData["document.document_name"] = file.name ;
	    	swfUploadHandler.swfu.setPostParams(swfUploadHandler.formData) ;
	    }
	    catch (ex) {
	    }
	    
	    return true;
	},
	
	uploadProgress:function(file, bytesLoaded, bytesTotal) {

	    try {
	        var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
	        swfUploadHandler.files[file.id].status.setProgress(percent);
	        //var progress = new FileProgress(file, this.customSettings.progressTarget);
	        //progress.setProgress(percent);
	        //progress.setStatus("Uploading...");
	    } catch (ex) {
	        this.debug(ex);
	    }
	},

	uploadSuccess:function(file, serverData) {
	    try {
	        //var progress = new FileProgress(file, this.customSettings.progressTarget);
	        //progress.setComplete();
	        //progress.setStatus("Complete.");
	        //progress.toggleCancel(false);
	    	swfUploadHandler.files[file.id].status.setProgress(100);
	    	eval("serverData = " + serverData) ;
	        if (serverData.data) {
	            $(uploader.documentsDiv).append(uploader.getHtml(serverData.data)) ;
	        }
	        if (serverData.code != 0) {
                alert(serverData.message) ;
            }
	    } catch (ex) {alert(ex);
	        this.debug(ex);
	    }
	},
	
	uploadComplete:function(file) {
	    try {
	        /*  I want the next upload to continue automatically so I'll call startUpload here */
	        if (this.getStats().files_queued === 0) {
	            //document.getElementById(this.customSettings.cancelButtonId).disabled = true;
	            hideLoading();
                $("#uploadFormDiv").dialog( "close" );
                $("#status1").html("");
	        } else {    
	            this.startUpload();
	        }
	    } catch (ex) {
	        this.debug(ex);
	    }

	},
	
	cancelQueue:function() {
	    //document.getElementById(this.swfu.customSettings.cancelButtonId).disabled = true;
	    this.swfu.stopUpload();

        for (var fileName in this.swfu.files) {
        	var _status = this.swfu.files[fileName].status;
        	_status.cancel.show() ;
        	_status.abort.hide();
        }
        
	    var stats;
	    
	    do {
	        stats = this.swfu.getStats();
	        this.swfu.cancelUpload();
	    } while (stats.files_queued !== 0);
	},
	
	cancel:function (statusBar) {
		this.cancelQueue();
	},
	
    save:function () {
        if (this.swfu.getStats().files_queued == 0) {
            alert("请选择要上传的文件！");
            return ;
        }
        showLoading();
    	this.swfu.startUpload();
    }
}