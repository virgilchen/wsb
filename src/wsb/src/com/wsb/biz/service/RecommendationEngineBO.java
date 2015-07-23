package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.wsb.biz.entity.RecommendationEngine;
import com.wsb.biz.entity.RecommendationEngineSO;

@Service("recommendationEngineSO")
@Scope("prototype")
@Transactional
public class RecommendationEngineBO extends BaseServiceImpl {
	
	public ArrayPageList<RecommendationEngine> query(RecommendationEngineSO recommendationEngineSO) {

        if (recommendationEngineSO == null) {
        	recommendationEngineSO = new RecommendationEngineSO() ;
        }
        recommendationEngineSO.addAsc("recmdt_engine_id") ;
        
        ArrayPageList<RecommendationEngine> result = (ArrayPageList<RecommendationEngine>)jdbcDao.query(recommendationEngineSO, RecommendationEngine.class);

        return result ;
    }
	
    

    public static RecommendationEngineBO getRecommendationEngineBO() {
    	return (RecommendationEngineBO)CodeHelper.getAppContext().getBean("recommendationEngineSO");
    }

}
