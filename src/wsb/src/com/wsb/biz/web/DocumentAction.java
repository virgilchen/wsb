package com.wsb.biz.web;

import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.entity.EventLog;
import com.globalwave.common.service.EventLogBO;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.Document;
import com.wsb.biz.entity.DocumentSO;
import com.wsb.biz.service.DocumentBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_documentAction")
@Scope("prototype")
public class DocumentAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private DocumentBO documentBO ;
    private Document document ;
    private DocumentSO documentSO ; 
    
    public String uploadForm() throws Exception{
    	return "jsp";
    }
    
    public String execute() throws Exception {        
        return this.list();        
    }

    @Pid(value=Pid.LOGINED,log=false)
    public String list() throws Exception {  

        ArrayPageList<Document> pageList = documentBO.query(documentSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=1071,log=false)
    public String get() throws Exception {  

    	Document org = documentBO.get(this.id) ;

    	renderObject(org, null) ; 
        return null ;  
    }

    @Pid(value=Pid.LOGINED)
    public String create()  throws Exception {        
    	try {
	        Object newDocument = documentBO.create(document) ;
			ResponseMessage msg = new ResponseMessage(newDocument, ResponseMessage.KEY_CREATE_OK) ;
			renderHtml("<script>parent.uploader.uploadOnReturn(" + msg.asJson() + ");</script>") ;
    	} catch (Exception e) {
    		log.error(document.getDocument_name() + " save error:", e);
    		String msg = e.getMessage() ;
    		if (StringUtils.isEmpty(msg)) {
    			msg = "文件上传失败，请与管理员联系！" ;
    		} else {
    			msg = msg.replaceAll("'", "\\\\'") ;
    		}
			renderHtml("<script>parent.uploader.uploadOnReturn({code:1000,message:'" + msg + "'});</script>") ;
    	}
    	
        return null;    
    }
    
    @Pid(value=Pid.LOGINED)
    public String createByAjax()  throws Exception {        
    	try {
	        Object newDocument = documentBO.create(document) ;
			ResponseMessage msg = new ResponseMessage(newDocument, ResponseMessage.KEY_CREATE_OK) ;
			renderJson(msg.asJson()) ;
    	} catch (Exception e) {
    		log.error(document.getDocument_name() + " save error:", e);
    		String msg = e.getMessage() ;
    		if (StringUtils.isEmpty(msg)) {
    			msg = "文件上传失败，请与管理员联系！" ;
    		} else {
    			msg = msg.replaceAll("'", "\\\\'") ;
    		}
			renderJson("{\"code\":1000, \"message\":\"" + msg + "\"}") ;
    	}
    	
        return null;    
    }

    
    //@Pid(value=1071)
    @Pid(value=Pid.LOGINED, log=false)
    public String download() throws Exception {  
    	
    	try {
	    	Document doc = documentBO.getDownloadDocument(this.id) ;
	

	    	EventLog eventLog = new EventLog() ;
	    	eventLog.setDesc_("File Download") ;
	    	eventLog.setExt_c1(String.valueOf(this.id)) ;
	    	eventLog.setExt_c2(doc.getDocument_name()) ;
	    	eventLog.setExt_c3(doc.getDocument_type()) ;
	    	eventLog.setEvent_type_code("ACTION") ;
	    	getEventLogBO().create(eventLog, doc.getOrder_id()) ;
	    	
	    	String fileName = doc.getFolder_name() + doc.getFile_uuid() ;
	        java.io.File f = new java.io.File(fileName) ;
	        
	        int length;
	        HttpServletResponse resp = this.getResponse() ;
	        ServletOutputStream op = resp.getOutputStream();
	        ServletContext context  = this.getServletContext();
	        String mimetype = context.getMimeType(fileName);
	
	        //
	        //  Set the response and go!
	        // 
	        //
	        resp.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
	        resp.setContentLength( (int)f.length() );
	        resp.setHeader( "Content-Disposition", "attachment; filename=\""+java.net.URLEncoder.encode(doc.getDocument_name(), "UTF-8")+"\"" );
	
	        //
	        //  Stream to the requester.
	        //
	        byte[] bbuf = new byte[1024];
	        DataInputStream in = new DataInputStream(new FileInputStream(f));
	
	        while ((in != null) && ((length = in.read(bbuf)) != -1))
	        {
	            op.write(bbuf,0,length);
	        }
	
	        in.close();
	        op.flush();
	        op.close();

    	} catch (Exception e) {
    		String msg = e.getMessage() ;
    		if (StringUtils.isEmpty(msg)) {
    			msg = "文件下载失败，请与管理员联系！" ;
    		} else {
    			msg = msg.replaceAll("'", "\\\\'") ;
    		}
			renderHtml("<script>parent.uploader.uploadOnReturn({code:1000,message:'" + msg + "'});</script>") ;
    	}
    	
    	return null ;
    }
    
    @Pid(value=1071)
    public String update()  throws Exception {
    	
        documentBO.update(document) ;
        
        renderObject(document, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    //@Pid(value=134)
    @Pid(value=-1)
    public String delete()  throws Exception {

        documentBO.delete(document) ;
        
        renderObject(document, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }
   
    public void setDocumentBO(DocumentBO documentBO) {
		this.documentBO = documentBO;
	}

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public DocumentSO getDocumentSO() {
        return documentSO;
    }

    public void setDocumentSO(DocumentSO documentSO) {
        this.documentSO = documentSO;
    }

    public EventLogBO getEventLogBO() {
        return (EventLogBO) CodeHelper.getAppContext().getBean("eventLogBO");
    }
}
