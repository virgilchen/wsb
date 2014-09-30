package com.globalwave.system.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.system.entity.Dict;
import com.globalwave.system.entity.DictSO;
import com.globalwave.util.AESEncryptUtil;

@Service("dictBO")
@Scope("prototype")
@Transactional
public class DictBO extends BaseServiceImpl {

    @SuppressWarnings("unchecked")
    public ArrayPageList<Dict> query(DictSO dictCriterion) {
    	
    	/*
    	if (dictCriterion != null) {
	    	if (StringUtils.isEmpty(dictCriterion.getCode_())) {
	    		dictCriterion.setCode_(null) ;
	    	}
	    	
	    	if (StringUtils.isEmpty(dictCriterion.getDomain_())) {
	    		dictCriterion.setDomain_(null) ;
	    	}
	    	
	    	if (StringUtils.isEmpty(dictCriterion.getName_())) {
	    		dictCriterion.setName_(null) ;
	    	}
	    	
	    	if (StringUtils.isEmpty(dictCriterion.getStatus_())) {
	    		dictCriterion.setStatus_(null) ;
	    	}
    	} else {
    	*/
        if (dictCriterion == null) {
    		dictCriterion = new DictSO() ;
    	}
    	
		dictCriterion.addAsc("domain_") ;
		dictCriterion.addAsc("order_") ;
	
        return (ArrayPageList<Dict>)jdbcDao.query(dictCriterion, Dict.class);
    }

    private void checkDictExisted(Dict dict) {
    	if (this.isDictExisted(dict.getDomain_(), dict.getCode_())){
    		throw new BusinessException(1301L, dict.getDomain_(), dict.getCode_()) ;//1301', '字典表中已经存在[{0}-{1}]的定义，请修改编码再保存！
    	}
    }
    private boolean isDictExisted(String domain, String code) {
    	Dict dict = new Dict() ;
    	dict.setDomain_(domain) ;
    	dict.setCode_(code) ;
    	
        return jdbcDao.find(dict) != null;
        
    }

    public Dict create(Dict dict) { 
    	checkDictExisted(dict) ;
        final Dict newDict = (Dict) jdbcDao.insert(dict) ;
                
        return newDict;
    }
    

    public void save(Map<String, Object> dicts, Integer total) {
        
        for (int i = 0 ;i < total ; i ++) {
            Dict dict = new Dict() ;
            
            dict.setId(Long.valueOf((String)dicts.get("id_" + i))) ;
            dict.setName_((String)dicts.get("value_" + i)) ;
            dict.addInclusions("name_") ;
            
            jdbcDao.update(dict);        
        }
    }

    public void update(Dict dict) {        
    	Dict old = (Dict)this.jdbcDao.get(dict) ;
    	
    	if (!old.getCode_().equals(dict.getCode_())) {
        	checkDictExisted(dict) ;
    	}
    	
        jdbcDao.update(dict);
    }

    public void updateDesc(Dict dict) {  
    	DictSO so = new DictSO();
    	
    	so.setDomain_eq(dict.getDomain_()) ;
    	so.setCode_eq(dict.getCode_()) ;
    	if (dict.getCode_().indexOf("password") > 0) {
    		dict.setDesc_(AESEncryptUtil.encrypt(dict.getDesc_())) ;
    	}
    	
    	dict.addInclusions("desc_") ;
    	
        jdbcDao.update(dict, so);
    }

    public void delete(Dict dict) {
        
        jdbcDao.delete(dict) ;
        
    }

    public void deleteAll(Long[] ids) {
        
        DictSO so = new DictSO() ;
        so.setIds(ids) ;

        jdbcDao.delete(Dict.class, so) ;
        
    }

    public Dict get(Long dict_id) {
        Dict dict = new Dict() ;
        dict.setId(dict_id) ;
        
        return (Dict)jdbcDao.get(dict) ;
    }


    public ArrayPageList<HashMap> countryOptions(String term) {
    	

        DictSO dictSo = new DictSO() ;
        dictSo.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE);
        dictSo.setTerm(term) ;
    	
        dictSo.addAsc("code") ;
        dictSo.addAsc("name_") ;
	
        return (ArrayPageList<HashMap>)jdbcDao.queryName("systemSQLs:countryOptions", dictSo, HashMap.class);
    }
}
