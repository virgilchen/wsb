package com.globalwave.system.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.system.entity.Organization;
import com.globalwave.system.entity.OrganizationSO;
import com.globalwave.system.entity.OrganizationPrivilege;
import com.globalwave.system.entity.Role;
import com.globalwave.system.entity.SessionUser;
import com.globalwave.system.entity.UserOrganization;
import com.globalwave.system.entity.UserSO;


/**
 * 管理员不受限制
 * 
 * 非管理员：
 * 只能看父级列表，但不能看明细及编辑
 * 能查下级（平铺时，不能跨级查），但不能跨级编辑
 * 
 * @author Virgil
 *
 */
@Service("organizationBO")
@Scope("prototype")
@Transactional
public class OrganizationBO extends BaseServiceImpl {

    public Organization create(Organization organization, Long[] privilege_ids) {  

    	Long pid = organization.getPro_organization_id() ;
    	if (pid != null && lock(pid) == 0) {
    		throw new BusinessException(1051L) ;//1051', '父组织不存在，本操作无效！
    	}
    	
    	checkRate(organization, null);

        checkOperationAuth(new long[]{organization.getPro_organization_id()}) ;
        
        Organization newOrganization = (Organization) jdbcDao.insert(organization) ;
        
        if (privilege_ids != null) {
            for (Long privilege_id : privilege_ids) {
                OrganizationPrivilege orgPrivilege = new OrganizationPrivilege() ;
                orgPrivilege.setPrivilege_id(privilege_id) ;
                orgPrivilege.setOrganization_id(newOrganization.getId()) ;
                jdbcDao.insert(orgPrivilege) ;
            }
        }
        
        return newOrganization;
    }
    public void update(Organization organization, Long[] privilege_ids) {

    	if (organization.getId().equals(organization.getPro_organization_id())) {
    		throw new BusinessException(1053L) ;//1053	父组织与子组织不该一样的标识！
    	}
    	
    	Long tempId = organization.getPro_organization_id() ;
    	for (int i = 0 ; i < 100 ; i ++) {
    		tempId = CodeHelper.getLong("Organization", "pro_organization_id", tempId) ;
    		if (tempId == null) {
    			break ;
    		} 
    		if (tempId.equals(organization.getId())) {
    			throw new BusinessException(1054L) ;//1054	父子关系包含死循环，请重新选择父组织！
    		}
    	}
    	
    	lock(organization.getPro_organization_id()) ;

    	checkRate(organization, null);
    	
        checkOperationAuth(new long[]{organization.getPro_organization_id()}) ;
    	
        jdbcDao.update(organization) ;

        OrganizationPrivilege orgPrivilege = new OrganizationPrivilege() ;
        orgPrivilege.setOrganization_id(organization.getId()) ;
        jdbcDao.delete(OrganizationPrivilege.class, orgPrivilege) ;
        
        if (privilege_ids != null) {
        	orgPrivilege = new OrganizationPrivilege() ;
        	orgPrivilege.setOrganization_id(organization.getId()) ;

        	for (Long privilege_id : privilege_ids) {
                orgPrivilege.setPrivilege_id(privilege_id) ;
                jdbcDao.insert(orgPrivilege) ;
            }
        }        
    }

    public void delete(Organization organization)  {

    	lock(organization.getPro_organization_id()) ;
    	
    	if (hasChildren(organization.getId())) {
    		throw new BusinessException(1052L) ;// 1052', '子组织存在，本操作无效！
    	}
    	

        checkOperationAuth(new long[]{((Organization)jdbcDao.get(Organization.class, organization.getId())).getPro_organization_id()}) ;
    	
        // delete cascade
        OrganizationPrivilege orgPrivilege = new OrganizationPrivilege() ;
        orgPrivilege.setOrganization_id(organization.getId()) ;
        jdbcDao.delete(OrganizationPrivilege.class, orgPrivilege) ;
        
        // delete cascade
        UserOrganization userOrg = new UserOrganization() ;
        userOrg.setOrganization_id(organization.getId()) ;
        jdbcDao.delete(UserOrganization.class,userOrg) ;
        
        jdbcDao.delete(organization) ;
        
    }

    public void deleteAll(Long[] organizationIds)  {
    	
    	for (Long oId:organizationIds) {
        	if (hasChildren(oId)) {
        		throw new BusinessException(1052L) ;// 1052', '子组织存在，本操作无效！
        	}

            checkOperationAuth(new long[]{((Organization)jdbcDao.get(Organization.class, oId)).getPro_organization_id()}) ;
    	}
    	
        OrganizationSO criterion = new OrganizationSO() ;
        criterion.setOrganizationIds(organizationIds) ;
        
        // delete cascade
        jdbcDao.delete(OrganizationPrivilege.class, criterion) ;
        jdbcDao.delete(UserOrganization.class, criterion) ;

        criterion.setOrganizationIds(null) ;
        criterion.setIds(organizationIds) ;
        jdbcDao.delete(Organization.class, criterion) ;
        
    }
    
    private boolean hasChildren(Long organizationId)  {
    	Organization so = new Organization() ;
    	so.setPro_organization_id(organizationId) ;
    	return jdbcDao.getName("systemSQLs:organizationChildrenCount", so, 0L) > 0 ;
    }

