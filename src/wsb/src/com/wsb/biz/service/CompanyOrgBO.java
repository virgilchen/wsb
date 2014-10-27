package com.wsb.biz.service;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.CompanyOrg;
import com.wsb.biz.entity.CompanyOrgSO;




@Service("companyOrgBO")
@Scope("prototype")
@Transactional
public class CompanyOrgBO extends BaseServiceImpl {
	
	public CompanyOrg create(CompanyOrg companyorg) {  

    	Long pid = companyorg.getOrg_id_upper();
    	if (pid != null && pid != 0 && lock(pid) == 0) {
    		throw new BusinessException(13001L) ;//13001', '父目录不存在，本操作无效！
    	}
    	
		Long proId = companyorg.getOrg_id_upper();
		
		if(proId == null || proId == 0L) {
			companyorg.setOrg_level(0);
		} else {
			CompanyOrg proItem = this.get(proId) ;
			companyorg.setOrg_level(proItem.getOrg_level() + 1);
		}
		
		CompanyOrg newItem = (CompanyOrg) jdbcDao.insert(companyorg) ;
        
        return newItem;
    }
	
	public void update(CompanyOrg companyorg) {

    	lock(companyorg.getOrg_id_upper()) ;
    	
        jdbcDao.update(companyorg) ;
    }
    
	public void delete(CompanyOrg companyorg) {

    	lock(companyorg.getOrg_id_upper()) ;
    	
    	if (hasChildren(companyorg.getId())) {
    		throw new BusinessException(13002L) ;// 13002', '子目录存在，本操作无效！
    	}
    	
        jdbcDao.delete(companyorg) ;
    }
	
	public void deleteAll(Long[] orgIds) {

    	for (Long oId:orgIds) {
        	if (hasChildren(oId)) {
        		throw new BusinessException(13002L) ;// 13002', '子目录存在，本操作无效！
        	}
    	}
    	
		CompanyOrgSO criterion = new CompanyOrgSO() ;
        criterion.setIds(orgIds);
        jdbcDao.delete(CompanyOrg.class, criterion) ;
    }
	
	public ArrayPageList<CompanyOrg> query(CompanyOrgSO companyorgSO) {

        if (companyorgSO == null) {
        	companyorgSO = new CompanyOrgSO() ;
        }

        companyorgSO.addAsc("org_level") ;
        companyorgSO.addAsc("org_name") ;
        
        return (ArrayPageList<CompanyOrg>)jdbcDao.query(companyorgSO, CompanyOrg.class);
    }
	
	public CompanyOrg get(Long orgId) {  
		   CompanyOrg org = new CompanyOrg() ;
	    	org.setId(orgId) ;
	        org = (CompanyOrg) jdbcDao.get(org) ;
	        
	        return org;
	}


    private boolean hasChildren(Long businessId) {
    	CompanyOrg so = new CompanyOrg() ;
    	so.setOrg_id_upper(businessId) ;
    	return jdbcDao.find(so) != null ;
    }

    private int lock(Long businessId) {
    	if (businessId == null) {
    		return 0;
    	}

    	CompanyOrg o = new CompanyOrg() ;
    	o.setOperate(CompanyOrg.OPERATE_UPDATE_UNVERSION) ;
    	o.setId(businessId) ;
    	o.addInclusions("id") ;
    	
    	return jdbcDao.update(o) ;
    }
    
}