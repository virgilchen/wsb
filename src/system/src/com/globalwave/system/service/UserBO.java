package com.globalwave.system.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.C;
import com.globalwave.common.Util;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.system.entity.OrganizationSO;
import com.globalwave.system.entity.Privilege;
import com.globalwave.system.entity.Role;
import com.globalwave.system.entity.RoleSO;
import com.globalwave.system.entity.SessionUser;
import com.globalwave.system.entity.User;
import com.globalwave.system.entity.UserRole;
import com.globalwave.system.entity.UserSO;
import com.globalwave.system.entity.UserOrganization;
import com.globalwave.system.web.annotations.Pid;
import com.globalwave.util.AESEncryptUtil;


@Service("userBO")
@Scope("prototype")
@Transactional
public class UserBO extends BaseServiceImpl {

	public ArrayPageList<User> query(UserSO userSO) {
	    return query(userSO, "queryUsers");
	}
	
	protected ArrayPageList<User> query(UserSO userSO, String sqlName) {
    	
    	if (userSO == null) {
    		userSO = new UserSO() ;
    	}
    	    	
    	userSO.addAsc("name_cn") ;
    	
        SessionUser sUser = SessionUser.get() ;
        if (!(sUser.getRole_codes().contains(Role.CODE_ADMIN) || sUser.getRole_codes().contains(Role.CODE_ORDER_ADMIN))) {
        	userSO.setLimited_organization_id(sUser.getDirect_organization_id()) ;
        }
        //return (ArrayPageList<User>)jdbcDao.query(userSO, User.class);
    	return (ArrayPageList<User>)jdbcDao.queryName("systemSQLs:" + sqlName, userSO, User.class);
    }

    public void checkLoginIdUnified(User user)  {
        User old = new User() ;
        old.setLogin_id(user.getLogin_id()) ;
        
        old = (User)jdbcDao.find(old) ;
        
        if (old != null) {
        	throw new BusinessException(1110L) ;//'1110', '用户登录标识已经被使用，请使用其它登录标识！'
        }
    }

    public void checkUserType(User user)  {
        if (User.TYPE_TERMINAL_AGENT.equals(user.getType_())) {
        	Integer level = CodeHelper.getInteger("Organization", "level_", user.getOrganization_id()) ;
        	if (level != 3) {
        	    throw new BusinessException(1114L) ;//1114', '终端代理的归属代理，应该 是一个中级代理！
        	}
        }
    }

    /**
     * 建立用户信息
     * 
     * @param user
     * @param organization_ids 如果为null，则不建立组织关系
     * @param role_ids 如果为null，则不建立角色关系
     * @return
     * @throws Exception
     */
    public User create(User user, Long[] organization_ids, Long[] role_ids) { 
    	
    	checkLoginIdUnified(user) ;
    	checkOperationAuth(user.getOrganization_id(), role_ids);
    	checkUserType(user);
    	
    	String pwd = user.getPassword_() ;
        user.setPassword_(Util.hash(pwd)) ;
        //if (user.getContact_id() != null) {
        if (this.isCustomerUser(user.getCompany_id())) {
            user.setOld_password_1(AESEncryptUtil.encrypt(pwd)) ;
        }
        
        User newUser = (User) jdbcDao.insert(user) ;
        saveUserOrganization(newUser, organization_ids);
        /*
        if (organization_ids != null) {
            for (Long organization_id : organization_ids) {
                UserOrganization userOrg = new UserOrganization() ;
                userOrg.setUser_id(newUser.getId()) ;
                userOrg.setOrganization_id(organization_id) ;
                jdbcDao.insert(userOrg) ;
            }
        }
        
        if (role_ids != null) {
            for (Long role_id : role_ids) {
                UserRole userRole = new UserRole() ;
                userRole.setUser_id(newUser.getId()) ;
                userRole.setRole_id(role_id) ;
                jdbcDao.insert(userRole) ;
            }
        }
         */

    	saveUserRole(newUser, role_ids) ;
    	
        return newUser;
    }
    
    public User create(User user, Long[] organization_ids, Long[] role_ids, HashMap<String, String> rates) { 
    
    	User result = this.create(user, organization_ids, role_ids);

    	
    	return result ;
    }
    