    private int lock(Long organizationId) {
    	if (organizationId == null) {
    		return 0;
    	}
    	
    	Organization o = new Organization() ;
    	o.setId(organizationId) ;
    	o.addInclusions("id") ;
    	o.setOperate(Organization.OPERATE_UPDATE_UNVERSION) ;
    	
    	return jdbcDao.update(o) ;
    }
    
    public ArrayPageList<Organization> query(OrganizationSO orgSO){

        if (orgSO == null) {
        	orgSO = new OrganizationSO() ;
        }
        /*
    	ArrayOrderList orderList = organizationCriterion.getOrderList() ;
        if (orderList == null) {
            orderList = new ArrayOrderList() ;
            orderList.addAsc("name_") ;
        }*/

        SessionUser sUser = SessionUser.get() ;
        if (!sUser.getRole_codes().contains(Role.CODE_ADMIN)) {
        	//throw new BusinessException(1001L, CodeHelper.getString("Privilege", "name_", new Long(pid.value()))) ;// 当前用户没有操作权限！
    		//User user = sUser.getUser() ;
    		long[] orgIds = sUser.getOrganization_ids() ;
        	if (orgSO.getLevel_() == null) {
        		orgSO.setPro_organization_id(orgIds[orgIds.length - 1]) ;
        	} else {
        		if (orgSO.getLevel_() < orgIds.length) {
        		    orgSO.setIds(new Long[]{orgIds[orgSO.getLevel_()]}) ;
        		}
        	}
        }
        
        orgSO.addAsc("level_") ;
    	orgSO.addAsc("order_") ;
        orgSO.addAsc("name_") ;
        
        
        return (ArrayPageList<Organization>)jdbcDao.query(orgSO, Organization.class);
    }


    public Organization get(Long id) {  
    	Organization org = new Organization() ;
    	org.setId(id) ;
        return (Organization) jdbcDao.get(org) ;
    }
    

    public Organization get(Long id, boolean isCreateNewWhenNotExist) {  
    	Organization org = new Organization() ;
    	org.setId(id) ;
        org = (Organization) jdbcDao.get(org) ;
        
        if (org == null) {
        	if (isCreateNewWhenNotExist) {
        		org = new Organization() ;
        	} else {
        	    return org ;
        	}
        }

        checkOperationAuth(new long[]{org.getId(), org.getPro_organization_id()}) ;
        
        //org.setPrivileges(getPrivilegeBO().possessedByOrganization(id)) ;
        
        return org;
    }
    
    private void checkOperationAuth(long[] ids) {

        SessionUser sUser = SessionUser.get() ;
        if (!sUser.getRole_codes().contains(Role.CODE_ADMIN)) {
    		long[] orgIds = sUser.getOrganization_ids() ;
    		long orgId = orgIds[orgIds.length - 1];
    		for (long id:ids) {
	        	if (id == orgId) {
	        	    return ;	
	        	}
    		}
        	throw new BusinessException(1001L, CodeHelper.getString("Privilege", "name_", 0L)) ;// 当前用户没有操作权限！
        }
    }
    
    public ArrayPageList<Organization> userBelongTo(Long userId) {
    	
        //String Sql = String.format(OrganizationSO.USER_BELONG_TO_ORGANIZATION, userId) ;
        //return (ArrayPageList<Organization>)jdbcDao.query(new StringBuffer(Sql), null, 1, Integer.MAX_VALUE, null, Organization.class);

    	UserSO so = new UserSO() ;
    	so.setUser_id(userId) ;
    	so.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE) ;
    	return (ArrayPageList<Organization>)jdbcDao.queryName("systemSQLs:user_belong_to_organization", so, Organization.class) ;
    }

    /**
     * 应收金额比率，本级ar_rate-上级ar_rate-commission_rate应该大于8
     */
    private void checkRate(Organization org, Organization proOrg) {
    	if (true) return;
    	
    	Double protectProfits = CodeHelper.getAsDouble("CM.protectProfits", "name_", "Global") ;
    	
    	String sql = "select count(1) " +
    			     "  from sys_organization " +
    			     " where pro_organization_id=? " +
    			     "   and ((ar_rate - ?) < ? or (100 - ? - commission_rate) < ?)" ;
    	Long result = jdbcDao.getLong(sql, new Object[]{org.getId(), org.getAr_rate(), protectProfits, org.getAr_rate(), protectProfits}) ;
    	
    	if (result != null && result.longValue() != 0) {
    		throw new BusinessException(1062L, protectProfits) ;// 下级门店利润未达到保护利润（{0}%）！
    	}
    	
    	if (proOrg == null) {
    		if(org.getPro_organization_id() == null || org.getPro_organization_id().intValue() == 0) {
    			return ;
    		}
    		
    		proOrg = (Organization)jdbcDao.get(Organization.class, org.getPro_organization_id()) ;
    	}

    	if (org.getAr_rate() - proOrg.getAr_rate() < protectProfits
    			|| 100 - proOrg.getAr_rate() - org.getCommission_rate() < protectProfits){
    		throw new BusinessException(1061L, protectProfits) ;// 本门店利润未达到保护利润（{0}%）！
    	}
    }
    
    private PrivilegeBO getPrivilegeBO() {
    	return (PrivilegeBO)CodeHelper.getAppContext().getBean("privilegeBO") ;
    }
}
