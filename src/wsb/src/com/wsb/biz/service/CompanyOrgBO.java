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

		CompanyOrg newItem = (CompanyOrg) jdbcDao.insert(companyorg) ;
        
        return newItem;
    }
	
	public void update(CompanyOrg companyorg) {
    	
        jdbcDao.update(companyorg) ;
    }
    
	public void delete(CompanyOrg companyorg) {
    	
        jdbcDao.delete(companyorg) ;
    }
	
	public void deleteAll(Long[] orgIds) {
    	
		CompanyOrgSO criterion = new CompanyOrgSO() ;
        criterion.setIds(orgIds);
        jdbcDao.delete(CompanyOrg.class, criterion) ;
    }
	
	public ArrayPageList<CompanyOrg> query(CompanyOrgSO companyorgSO) {

        if (companyorgSO == null) {
        	companyorgSO = new CompanyOrgSO() ;
        }
        companyorgSO.addDesc("org_id") ;
        
        return (ArrayPageList<CompanyOrg>)jdbcDao.query(companyorgSO, CompanyOrg.class);
    }
	
	public CompanyOrg get(Long orgId) {  
		   CompanyOrg org = new CompanyOrg() ;
	    	org.setId(orgId) ;
	        org = (CompanyOrg) jdbcDao.get(org) ;
	        
	        return org;
	}
}