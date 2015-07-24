package com.wsb.biz.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.wsb.biz.entity.WfKeyInfoDetails;
import com.wsb.biz.entity.WfKeyInfoDetailsSO;

@Service("wfKeyInfoDetailsBO")
@Scope("prototype")
@Transactional
public class WfKeyInfoDetailsBO extends BaseServiceImpl{
	
	public WfKeyInfoDetails create(WfKeyInfoDetails wfKeyInfoDetails) {  

    	WfKeyInfoDetails newItem = (WfKeyInfoDetails) jdbcDao.insert(wfKeyInfoDetails) ;
        
        return newItem;
    }
    
    public void update(WfKeyInfoDetails wfKeyInfoDetails) {
    	
        jdbcDao.update(wfKeyInfoDetails) ;
    }
    
    public void updateAll(List<WfKeyInfoDetails> details, Long wf_key_info_id) {
    	
    	WfKeyInfoDetailsSO wfKeyInfoDetailsSO = new WfKeyInfoDetailsSO();
    	wfKeyInfoDetailsSO.setWf_key_info_id(wf_key_info_id);
    	jdbcDao.delete(WfKeyInfoDetails.class, wfKeyInfoDetailsSO);
    	
    	for (int i = 0; i < details.size(); i++) {
    		if (details.get(i) == null) {
    			continue ;
    		}
			details.get(i).setWf_key_info_id(wf_key_info_id);
			jdbcDao.insert(details.get(i));
		}
    }
    

    public void delete(WfKeyInfoDetails wfKeyInfoDetails) {
    	
        jdbcDao.delete(wfKeyInfoDetails) ;
        
    }

    public void deleteAll(Long[] wfKeyInfoDetailsIds) {
    	
        WfKeyInfoDetailsSO wfKeyInfoDetails = new WfKeyInfoDetailsSO() ;
        wfKeyInfoDetails.setIds(wfKeyInfoDetailsIds) ;
        jdbcDao.delete(WfKeyInfoDetails.class, wfKeyInfoDetails) ;
        
    }
    
    
    public ArrayPageList<WfKeyInfoDetails> query(WfKeyInfoDetailsSO wfKeyInfoDetailsSO) {

        if (wfKeyInfoDetailsSO == null) {
        	wfKeyInfoDetailsSO = new WfKeyInfoDetailsSO() ;
        }
        wfKeyInfoDetailsSO.addAsc("wf_key_info_details_id") ;
        
        return (ArrayPageList<WfKeyInfoDetails>)jdbcDao.query(wfKeyInfoDetailsSO, WfKeyInfoDetails.class);
    }



    public WfKeyInfoDetails get(Long id) {  
    	WfKeyInfoDetails wfKeyInfoDetails = new WfKeyInfoDetails() ;
    	wfKeyInfoDetails.setId(id) ;
    	wfKeyInfoDetails = (WfKeyInfoDetails) jdbcDao.get(wfKeyInfoDetails) ;
        
        
        return wfKeyInfoDetails;
    }
    
    public static WfKeyInfoDetailsBO getWfKeyInfoDetailsBO() {
    	return (WfKeyInfoDetailsBO)CodeHelper.getAppContext().getBean("wfKeyInfoDetailsBO");
    }
}
