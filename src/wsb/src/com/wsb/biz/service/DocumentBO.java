package com.wsb.biz.service;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.U;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.Document;
import com.wsb.biz.entity.DocumentSO;


@Service("documentBO")
@Scope("prototype")
@Transactional
public class DocumentBO extends BaseServiceImpl {

	private Log log = LogFactory.getLog(this.getClass()) ;
	
	public static String getUploadPath() {
		return CodeHelper.getWebConfig("realPath") + "/upload/" ;
	}
	
	public Document create(Document document) {  
		/*
		Long order_id = document.getOrder_id();
		if (order_id != null) {
			OrderBO orderBo = getOrderBO();
			Order order = orderBo.get(order_id);
			if (order != null && "T".equals(order.getStatus())) {
//				order.setStatus("N");
				order.setStatus("C");
				order.setOrder_type(new Long(1));
				if (order.getTime_place() == null) {
					order.setTime_place(new Timestamp(System.currentTimeMillis()));
				}
				orderBo.update(order, false);
			}
		}*/
		return createDoc(document);
	}
	
    private Document createDoc(Document document) {  
    	document.setFile_uuid(UUID.randomUUID().toString()) ;
    	document.setFolder_name(U.yyyyMMdd(U.currentDate()).substring(0, 6)) ;
    	
        Document newItem = (Document) jdbcDao.insert(document) ;
        
    	String destBase = getUploadPath() + document.getFolder_name() + "/" ;
    	
        File baseFile = new File(destBase);
        if (!baseFile.exists()) {
            //baseFile.mkdir();
        	try {
                FileUtils.forceMkdir(baseFile) ;
        	} catch (Exception e) {
        		log.error(e, e) ;
        		throw new BusinessException(e) ;
        	}
        }
        
    	String dest = destBase + document.getFile_uuid() ;
    	
    	if(!document.getDocument_file().renameTo(new File(dest))) {
    		log.error("file can't write to '" + dest + "'") ;
    		throw new BusinessException(20009L) ;//文件不能写入，请重新上传！
    	}
        
        return newItem;
    }
    
    public Document getDownloadDocument(Long id) {
    	Document document = this.get(id) ;
    	String destBase = getUploadPath() + document.getFolder_name() + "/" ;
    	document.setFolder_name(destBase) ;
    	return  document;
    }
    
    public void update(Document document) {

    	document.addExclusions("folder_name") ;
    	document.addExclusions("file_uuid") ;
    	
        jdbcDao.update(document) ;
    }

    public void delete(Document document) {

    	document = (Document)jdbcDao.get(document) ;
    	jdbcDao.delete(document) ;
    	
    	String destBase = getUploadPath() + document.getFolder_name() + "/" ;

    	File fileToDel = new File(destBase + document.getFile_uuid()) ; 
    	
    	if (fileToDel.exists()) {
    	    fileToDel.delete() ;     
    	}
    }

    
    public ArrayPageList<Document> query(DocumentSO documentSO) {

        if (documentSO == null) {
        	documentSO = new DocumentSO() ;
        }
        documentSO.addAsc("order_") ;
        documentSO.addAsc("company_id") ;
        documentSO.addAsc("document_type") ;
        documentSO.addAsc("document_name") ;
        
        return (ArrayPageList<Document>)jdbcDao.query(documentSO, Document.class);
    }

    
    public ArrayPageList<Document> query(Long order_id, String document_type) {

        DocumentSO documentSO = new DocumentSO() ;
        documentSO.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE);
        documentSO.setDocument_type(document_type);
        documentSO.setOrder_id(order_id);
        
        documentSO.addAsc("order_") ;
        documentSO.addAsc("company_id") ;
        documentSO.addAsc("document_type") ;
        documentSO.addAsc("document_name") ;
        
        return (ArrayPageList<Document>)jdbcDao.query(documentSO, Document.class);
    }

    public Document get(Long id) {  
    	Document org = new Document() ;
    	org.setId(id) ;
        org = (Document) jdbcDao.get(org) ;
        
        
        return org;
    }
    
    public static DocumentBO getDocumentBO() {
    	return (DocumentBO)CodeHelper.getAppContext().getBean("documentBO");
    }
    
}
