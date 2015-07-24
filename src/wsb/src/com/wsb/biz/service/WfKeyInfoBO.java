package com.wsb.biz.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.wsb.biz.entity.WfKeyInfo;
import com.wsb.biz.entity.WfKeyInfoDetails;
import com.wsb.biz.entity.WfKeyInfoDetailsSO;
import com.wsb.biz.entity.WfKeyInfoSO;

@Service("wfKeyInfoBO")
@Scope("prototype")
@Transactional
public class WfKeyInfoBO extends BaseServiceImpl{
	
	public WfKeyInfo create(WfKeyInfo wfKeyInfo,List<WfKeyInfoDetails> details) {  

    	WfKeyInfo newItem = (WfKeyInfo) jdbcDao.insert(wfKeyInfo) ;
    	
    	if (details != null) {
			for (int i = 0; i < details.size(); i++) {
				details.get(i).setWf_key_info_id(newItem.getId());
				jdbcDao.insert(details.get(i));
			}
		}
        
        return newItem;
    }
    
    public void update(WfKeyInfo wfKeyInfo,List<WfKeyInfoDetails> details) {
    	
        jdbcDao.update(wfKeyInfo) ;
        
        if (details != null) {
        	WfKeyInfoDetailsBO.getWfKeyInfoDetailsBO().updateAll(details, wfKeyInfo.getId());
		}
    }
    

    public void delete(WfKeyInfo wfKeyInfo) {
    	
        jdbcDao.delete(wfKeyInfo) ;
        
    }

    public void deleteAll(Long[] wfKeyInfoIds) {
    	
        WfKeyInfoSO wfKeyInfo = new WfKeyInfoSO() ;
        wfKeyInfo.setIds(wfKeyInfoIds) ;
        jdbcDao.delete(WfKeyInfo.class, wfKeyInfo) ;
        
    }
    
    
    public ArrayPageList<WfKeyInfo> query(WfKeyInfoSO wfKeyInfoSO) {

        if (wfKeyInfoSO == null) {
        	wfKeyInfoSO = new WfKeyInfoSO() ;
        }
        wfKeyInfoSO.addAsc("wf_key_info_id") ;
        
        return (ArrayPageList<WfKeyInfo>)jdbcDao.query(wfKeyInfoSO, WfKeyInfo.class);
    }



    public WfKeyInfo get(Long id) {  
    	WfKeyInfo wfKeyInfo = new WfKeyInfo() ;
    	wfKeyInfo.setId(id) ;
    	wfKeyInfo = (WfKeyInfo) jdbcDao.get(wfKeyInfo) ;
        
        
        return wfKeyInfo;
    }
    
    public static WfKeyInfoBO getWfKeyInfoBO() {
    	return (WfKeyInfoBO)CodeHelper.getAppContext().getBean("wfKeyInfoBO");
    }

}