    /**
     * 是否客户用户
     * 
     * @return
     */
    private boolean isCustomerUser(Long companyId) {
    	
    	if (companyId == null) {
    		return false ;
    	}
    	
    	
    	return false ;
    }

    /**
     * 更新用户，包括组织、角色信息
     * 
     * @param user
     * @param organization_ids
     * @param role_ids
     */
    public void update(User user, Long[] organization_ids, Long[] role_ids) {

    	//checkOperationAuth(user.getOrganization_id(), role_ids);
    	
    	update(user);
    	
        UserOrganization userOrg = new UserOrganization() ;
        userOrg.setUser_id(user.getId()) ;
        
        jdbcDao.delete(UserOrganization.class, userOrg) ;
        jdbcDao.delete(UserRole.class, userOrg) ;
        
        saveUserOrganization(user, organization_ids);
    	saveUserRole(user, role_ids) ;
    }

    public void update(User user, Long[] organization_ids, Long[] role_ids, HashMap<String, String> rates) {
        this.update(user, organization_ids, role_ids);
        
    }

    /**
     * 只更新user的信息，不更权限、角色信息
     * 
     * @param user
     */
    public void update(User user) {

    	checkOperationAuth(user.getOrganization_id(), null) ;
    	checkUserType(user);
    	
        if (StringUtils.isEmpty(user.getPassword_())) {
            user.addExclusions("password_") ;
        } else {
            user.setPassword_(Util.hash(user.getPassword_())) ;
            if (this.isCustomerUser(user.getCompany_id())) {
                user.setOld_password_1(AESEncryptUtil.encrypt(user.getPassword_())) ;
            }
        }

        user.addExclusions("login_id") ;// login_id is very import, can't be changed!
        
        jdbcDao.update(user);
    }


    /**
     * 冻结用户，冻结后，用户不能再登录
     * 
     * @param user
     */
    public void forzen(User user) {
    	user.setStatus_(C.RECORD_STATUS_FORZEN) ;
    	updateStatus(user) ;
    }
    
    /**
     * 激活用户
     * 
     * @param user
     */
    public void active(User user) {
    	user.setStatus_(C.RECORD_STATUS_ACTIVE) ;
    	updateStatus(user) ;
    }
    
    /**
     * 更改用户的状态
     * @param user
     */
    private void updateStatus(User user)  {

        user.addInclusions("status_") ;// login_id is very import, can't be changed!
        
        jdbcDao.update(user);
    }
    
	private void saveUserOrganization(User user, Long[] organization_ids) {
		UserOrganization userOrg;
		if (organization_ids != null) {
            for (Long organization_id : organization_ids) {
                userOrg = new UserOrganization() ;
                userOrg.setUser_id(user.getId()) ;
                userOrg.setOrganization_id(organization_id) ;
                jdbcDao.insert(userOrg) ;
            }
        }
	}
	
	private void saveUserRole(User user, Long[] role_ids) {
        if (role_ids != null) {
            for (Long role_id : role_ids) {
                UserRole userRole = new UserRole() ;
                userRole.setUser_id(user.getId()) ;
                userRole.setRole_id(role_id) ;
                jdbcDao.insert(userRole) ;
            }
        }
	}

    public void changePassword(User user) {

        if (StringUtils.isEmpty(user.getPassword_())) {
        	throw new BusinessException(1108L) ;//'1108', '密码为空，更改密码失败！'
        }
        
        User user2 = (User)jdbcDao.get(SessionUser.get().getUser()) ;
        
        if (!Util.hash(user.getOld_password()).equals(user2.getPassword_())) {
        	throw new BusinessException(1109L) ;      //('1109', '原密码不正确，更改密码失败！' )  	
        }
        
        user.setId(user2.getId()) ;
        String pwd = user.getPassword_();
        user.setPassword_(Util.hash(pwd)) ;
        user.addInclusions("password_") ;
        
        //if (user2.getContact_id() == null || user2.getContact_id() == 0) {
        if (this.isCustomerUser(user2.getCompany_id())) {
            user.setOld_password_1(AESEncryptUtil.encrypt(pwd)) ;
            user.addInclusions("old_password_1") ;
        }
        user.setOperate(User.OPERATE_UPDATE_UNVERSION) ;

        jdbcDao.update(user) ;
        
    }
    public void delete(User user) {

    	

    	
    	// get user just for checkOperationAuth
		this.get(user.getId());
		
        UserOrganization userOrg = new UserOrganization() ;
        userOrg.setUser_id(user.getId()) ;
        jdbcDao.delete(UserOrganization.class,userOrg) ;
        
        UserRole userRole = new UserRole() ;
        userRole.setUser_id(user.getId()) ;
        jdbcDao.delete(UserRole.class, userRole) ;
        
        jdbcDao.delete(user) ;
        
        
    }

