package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.Business;
import com.wsb.biz.entity.BusinessSO;
import com.wsb.biz.entity.OrderProcess;
import com.wsb.biz.entity.OrderProcessSO;


@Service("businessBO")
@Scope("prototype")
@Transactional
public class BusinessBO extends BaseServiceImpl {

    public Business create(Business business) {  
    	
    	Long pid = business.getBusiness_id_upper();
    	if (pid != null && lock(pid) == 0) {
    		throw new BusinessException(12001L) ;//12001', '父目录不存在，本操作无效！
    	}

    	business.setDeep_level((short)(this.get(pid).getDeep_level() + 1));
    	
    	Business newItem = (Business) jdbcDao.insert(business) ;
        
        return newItem;
    }
    
    public void update(Business business) {
    	
    	lock(business.getBusiness_id_upper()) ;
    	
        jdbcDao.update(business) ;
    }
    

    public void delete(Business business) {

    	lock(business.getBusiness_id_upper()) ;
    	
    	if (hasChildren(business.getId())) {
    		throw new BusinessException(12002L) ;// 12002', '子目录存在，本操作无效！
    	}
    	
    	if (jdbcDao.queryName("bizSQLs:businessHasUsed", business, Business.class).size() > 0) {
    		throw new BusinessException(12003L) ;// 12003', '业务已被使用，不能被删除！
    	}
    	
        jdbcDao.delete(business) ;

        OrderProcessSO criterion = new OrderProcessSO() ;
        criterion.setBusiness_id(business.getId()) ;
        jdbcDao.delete(OrderProcess.class, criterion) ;
    }

    public void deleteAll(Long[] businessIds) {
    	/*
    	for (Long oId:businessIds) {
        	if (hasChildren(oId)) {
        		throw new BusinessException(12002L) ;// 12002', '子目录存在，本操作无效！
        	}
    	}
    	
        BusinessSO criterion = new BusinessSO() ;
        criterion.setIds(businessIds) ;
        jdbcDao.delete(Business.class, criterion) ;
		*/
    	
    	for (Long oId:businessIds) {
    		Business b = new Business();
    		b.setId(oId);
    		delete(b);
    	}
    }
    
    
    public ArrayPageList<Business> query(BusinessSO businessSO) {

        if (businessSO == null) {
        	businessSO = new BusinessSO() ;
        }
        businessSO.addAsc("business_id_upper");
        businessSO.addAsc("business_id") ;
        
        return (ArrayPageList<Business>)jdbcDao.query(businessSO, Business.class);
    }



    public Business get(Long id) {  
    	Business biz = new Business() ;
    	biz.setId(id) ;
        biz = (Business) jdbcDao.get(biz) ;
        
        
        return biz;
    }
    
    

    private boolean hasChildren(Long businessId) {
    	Business so = new Business() ;
    	so.setBusiness_id_upper(businessId) ;
    	return jdbcDao.find(so) != null ;
    }

    private int lock(Long businessId) {
    	if (businessId == null) {
    		return 0;
    	}

    	Business o = new Business() ;
    	o.setOperate(Business.OPERATE_UPDATE_UNVERSION) ;
    	o.setId(businessId) ;
    	o.addInclusions("id") ;
    	
    	return jdbcDao.update(o) ;
    }
    
}
