package com.globalwave.system.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.cache.CodeHelper;


/**
 *
 * 
 * @author Virgil
 *
 */
@Service("sequenceBO")
@Scope("prototype")
@Transactional
public class SequenceBO extends BaseServiceImpl {

    public Long nextValue(String name) {  

    	Object[] params = new Object[]{name} ;
    	int i = jdbcDao.execute("update cfg_sequence set value=value+1 where name=?", params);
        
    	if (i == 0) {
    		jdbcDao.execute("insert cfg_sequence (name, value) values(?, ?)", new Object[]{name, 0});
        	jdbcDao.execute("update cfg_sequence set value=value+1 where name=?", params);
    	}
    	
    	
        return jdbcDao.getLong("select value from cfg_sequence where name=?", params);
    }
    
    public Long currentValue(String name) {

    	Object[] params = new Object[]{name} ;
        return jdbcDao.getLong("select value from cfg_sequence where name=?", params);     
    }

    public static SequenceBO getSequenceBO() {
    	return (SequenceBO)CodeHelper.getAppContext().getBean("sequenceBO");
    }
}
