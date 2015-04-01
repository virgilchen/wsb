package com.wsb.biz.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.entity.SessionUser;
import com.globalwave.util.DataFilterUtil;
import com.wsb.biz.entity.RecmdtInventory;
import com.wsb.biz.entity.RecmdtInventorySO;

@Service("recmdtInventoryBO")
@Scope("prototype")
@Transactional
public class RecmdtInventoryBO extends BaseServiceImpl {
	
	public RecmdtInventory create(RecmdtInventory recmdtInventory) {  

    	RecmdtInventory newItem = (RecmdtInventory) jdbcDao.insert(recmdtInventory) ;
        
        return newItem;
    }
    
    public void update(RecmdtInventory recmdtInventory) {
    	
        jdbcDao.update(recmdtInventory) ;
    }
    
    public void update(List<RecmdtInventory> recmdtInventorys, Long recmdt_id) {
    	for (RecmdtInventory recmdtInventory : recmdtInventorys) {
    		if (recmdtInventory == null) {
    			continue ;
    		}

            recmdtInventory.setRecmdt_code(recmdt_id);

    		if (recmdtInventory.getId() == null) {
    			continue ;
    		}
    		
    		RecmdtInventory oldRecmdtInventory = (RecmdtInventory)this.jdbcDao.get(recmdtInventory);
            
            DataFilterUtil.updateFilter(recmdtInventory, oldRecmdtInventory, this.getRestrictedProperties());
    	}
    	
    	RecmdtInventorySO recmdtInventorySO = new RecmdtInventorySO();
    	recmdtInventorySO.setRecmdt_code(recmdt_id);
    	jdbcDao.delete(RecmdtInventory.class, recmdtInventorySO);
    	
    	for (RecmdtInventory recmdtInventory : recmdtInventorys) {
    		if (recmdtInventory == null) {
    			continue ;
    		}

       		jdbcDao.insert(recmdtInventory);
    	}
    }
    

    public void delete(RecmdtInventory recmdtInventory) {
    	
        jdbcDao.delete(recmdtInventory) ;
        
    }

    public void deleteAll(Long[] recmdtInventoryIds) {
    	
        RecmdtInventorySO recmdtInventory = new RecmdtInventorySO() ;
        recmdtInventory.setIds(recmdtInventoryIds) ;
        jdbcDao.delete(RecmdtInventory.class, recmdtInventory) ;
        
    }
    
    
    public ArrayPageList<RecmdtInventory> query(RecmdtInventorySO recmdtInventorySO) {

        if (recmdtInventorySO == null) {
        	recmdtInventorySO = new RecmdtInventorySO() ;
        }
        recmdtInventorySO.addAsc("recmdt_id") ;
        
        ArrayPageList<RecmdtInventory> result = (ArrayPageList<RecmdtInventory>)jdbcDao.query(recmdtInventorySO, RecmdtInventory.class);

        DataFilterUtil.maskStar4List(result, getRestrictedProperties(), 0);
        
        return result ;
    }



    public RecmdtInventory get(Long id) {  
    	RecmdtInventory recmdtInventory = new RecmdtInventory() ;
    	recmdtInventory.setId(id) ;
    	recmdtInventory = (RecmdtInventory) jdbcDao.get(recmdtInventory) ;
        
        DataFilterUtil.maskStar(recmdtInventory, getRestrictedProperties(), 0);
        
        return recmdtInventory;
    }
    

    private String[] getRestrictedProperties() {
    	SessionUser su = SessionUser.get();
    	String authority = StaffRoleBO.getStaffRoleBO().get(su.getStaff().getStaff_role_id()).getStaff_role_authority();
    	
    	String pros = CodeHelper.getString("CUS.CAR.R", "desc_", authority);
    	
    	if (pros == null) {
    		return new String[] {};
    	} else {
    		return pros.split(",");
    	}
    }
    

    public static RecmdtInventoryBO getRecmdtInventoryBO() {
    	return (RecmdtInventoryBO)CodeHelper.getAppContext().getBean("recmdtInventoryBO");
    }

}