    public void deleteNotAgentCheck(Long id) {

		User user = new User() ;
		user.setId(id) ;
		user = (User)jdbcDao.get(user) ;
		
        UserOrganization userOrg = new UserOrganization() ;
        userOrg.setUser_id(user.getId()) ;
        jdbcDao.delete(UserOrganization.class, userOrg) ;
        
        UserRole userRole = new UserRole() ;
        userRole.setUser_id(user.getId()) ;
        jdbcDao.delete(UserRole.class, userRole) ;
        
        
        jdbcDao.delete(user) ;
        
    }

    public void deleteAll(Long[] userIds) {
    	for(Long userId:userIds) {
           this.delete(this.get(userId));
    	}
    	/*
        UserSO criterion = new UserSO() ;
        criterion.setIds(userIds) ;
        //criterion.setType_(User.TYPE_FOREIGNER_AGENT) ;
        Long agentTotal = jdbcDao.getLong("sys_user", "count(1)", criterion, 0L) ;
    	if (agentTotal > 0) {
    		throw new BusinessException(1112L) ;//1112', '当前要删除的用户中包括海外联系人，这些用户，请通过联系人方式删除！
    	}

    	
    	for(Long userId:userIds) {
        	// get user just for checkOperationAuth
    		this.get(userId);
    		
            getProfitRateBO().deleteSalesRate(userId);
    	}
    	
        UserSO criterion = new UserSO() ;
        criterion.setUserIds(userIds) ;
        
        // delete cascade
        jdbcDao.delete(UserOrganization.class, criterion) ;
        
        // delete cascade
        jdbcDao.delete(UserRole.class, criterion) ;

        criterion.setUserIds(null) ;
        criterion.setIds(userIds) ;
        jdbcDao.delete(User.class, criterion) ;
        */
        
    }

    public User get(Long id) {
    	User result = get(id, false, false) ;
    	
    	checkOperationAuth(result.getOrganization_id(), null);
    	
    	return result ;
    }
    
    
    public User get(Long id, boolean isCreateNewWhenNotExist, boolean withDetail) {  
    	User user = new User() ;
    	user.setId(id) ;
        user = (User) jdbcDao.get(user) ;
        
        if (user == null) {
        	if (isCreateNewWhenNotExist) {
        		user = new User() ;
        	} else {
        	    return user ;
        	}
        }
        
		if (withDetail) {
			OrganizationSO orgSo = new OrganizationSO();
			orgSo.setRecord_status(C.RECORD_STATUS_ACTIVE);
			user.setOrganizations(getOrganizationBO().query(orgSo));
			
			Long limitedUserid = null ;
	        SessionUser sUser = SessionUser.get() ;
	        if (!sUser.getRole_codes().contains(Role.CODE_ADMIN)) {
	        	limitedUserid = sUser.getUser().getId();
	        }
			user.setRoles(getRoleBO().userBelongTo(id, limitedUserid));
		}
		
        return user;
    }

    public User find(Long user_id) {
        User user = new User() ;
        user.setId(user_id) ;
        
        return (User)jdbcDao.get(user) ;
    }

    public User findByLoginId(String login_id) throws Exception {
        User user = new User() ;
        user.setLogin_id(login_id) ;

        return (User)jdbcDao.find(user) ;
    }

