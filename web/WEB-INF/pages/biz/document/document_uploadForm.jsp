<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String root = request.getContextPath();
%>

<style>
#dragAndDropHandler
{
border:2px dotted #0B85A1;
width:100%;
color:#92AAB0;
text-align:left;vertical-align:middle;
padding:10px 10px 10 10px;
margin-bottom:10px;
font-size:200%;
}
.progressBar {
    width: 50px;
    height: 22px;
    border: 1px solid #ddd;
    border-radius: 5px; 
    overflow: hidden;
    display:inline-block;
    margin:0px 10px 5px 5px;
    vertical-align:top;
}
 
.progressBar div {
    height: 100%;
    color: #fff;
    text-align: right;
    line-height: 22px; /* same as #progressBar height if we want text middle aligned */
    width: 0;
    background-color: #0ba1b5; border-radius: 3px; 
}
.statusbar
{
    border-top:1px solid #A9CCD1;
    min-height:25px;
    width:450px;
    padding:10px 10px 0px 10px;
    vertical-align:top;
}
.statusbar:nth-child(odd){
    background:#EBEFF0;
}
.filename
{
display:inline-block;
vertical-align:top;
width:200px;
}
.filesize
{
display:inline-block;
vertical-align:top;
color:#30693D;
width:100px;
margin-left:10px;
margin-right:5px;
}
.abort{
    background-color:#A8352F;
    -moz-border-radius:4px;
    -webkit-border-radius:4px;
    border-radius:4px;display:inline-block;
    color:#fff;
    font-family:arial;font-size:13px;font-weight:normal;
    padding:4px 15px;
    cursor:pointer;
    vertical-align:top;
    display: none;
    }
</style>



<script type="text/javascript" src="<%=root %>/plugin/jTemplates/jquery-jtemplates.js"></script>
<script type="text/javascript" src="<%=root %>/plugin/SWFUpload/swfupload.js"></script>
<script type="text/javascript" src="<%=root %>/plugin/SWFUpload/handlers.js"></script>

<script type="text/javascript">

var uploader_parent = {
	getHtml:function(data) {
	    return '<div>' 
	            + '  <img src="<%=root %>/photo/' + data.folder_name + '/' + data.file_uuid + '"/>'
			    + '  <a onclick="uploader.download('+data.id+');" style="cursor:pointer;">'+data.document_name+'</a>'
			    + '&nbsp;&nbsp;<a onclick="uploader.doDelete('+data.id+', this);" style="cursor:pointer;color:blue;">删除</a>'
			    + '</div>' ;
	}
};// end uploader

</script>


<script type="text/javascript" src="<%=root%>/script/biz/document/document_uploadForm.js"></script>

<div id="uploadFormDiv" style="display:none;">


<table width="100%" align="center"> 
  <tr>
    <td id="uploadFormTd">            
      <form  method="post" id="uploadEditForm" name="uploadEditForm" onsubmit="return false;" style="margin: 0" enctype="multipart/form-data" target="save_iframe">
	      <input type="hidden" name="document.id" id="document.id"/>
	      <input type="hidden" name="document.version_id"/>
	      <input type="hidden" name="document.document_type" id="document.document_type"/>
	  
	      <table cellspacing="0" cellpadding="0" width="98%">
	        <tr style="display:none;">
	          <td>biz_id:</td>
	          <td><input type="text" name="document.biz_id" id="document.biz_id"/></td>
	        </tr>
	        <tr>
	          <td class="item_label" style="width: 80px;">浏览：<button id="changeChannelBtn" class="ui-icon ui-icon-transferthick-e-w"  style="display: none;margin: 2px;" onclick="uploader.changeChannel(this)" title="切换至单文件模式">+</button></td>
	          <td class="item_input">
	            <div style="display:none;" id="sFileDiv1">
	              <input type="file" name="document.document_file" id="document.document_file" onchange="uploader.setDocumentName(this)"  />
                </div>
                
                <div style="display: inline;" id="mFileDiv1">
                  <div style="width: 100px;">  
                      <span id="spanButtonPlaceholder1"></span>  
                      <input id="btnCancel1" type="button" value="Cancel Uploads" onclick="swfUploadHandler.cancelQueue();" disabled="disabled" style="margin-left: 2px; height: 22px; font-size: 8pt;display: none;" />  
                  </div>  
                  <div id="dragAndDropHandler" style="display: none;">可拖放文件到此区域<br/>Drag & Drop Files Here</div>
                </div>
	          </td>
	        </tr>
	        <tr>
	          <td class="item_label" style="padding: 5px">文件名：</td>
	          <td class="item_input">
                <div style="display:none;" id="sFileDiv2">
	              <input type="text" name="document.document_name" id="document.document_name"  required="required" />
                </div>
	          
                <div id="mFileDiv2">
                  <div id="status1"></div>
                  <!-- 
                  <div class="fieldset flash" id="fsUploadProgress1">  
                      <span class="legend">文件上传</span>  
                  </div>  
                   -->
                </div>
	          </td>
	        </tr>
	        
	        
	        
	        <tr style="display:none;">
	          <td>company_id:</td>
	          <td><input type="text" name="document.company_id" id="document.company_id" /></td>
	        </tr>
	        <tr id="remarkTD">
	          <td class="item_label" style="padding: 5px">备注：</td>
	          <td class="item_input"><input type="text" name="document.desc_"/></td>
	        </tr>
	        <tr style="display:none;">
	          <td>Order:</td>
	          <td><input type="text" name="document.order_" value="0"/></td>
	        </tr>
	      </table>
	      
      </form>
      
      

      
    </td>
  </tr>
  
  <tr>
    <td align="center" style="padding-top:10px;">
      <button onclick="uploader.save()" accesskey="S">保存(S)</button>
      <!-- 
      <input type="reset"/>
       -->
    </td>
  </tr>
</table>

<iframe name="save_iframe" id="save_iframe" style="display:none;"></iframe>

<textarea id="downloadListTemplate" rows="100" cols="100" style="display: none;">
<table >
  <thead>
    <tr>
      <th width="40%">文件名</th>
      <th width="30%">下载时间</th>
      <th width="25%">下载人</th>
    </tr>
  </thead>

  <tbody id="listBody" >
    {#foreach $T.list as record}
    <tr>
      <td>{$T.record.ext_c2}</td>
      <td>{$T.record.created_on}</td>
      <td>{$T.record.created_by}</td>
    </tr>
    {#/for}
  </tbody>
</table>
</textarea>

<!-- 下载记录明细 -->
<div id="downloadListDiv" style="display: none;" class="admin_system">

</div>


</div>