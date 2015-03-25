package com.wsb.biz.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.Recmdt;
import com.wsb.biz.entity.RecmdtInventory;
import com.wsb.biz.entity.RecmdtSO;
import com.wsb.biz.entity.RecommendationKeyMapDimSO;
import com.wsb.biz.entity.RecommendationOprMapDimSO;
import com.wsb.biz.service.RecmdtBO;
import com.opensymphony.xwork2.Preparable;



@Service("biz_recmdtAction")
@Scope("prototype")
public class RecmdtAction extends BaseAction implements Preparable {
	
	private static final long serialVersionUID = 7244882365197775441L;
	
	private Recmdt recmdt;
	private RecmdtSO recmdtSO;
	private RecmdtBO recmdtBO;
	private List<RecmdtInventory> recmdtInventorys;
	
	
	public String execute() throws Exception { 
        return this.list(); 
    }
	
	
	@Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<Recmdt> pageList = recmdtBO.query(recmdtSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }
	
	
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	Recmdt recmdt = recmdtBO.get(this.id) ;
    	
    	ArrayList optList = new ArrayList();
    	
    	RecommendationKeyMapDimSO recommendationKeyMapDimSO = new RecommendationKeyMapDimSO();
    	
    	//资料类型下拉菜单
    	getRequest().setAttribute("keyTypeList", recmdtBO.queryRecommendationKeyType());
    	
    	//资料内容下拉菜单
    	String key = "";
    	getRequest().setAttribute("keyValueList", recmdtBO.queryRecommendationByKey(key));
    	
    	//运算符下拉菜单
    	RecommendationOprMapDimSO recommendationOprMapDimSO =  new RecommendationOprMapDimSO();
    	getRequest().setAttribute("oprValueList", recmdtBO.queryRecommendationOprMapDim(recommendationOprMapDimSO));
    	
    	renderObject(recmdt, null) ; 
        return null ;  
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newRecmdt = recmdtBO.create(recmdt,recmdtInventorys) ;

        renderObject(newRecmdt, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
    	recmdtBO.update(recmdt) ;

        renderObject(recmdt, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
        	recmdtBO.delete(recmdt) ;
        } else {
        	recmdtBO.deleteAll(ids) ;
        }

        
        renderObject(recmdt, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }


	public Recmdt getRecmdt() {
		return recmdt;
	}


	public void setRecmdt(Recmdt recmdt) {
		this.recmdt = recmdt;
	}


	public RecmdtSO getRecmdtSO() {
		return recmdtSO;
	}


	public void setRecmdtSO(RecmdtSO recmdtSO) {
		this.recmdtSO = recmdtSO;
	}


	public RecmdtBO getRecmdtBO() {
		return recmdtBO;
	}


	public void setRecmdtBO(RecmdtBO recmdtBO) {
		this.recmdtBO = recmdtBO;
	}


	public List<RecmdtInventory> getRecmdtInventorys() {
		return recmdtInventorys;
	}


	public void setRecmdtInventorys(List<RecmdtInventory> recmdtInventorys) {
		this.recmdtInventorys = recmdtInventorys;
	}
    
    
    
    
}