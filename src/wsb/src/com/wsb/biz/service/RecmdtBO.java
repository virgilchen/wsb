package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.util.DataFilterUtil;
import com.wsb.biz.entity.Recmdt;
import com.wsb.biz.entity.RecmdtSO;
import com.wsb.biz.entity.RecommendationKeyMapDim;
import com.wsb.biz.entity.RecommendationKeyMapDimSO;
import com.wsb.biz.entity.RecommendationOprMapDim;
import com.wsb.biz.entity.RecommendationOprMapDimSO;



@Service("recmdtBO")
@Scope("prototype")
@Transactional
public class RecmdtBO extends BaseServiceImpl {
	
	public Recmdt create(Recmdt recmdt) {  

		Recmdt newItem = (Recmdt) jdbcDao.insert(recmdt) ;
        
        return newItem;
    }
	
    public void update(Recmdt recmdt) {
    	
        jdbcDao.update(recmdt) ;
    }
	
    public void delete(Recmdt recmdt) {
    	
        jdbcDao.delete(recmdt) ;
        
    }
    
    public void deleteAll(Long[] recmdtIds) {
    	
    	RecmdtSO recmdtSO = new RecmdtSO() ;
    	recmdtSO.setIds(recmdtIds) ;
        jdbcDao.delete(Recmdt.class, recmdtSO) ;
        
    }
    
    public ArrayPageList<Recmdt> query(RecmdtSO recmdtSO) {

        if (recmdtSO == null) {
        	recmdtSO = new RecmdtSO() ;
        }
        recmdtSO.addDesc("recmdt_id") ;
        
        return (ArrayPageList<Recmdt>)jdbcDao.query(recmdtSO, Recmdt.class);
    }
    
    public Recmdt get(Long id) {  
    	Recmdt recmdt = new Recmdt() ;
    	recmdt.setId(id) ;
    	recmdt = (Recmdt) jdbcDao.get(recmdt) ;
        
        return recmdt;
    }
    
    public ArrayPageList<RecommendationKeyMapDim> queryRecommendationKeyType() {

        ArrayPageList<RecommendationKeyMapDim> result = 
        		(ArrayPageList<RecommendationKeyMapDim>)jdbcDao.queryName("bizSQLs:queryRecommendationKeyType", 
        				null, RecommendationKeyMapDim.class);

        return result;
    }
    
    public ArrayPageList<RecommendationKeyMapDim> queryRecommendationByKey(String key) {

    	RecommendationKeyMapDimSO recommendationKeyMapDimSO = new RecommendationKeyMapDimSO();
    	if(key != null && !key.equals(""))
    	recommendationKeyMapDimSO.setRecmdt_key_type(key);
        
        ArrayPageList<RecommendationKeyMapDim> result = 
        		(ArrayPageList<RecommendationKeyMapDim>)jdbcDao.queryName("bizSQLs:queryRecommendationByKey", 
        				recommendationKeyMapDimSO, RecommendationKeyMapDim.class);

        return result;
    }
    
    public ArrayPageList<RecommendationOprMapDim> queryRecommendationOprMapDim(RecommendationOprMapDimSO recommendationOprMapDimSO) {

        if (recommendationOprMapDimSO == null) {
        	recommendationOprMapDimSO = new RecommendationOprMapDimSO() ;
        }
        
        ArrayPageList<RecommendationOprMapDim> result = 
        		(ArrayPageList<RecommendationOprMapDim>)jdbcDao.queryName("bizSQLs:queryRecommendationOprMapDim", 
        				recommendationOprMapDimSO, RecommendationOprMapDim.class);

        return result;
    }
    
}