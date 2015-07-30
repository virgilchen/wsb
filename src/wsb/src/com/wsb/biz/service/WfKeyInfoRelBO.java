package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.wsb.biz.entity.WfKeyInfoRel;
import com.wsb.biz.entity.WfKeyInfoRelSO;

@Service("wfKeyInfoRelBO")
@Scope("prototype")
@Transactional
public class WfKeyInfoRelBO extends BaseServiceImpl {
	
	public WfKeyInfoRel create(WfKeyInfoRel wfKeyInfoRel) {  

    	WfKeyInfoRel newItem = (WfKeyInfoRel) jdbcDao.insert(wfKeyInfoRel) ;
    	
        
        return newItem;
    }
    
    public void update(WfKeyInfoRel wfKeyInfoRel) {
    	
        jdbcDao.update(wfKeyInfoRel) ;
        
    }
    

    public void delete(WfKeyInfoRel wfKeyInfoRel) {
    	
        jdbcDao.delete(wfKeyInfoRel) ;
        
    }

    public void deleteAll(Long[] wfKeyInfoRelIds) {
    	
        WfKeyInfoRelSO wfKeyInfoRelSO = new WfKeyInfoRelSO() ;
        wfKeyInfoRelSO.setIds(wfKeyInfoRelIds) ;
        jdbcDao.delete(WfKeyInfoRel.class, wfKeyInfoRelSO) ;
        
    }
    
    
    public ArrayPageList<WfKeyInfoRel> query(WfKeyInfoRelSO wfKeyInfoRelSO) {

        if (wfKeyInfoRelSO == null) {
        	wfKeyInfoRelSO = new WfKeyInfoRelSO() ;
        }
        wfKeyInfoRelSO.addAsc("rel_id") ;
        
        return (ArrayPageList<WfKeyInfoRel>)jdbcDao.query(wfKeyInfoRelSO, WfKeyInfoRel.class);
    }



    public WfKeyInfoRel get(Long id) {  
    	WfKeyInfoRel wfKeyInfoRel = new WfKeyInfoRel() ;
    	wfKeyInfoRel.setId(id) ;
    	wfKeyInfoRel = (WfKeyInfoRel) jdbcDao.get(wfKeyInfoRel) ;
        
        
        return wfKeyInfoRel;
    }
    
    public static WfKeyInfoRelBO getWfKeyInfoRelBO() {
    	return (WfKeyInfoRelBO)CodeHelper.getAppContext().getBean("wfKeyInfoRelBO");
    }

}
