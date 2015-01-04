<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
<!--
var documentUploader = {
    documentType:'O',
    
    doUpload:function(){
    	var _this = this ;
        load("uploadFormWinDiv", root + "/biz/document/document_uploadForm.action", null, function(responseText, textStatus, XMLHttpRequest) {
            uploader.url = "/biz/document" ;
            uploader.getHtml=function(data) {
                return parse(E$("templateListDocumentDisplay").val(), data);
            };
            
            uploader.show(V('order.id'), _this.documentType, 'listDocumentDisplay', true, true); 
        });
    },

    doDelete:function (id, linkObj){
    	if (typeof(uploader) != "undefined") {
    		uploader.doDelete(id, linkObj);
    	} else {
            load("uploadFormWinDiv", root + "/biz/document/document_uploadForm.action", null, function(responseText, textStatus, XMLHttpRequest) {
                uploader.url = "/biz/document" ;
                uploader.getHtml=function(data) {
                    return parse(E$("templateListDocumentDisplay").val(), data);
                };
                
                uploader.doDelete(id, linkObj);
            });
    		
    	}
    },
    
    doShow:function(datas){
    	//uploader.renderList('photoDisplayDiv', datas, 'P');
    	var document_type = this.documentType;
        var div$ = E$('listDocumentDisplay') ;
        div$.html("") ;
        
        $(datas).each(function (i, data) {
            if (document_type != data.document_type) {
                return ;
            }
            div$.append(parse(E$("templateListDocumentDisplay").val(), data)) ;
            hasFootPic = true;
        }) ;
    },
    
    help:function() {
    	load("helpDiv1", root + "/biz/message_help.action?id=1", {}, function (responseText, textStatus, XMLHttpRequest) {
    	    helpWin1.show() ;
    	})  ;
    }
}
//-->
</script>



<table id="photoDisplayTD">
  <thead>
    <tr>
      <th>上传时间 </th>
      <th>操作人</th>
      <th>文件名</th>
      <th>备注</th>
      <th>操作</th>
    </tr>
  </thead>
  
  <tbody id="listDocumentDisplay"></tbody>
  
  <tbody style="display:none;visibility: false;" disabled="true">
    <tr>
      <td>
        <textarea id="templateListDocumentDisplay" jTemplate="yes">
          <tr>
            <td>{$T.created_on}</td>
            <td>{$T.created_by}</td>
            <td>{$T.document_name}</td>
            <td>{fmt.maxlen($T.desc_,10000)}</td>
            <td>
              <a href="<%=request.getContextPath() %>/upload/{$T.folder_name}/{$T.file_uuid}" target="_blank" style="cursor:pointer;">查看 </a>
              &nbsp;
              <a href="<%=request.getContextPath() %>/biz/document_download.action?id={$T.id}" target="_blank" style="cursor:pointer;">下载</a>
              &nbsp;
              <a onclick="documentUploader.doDelete({$T.id}, $(this).parent());" style="cursor:pointer;color:blue;">删除</a>
            </td>
          </tr>                              
        </textarea>
      </td>
    </tr>
  </tbody>
</table>