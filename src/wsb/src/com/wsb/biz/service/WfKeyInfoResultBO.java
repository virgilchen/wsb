package com.wsb.biz.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.wsb.biz.entity.WfKeyInfoResult;
import com.wsb.biz.entity.WfKeyInfoResultSO;

@Service("wfKeyInfoResultBO")
@Scope("prototype")
@Transactional
public class WfKeyInfoResultBO extends BaseServiceImpl{
	
	public WfKeyInfoResult create(WfKeyInfoResult wfKeyInfoResult) {  

    	WfKeyInfoResult newItem = (WfKeyInfoResult) jdbcDao.insert(wfKeyInfoResult) ;
    	
        return newItem;
    }
    
    public void update(WfKeyInfoResult wfKeyInfoResult) {
    	
        jdbcDao.update(wfKeyInfoResult) ;
        
    }
    

    public void delete(WfKeyInfoResult wfKeyInfoResult) {
    	
        jdbcDao.delete(wfKeyInfoResult) ;
        
    }

    public void deleteAll(Long[] wfKeyInfoResultIds) {
    	
        WfKeyInfoResultSO wfKeyInfoResult = new WfKeyInfoResultSO() ;
        wfKeyInfoResult.setIds(wfKeyInfoResultIds) ;
        jdbcDao.delete(WfKeyInfoResult.class, wfKeyInfoResult) ;
        
    }
    
    
    public ArrayPageList<WfKeyInfoResult> query(WfKeyInfoResultSO wfKeyInfoResultSO) {

        if (wfKeyInfoResultSO == null) {
        	wfKeyInfoResultSO = new WfKeyInfoResultSO() ;
        }
        wfKeyInfoResultSO.addAsc("wf_key_info_result_id") ;
        
        return (ArrayPageList<WfKeyInfoResult>)jdbcDao.query(wfKeyInfoResultSO, WfKeyInfoResult.class);
    }



    public WfKeyInfoResult get(Long id) {  
    	WfKeyInfoResult wfKeyInfoResult = new WfKeyInfoResult() ;
    	wfKeyInfoResult.setId(id) ;
    	wfKeyInfoResult = (WfKeyInfoResult) jdbcDao.get(wfKeyInfoResult) ;
        
        
        return wfKeyInfoResult;
    }
    
    public static WfKeyInfoResultBO getWfKeyInfoResultBO() {
    	return (WfKeyInfoResultBO)CodeHelper.getAppContext().getBean("wfKeyInfoResultBO");
    }

}
