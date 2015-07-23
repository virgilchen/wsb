package com.wsb.biz.web;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.opensymphony.xwork2.Preparable;
import com.wsb.biz.entity.Car;
import com.wsb.biz.entity.WfKeyInfoDetails;
import com.wsb.biz.entity.WfKeyInfoDetailsSO;
import com.wsb.biz.entity.WfKeyInfo;
import com.wsb.biz.entity.WfKeyInfoSO;
import com.wsb.biz.service.WfKeyInfoDetailsBO;
import com.wsb.biz.service.WfKeyInfoBO;

@Service("biz_wfKeyInfoAction")
@Scope("prototype")
public class WfKeyInfoAction extends BaseAction implements Preparable{
	
private static final long serialVersionUID = 7244882365197775441L;
    
    private WfKeyInfoBO wfKeyInfoBO ;
    private WfKeyInfo wfKeyInfo ;
    private WfKeyInfoSO wfKeyInfoSO ;
    private WfKeyInfoDetailsBO wfKeyInfoDetailsBO ;
    private WfKeyInfoDetails wfKeyInfoDetails ;
    private WfKeyInfoDetailsSO wfKeyInfoDetailsSO ; 
    private List<WfKeyInfoDetails> details ;
    
    public String execute() throws Exception { 
        return this.list(); 
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<WfKeyInfo> pageList = wfKeyInfoBO.query(wfKeyInfoSO) ;
        
        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception { 
    	
    	WfKeyInfo wfKeyInfo = wfKeyInfoBO.get(this.id) ;
    	WfKeyInfoDetailsSO wfKeyInfoDetailsSO = new WfKeyInfoDetailsSO();
    	wfKeyInfoDetailsSO.setWf_key_info_id(wfKeyInfo.getId());
    	wfKeyInfo.setWfKeyInfoDetailsList(wfKeyInfoDetailsBO.query(wfKeyInfoDetailsSO));
    	
    	renderObject(wfKeyInfo, null) ; 
    	
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        
        Object newWfKeyInfo = wfKeyInfoBO.create(wfKeyInfo,details) ;
        
        renderObject(newWfKeyInfo, ResponseMessage.KEY_CREATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

        wfKeyInfoBO.update(wfKeyInfo,details) ;

        renderObject(wfKeyInfo, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            wfKeyInfoBO.delete(wfKeyInfo) ;
        } else {
            wfKeyInfoBO.deleteAll(ids) ;
        }

        
        renderObject(wfKeyInfo, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }
    
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String view360() throws Exception {  
    	ArrayPageList<WfKeyInfo> pageList = wfKeyInfoBO.query(wfKeyInfoSO) ;

        renderList(pageList) ; 
        return "jsp" ;  
    }
    
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String openNewView() throws Exception {

    	WfKeyInfo wfKeyInfo = wfKeyInfoBO.get(this.id) ;
    	WfKeyInfoDetailsSO wfKeyInfoDetailsSO = new WfKeyInfoDetailsSO();
    	wfKeyInfoDetailsSO.setWf_key_info_id(wfKeyInfo.getId());
    	wfKeyInfo.setWfKeyInfoDetailsList(wfKeyInfoDetailsBO.query(wfKeyInfoDetailsSO));
    	
    	renderObject(wfKeyInfo, null) ; 
    	
        return null ;  
    }
    

    public void setWfKeyInfoBO(WfKeyInfoBO wfKeyInfoBO) {
		this.wfKeyInfoBO = wfKeyInfoBO;
	}

    public WfKeyInfo getWfKeyInfo() {
        return wfKeyInfo;
    }

    public void setWfKeyInfo(WfKeyInfo wfKeyInfo) {
        this.wfKeyInfo = wfKeyInfo;
    }

    public WfKeyInfoSO getWfKeyInfoSO() {
        return wfKeyInfoSO;
    }

    public void setWfKeyInfoSO(WfKeyInfoSO wfKeyInfoSO) {
        this.wfKeyInfoSO = wfKeyInfoSO;
    }

	public WfKeyInfoDetails getWfKeyInfoDetails() {
		return wfKeyInfoDetails;
	}

	public void setWfKeyInfoDetails(WfKeyInfoDetails wfKeyInfoDetails) {
		this.wfKeyInfoDetails = wfKeyInfoDetails;
	}

	public WfKeyInfoDetailsSO getWfKeyInfoDetailsSO() {
		return wfKeyInfoDetailsSO;
	}

	public void setWfKeyInfoDetailsSO(WfKeyInfoDetailsSO wfKeyInfoDetailsSO) {
		this.wfKeyInfoDetailsSO = wfKeyInfoDetailsSO;
	}

	public void setWfKeyInfoDetailsBO(WfKeyInfoDetailsBO wfKeyInfoDetailsBO) {
		this.wfKeyInfoDetailsBO = wfKeyInfoDetailsBO;
	}

	public List<WfKeyInfoDetails> getDetails() {
		return details;
	}

	public void setDetails(List<WfKeyInfoDetails> details) {
		this.details = details;
	}

}
