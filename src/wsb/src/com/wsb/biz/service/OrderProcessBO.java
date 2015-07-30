package com.wsb.biz.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.wsb.biz.entity.OrderProcess;
import com.wsb.biz.entity.OrderProcessSO;
import com.wsb.biz.entity.WfKeyInfoRel;
import com.wsb.biz.entity.WfKeyInfoRelSO;


@Service("orderProcessBO")
@Scope("prototype")
@Transactional
public class OrderProcessBO extends BaseServiceImpl {

    public void save(List<OrderProcess> orderProcesses, Long businessId) {  
    	
    	OrderProcess so = new OrderProcess();
    	so.setBusiness_id(businessId);
    	
    	jdbcDao.delete(OrderProcess.class, so) ;
    	
    	WfKeyInfoRel rel = new WfKeyInfoRel();
    	rel.setBusiness_id(businessId);
    	jdbcDao.delete(WfKeyInfoRel.class, rel);
    	
    	
    	if (orderProcesses != null) {
	    	for (OrderProcess op : orderProcesses) {
	    		if (op == null) {
	    			continue;
	    		}
	    		
	    	    jdbcDao.insert(op) ;
	    	    
	    	    if(op.getWf_key_info_id() != null && !op.getWf_key_info_id().trim().equals("")){
	    	    	if(op.getWf_key_info_id().indexOf(",")>0){
	    	    		String[] keyIds = op.getWf_key_info_id().split(",");
	    	    		for(int i=0; i<keyIds.length; i++){
	    	    			String keyid = keyIds[i];
	    	    			WfKeyInfoRel wfKeyInfoRel = new WfKeyInfoRel();
	    	    			wfKeyInfoRel.setBusiness_id(businessId);
	    	    			wfKeyInfoRel.setProcs_step_no(op.getProcs_step_no());
	    	    			wfKeyInfoRel.setWf_key_info_id(new Long(keyid));
	    	    			jdbcDao.insert(wfKeyInfoRel);
	    	    		}
	    	    	}else{
	    	    		WfKeyInfoRel wfKeyInfoRel = new WfKeyInfoRel();
    	    			wfKeyInfoRel.setBusiness_id(businessId);
    	    			wfKeyInfoRel.setProcs_step_no(op.getProcs_step_no());
    	    			wfKeyInfoRel.setWf_key_info_id(new Long(op.getWf_key_info_id()));
    	    			jdbcDao.insert(wfKeyInfoRel);
	    	    	}
	    	    }
	    	}
    	}
        
    }
    
    public void update(OrderProcess orderProcess) {
    	
        jdbcDao.update(orderProcess) ;
    }
    

    public void delete(OrderProcess orderProcess) {
    	
        jdbcDao.delete(orderProcess) ;
        
    }

    /*
    public void deleteAll(Long[] orderProcessIds) {
    	
        OrderProcessSO criterion = new OrderProcessSO() ;
        criterion.setIds(orderProcessIds) ;
        jdbcDao.delete(OrderProcess.class, criterion) ;
        
    }*/
    
    
    public ArrayPageList<OrderProcess> query(OrderProcessSO orderProcessSO) {

        if (orderProcessSO == null) {
        	orderProcessSO = new OrderProcessSO() ;
        }
        orderProcessSO.addAsc("procs_step_no") ;
        
        ArrayPageList<OrderProcess> opList = (ArrayPageList<OrderProcess>)jdbcDao.query(orderProcessSO, OrderProcess.class);
        if(opList != null && opList.size()>0){
        	for(OrderProcess op : opList){
        		WfKeyInfoRelSO wfKeyInfoRelSO = new WfKeyInfoRelSO();
        		wfKeyInfoRelSO.setBusiness_id(op.getBusiness_id());
        		wfKeyInfoRelSO.setProcs_step_no(op.getProcs_step_no());
        		ArrayPageList<WfKeyInfoRel> relList = new ArrayPageList<WfKeyInfoRel>();
        		relList = WfKeyInfoRelBO.getWfKeyInfoRelBO().query(wfKeyInfoRelSO);
        		String keyIds = "";
        		if(relList != null && relList.size()>0){
        			for(WfKeyInfoRel wfKeyInfoRel : relList){
        				keyIds += wfKeyInfoRel.getWf_key_info_id() + ",";
        			}
        			keyIds = keyIds.substring(0,keyIds.lastIndexOf(","));
        		}
        		op.setWf_key_info_id(keyIds);
        	}
        }
        
        return opList;
    }


/*
    public OrderProcess get(Long id) {  
    	OrderProcess org = new OrderProcess() ;
    	org.setId(id) ;
        org = (OrderProcess) jdbcDao.get(org) ;
        
        
        return org;
    }
    */
}
