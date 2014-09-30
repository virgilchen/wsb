package com.globalwave.system.web;



import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.Convertor;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.entity.Dict;
import com.globalwave.system.entity.DictSO;
import com.globalwave.system.service.DictBO;
import com.globalwave.system.web.annotations.Pid;
import com.globalwave.util.GsonUtil;
import com.opensymphony.xwork2.Preparable;

@Service("system_dictAction")
@Scope("prototype")
public class DictAction extends BaseAction implements Preparable {
   
    private static final long serialVersionUID = 1L;

    private DictBO dictService = null ;

    private Dict dict ;
    private DictSO dictSO ;
    private Long[] dict_ids ;
    private Integer versionId = 0;
    private String term ;
    private Integer total ;
    private Map<String, Object> items ;
    
    public String execute() throws Exception {
        return this.list();    
        
    }
    
    public String edit() throws Exception {
    	return "jsp";
    }

    @Pid(value=2050, log=false)
    public String list() throws Exception {        
        
        ArrayPageList<Dict> pageList = dictService.query(dictSO) ;

        renderList(pageList) ;
        
        return null ;    
        
    }


    @Pid(value=2050, log=false)
    public String get() throws Exception {
    	
    	renderObject(this.dictService.get(this.id), null) ; 
    	
        return null ;  
    	
    }
    
    public String findOptions() throws Exception { 
    	
    	Long start = System.currentTimeMillis() ;
    	
    	Map<String,List<Map<String, Object>>> optionsTables = CodeHelper.findOptions(versionId) ;

    	log.error("find options time escape==>>" + (System.currentTimeMillis() - start)) ;
    	
    	if (isJsonRequest()||"json".equals(getRequest().getParameter("dataType"))) {
    		renderJson("var g$dict = " + GsonUtil.getGson().toJson(optionsTables) + ";") ;
    	} else {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("root") ;

        	for (String tableName:optionsTables.keySet()) {
        		List<Map<String, Object>> rows = optionsTables.get(tableName) ;
        		root.add(Convertor.list2Xml(rows, tableName)) ;
        	}
            log.debug("\n\n" + root.asXML() + "\n\n") ;
            renderXML(root.asXML()) ;
	
    	}        
    	return null ;
    }

    @Pid(value=2073, log=false)
    public String reloadView() throws Exception { 
    	return "jsp" ;
    }
    public String reloadOptions() throws Exception { 
        //CodeHelper.reload("Dict") ;
        //CodeHelper.reload("Message") ;
        CodeHelper.reload();
        //renderXML("<msg>OK.</msg>") ;
        

        renderObject(null, ResponseMessage.KEY_UPDATE_OK) ;
        
    	return null ;
    }

    @Pid(value=2073)
    public String reloadContext() throws Exception {
    	
        ((AbstractRefreshableApplicationContext) CodeHelper.getAppContext()).refresh();

        renderObject(null, ResponseMessage.KEY_UPDATE_OK) ;
	    
		return null ;
    }
    
    @Pid(value=2050)
    public String create()  throws Exception {        
                
        final Object newDict = dictService.create(dict) ;
        CodeHelper.reload("Dict") ;
        
        //renderXML(this.getSuccessCreateMessage(newDict).asXML()) ;
        renderObject(newDict, ResponseMessage.KEY_CREATE_OK) ;
        
        return null;    
        
    }
    
    @Pid(value=2050)
    public String update()  throws Exception {     
        
        dictService.update(dict) ;
        CodeHelper.reload("Dict") ;

        //renderXML(this.getSuccessUpdateMessage(dict).asXML()) ;
        renderObject(dict, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }
    
    @Pid(value=2050)
    public String updateDesc()  throws Exception {     
        
        dictService.updateDesc(dict) ;
        CodeHelper.reload("Dict") ;

        //renderXML(this.getSuccessUpdateMessage(dict).asXML()) ;
        renderObject(dict, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }
    
    @Pid(value=2050)
    public String save()  throws Exception {     
        
        dictService.save(this.items, this.total) ;
        CodeHelper.reload("Dict") ;

        //renderXML(this.getSuccessUpdateMessage(dict).asXML()) ;
        renderObject(dict, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }
    
    @Pid(value=2050)
    public String delete()  throws Exception {
        
        if (dict_ids == null && ids == null) {
            dictService.delete(dict) ;
        } else {
            dictService.deleteAll(dict_ids == null ? ids : dict_ids) ;
        }

        CodeHelper.reload("Dict") ;
        
        //renderXML(this.getSuccessDeleteMessage().asXML()) ;
        renderObject(dict, ResponseMessage.KEY_DELETE_OK) ;
        
        
        return null;    
        
    }


    public String countryOptions() throws Exception {

    	List result = dictService.countryOptions(term) ;
    	log.debug("\n\n" + result) ;
    	this.renderJson(GsonUtil.getGson().toJson(result)) ;
        //renderList(dictService.countryOptions(term)) ;
        
        return null ;    
        
    }
    
    public void prepare() throws Exception {
        // TODO Auto-generated method stub

    }
        
    // ------------------------------------------------------------------------
    
    // ioc
    public void setDictBO(DictBO dictService) {
        this.dictService = dictService;
    }

    // entity...
    public Dict getDict() {
        return dict;
    }

    public void setDict(Dict dict) {
        this.dict = dict;
    }

    public void setDict_ids(Long[] dict_ids) {
        this.dict_ids = dict_ids;
    }

	public DictSO getDictSO() {
		return dictSO;
	}

	public void setDictSO(DictSO dictSO) {
		this.dictSO = dictSO;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}


	public Map<String, Object> getItems() {
		return items;
	}

	public void setItems(Map<String, Object> items) {
		this.items = items;
	}
}