    /**
     * 转向登录
     * 
     * @param user
     * @return
     * @throws Exception
     */
    public SessionUser loginForward(User user) throws Exception {
    	String loginId = user.getLogin_id() ;

    	SessionUser currentLogined = SessionUser.get() ;
    	String isEnabled = CodeHelper.getString("LOGIN.FORWARD.ENABLED", "name_", currentLogined.getLogin_id()) ;
    	
    	if (isEnabled == null) {
    		throw new BusinessException(1001L, "LOGIN.FORWARD") ;// 当前用户没有操作权限！
    	}
    	
    	user.setLogin_id(loginId.split("@")[1]) ;// sysadm
    	
    	if (loginId.equals(user.getLogin_id())) {
    		throw new BusinessException(1001L, "LOGIN.FORWARD[SAME_USER]") ;// 当前用户没有操作权限！
    	}
    	
    	SessionUser superUser = this.login(user, false/*isPosLogin*/, true/*checkPassword*/) ;

    	if (!superUser.hasPrivilege(Pid.LOGIN_FORWARD)) {
    		throw new BusinessException(1001L, Pid.LOGIN_FORWARD) ;// 当前用户没有操作权限！
    	}
    	
    	user.setLogin_id(loginId.split("@")[0]) ;
    	return this.login(user, false/*isPosLogin*/, false/*checkPassword*/) ;
    }

    /**
     * 
     * @param user
     * @param isPosLogin
     * @return
     * @throws Exception
     */
    public SessionUser login(User user, boolean isPosLogin) throws Exception {
    	return this.login(user, isPosLogin, true) ;
    }
    
    private SessionUser login(User user, boolean isPosLogin, boolean checkPassword) throws Exception {

    	String loginId = user.getLogin_id() ;
    	String password = user.getPassword_() ;
    	
        if (StringUtils.isBlank(loginId)){
        	throw new BusinessException(1L, CodeHelper.getMessage(1199L/*"loginId"*/)) ;// required
        }
        if(checkPassword && StringUtils.isBlank(password)) {
        	throw new BusinessException(1L, CodeHelper.getMessage(1198L/*"password"*/)) ;// required
        }

        User userResult = this.findByLoginId(user.getLogin_id());
        
        if (userResult == null) {
        	throw new BusinessException(1101L) ;//('1101', '用户不存在！' );
        } 
        
        if (C.RECORD_STATUS_FORZEN.equals(userResult.getStatus_())) {
        	throw new BusinessException(1111L) ;//1111', '用户被冻结，不能进行任何操作！
        }
        
        if (!C.RECORD_STATUS_ACTIVE.equals(userResult.getStatus_())) {
        	throw new BusinessException(1112L) ;//1112', '用户非激活状态，不能登录！
        }
        
        SessionUser sessionUser = new SessionUser() ;
        sessionUser.setUser(userResult) ;
        
        /*
        if (this.getSysConfig().isUseSafeLogin()) {
            if (this.getLoginErrorService().isCanNotLogin(ui.getId())) {
                this.addActionError(this.getText("error.login.times"));
                return INPUT;
            }
        }*/

        if (checkPassword && !Util.hash(password).equals(userResult.getPassword_())) { 
        	
        	throw new BusinessException(1104L) ;//('1104', '用户密码不正确！' );
        }
        userResult.setPassword_(null) ;
        
        ArrayPageList<Privilege> privileges =
        	getPrivilegeService().getAllByUserId(userResult.getId()) ;
        
        Set<Short> privilegeIds = new HashSet<Short>(privileges.size()) ;
        for (int i = privileges.size() - 1 ; i >= 0 ; i --) {
            privilegeIds.add(privileges.get(i).getCode_()) ;
        }
        sessionUser.setPrivilege_ids(privilegeIds) ;
        sessionUser.setPrivileges(privileges) ;
        
        
        ArrayPageList<Role> roles = getRoleBO().userHasRoles(userResult.getId()) ;
        Set<String> role_codes = new HashSet<String>() ;
        sessionUser.setRole_codes(role_codes) ;
        for (Role role: roles) {
        	role_codes.add(role.getCode_()) ;
        }
        
        /*
        if (userResult.getCompany_id().longValue() == 1l) {
        	sessionUser.setComany_type(new Integer(0));
        }
        else {

        }*/
        
    	sessionUser.setOrganization_ids(getUserOrganizationIds(userResult));
        
        //sessionUser.setMarks(this.getMarkItBO().queryByUserId(userResult.getId())) ;
    	return sessionUser ;
    }

    public long[] getUserOrganizationIds(User user) {
    	long[] orgIds = null;
        Long org_id = user.getOrganization_id() ;
        if (org_id != null) {
        	Integer level = CodeHelper.getInteger("Organization", "level_", org_id) ;
        	
        	if (level == null) {
        		throw new BusinessException(1055L);// 1055', '终端不存在，请与管理员联系！
        	}
        	
        	orgIds = new long[level + 1] ;
        	orgIds[level] = org_id ;
        			
        	for (; level > 0 ; level --) {
        		orgIds[level - 1] = CodeHelper.getLong("Organization", "pro_organization_id", orgIds[level]) ;
        	}
        }
        
        return orgIds;
    }

    public ArrayPageList<User> queryUserByRole(String roleCode, String roleName) {
    	return this.queryUserByRole(new String[]{roleCode}, roleName) ;
    }
    
    public ArrayPageList<User> queryUserByRole(String[] roleCodes, String roleName) {
    	
    	RoleSO so = new RoleSO() ;
    	so.setCodes(roleCodes) ;
    	so.setName_(roleName) ;
    	
    	so.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE) ;
    	return (ArrayPageList<User>)jdbcDao.queryName("systemSQLs:queryUserByRole", so, User.class) ;
    }
    
    private void checkOperationAuth(Long userOrgId, Long[] role_ids) {

        SessionUser sUser = SessionUser.get() ;
        if (!(sUser.getRole_codes().contains(Role.CODE_ADMIN) || sUser.getRole_codes().contains(Role.CODE_ORDER_ADMIN))) {
        	Long proUserOrgId = CodeHelper.getLong("Organization", "pro_organization_id", userOrgId) ;
        	Long userDirectOrgId = sUser.getDirect_organization_id() ;
    		if (userDirectOrgId == null || 
    				!userDirectOrgId.equals(userOrgId) && !userDirectOrgId.equals(proUserOrgId)) {
        	    throw new BusinessException(1001L, CodeHelper.getString("Privilege", "name_", 0L)) ;// 当前用户没有操作权限！
    		}
    		
    		
    		if (!sUser.getRole_codes().contains(Role.CODE_ORG_ADMIN)) {
    			throw new BusinessException(1001L, CodeHelper.getString("Privilege", "name_", 0L)) ;// 当前用户没有操作权限！
    		}
    		
    		if (role_ids != null) {
    			UserSO so = new UserSO() ;
    			so.setUser_id(sUser.getUser().getId()) ;
    			so.setRole_ids(role_ids);
    			so.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE);
    			
    			ArrayPageList<Role> roles = (ArrayPageList<Role>)jdbcDao.queryName("systemSQLs:queryUserRolesExcluded", so, Role.class);
    			/*
    			if (jdbcDao.getName("systemSQLs:countUserRolesExcluded", so, 0L) > 0L) {
    				 //throw new BusinessException(1001L, CodeHelper.getString("Privilege", "name_", 0L)) ;// 当前用户没有操作权限！
    			}*/
    			for (Role role:roles) {
    				if (Role.CODE_CUSTOMER.equals(role.getCode_())){
    					continue;
    				}
    				throw new BusinessException(1115L, role.getName_()) ;//'1115', '用户角色中，不含【{0}】，未能授予角色！'
    			}
    		}
        }
    }

   

    private PrivilegeBO getPrivilegeService() {
    	return (PrivilegeBO)CodeHelper.getAppContext().getBean("privilegeBO") ;
    }

    private RoleBO getRoleBO() {
    	return (RoleBO)CodeHelper.getAppContext().getBean("roleBO") ;
    }
    private OrganizationBO getOrganizationBO() {
    	return (OrganizationBO)CodeHelper.getAppContext().getBean("organizationBO") ;
    }

    
    
}
