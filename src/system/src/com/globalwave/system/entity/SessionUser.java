package com.globalwave.system.entity;

import java.util.Locale;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.globalwave.common.ArrayPageList;
import com.globalwave.common.Convertor;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.Staff;

public class SessionUser {

    final public static String SESSION_PK = "USER_SESSION_ID_07611239" ;
    final public static boolean IS_VALIDE_PRIVILEGE = true ;
    
    private User user ;
    private Staff staff ;
    private long[] organization_ids ;
    private Set<Short> privilege_ids ;
    private Set<String> role_codes ;
    
    private ArrayPageList<Privilege> privileges ;

    
    private Locale locale ;
    
    private int versionId = 0 ;
    
    private static ThreadLocal<SessionUser> tl=new ThreadLocal<SessionUser> ();//私有静态变量 

    private static ThreadLocal<Locale> t2=new ThreadLocal<Locale> ();
    
    
    public static SessionUser get(){
    	SessionUser su = tl.get() ;
    	if(su == null) {
    		throw new BusinessException(1000L) ;// 未登陆或已经超时，请重新登陆！
    	}
        return su;
    }
    
    //判断用户是否已登录
    public static boolean isLogon() {
    	SessionUser su = tl.get() ;
    	if (su == null) {
    		return false;
    	}
    	return true;
    }
    
    public static void set(SessionUser user){
        tl.set(user);
    }
    
    public static void remove(){
    	tl.remove() ;
    }

    public static Locale getLocale(){

    	SessionUser su = tl.get() ;
    	if (su == null) {
    	    return t2.get() ;
    	} else {
    		return su.locale ;
    	}
    }

    public static void setLocale(Locale locale){
    	SessionUser su = tl.get() ;
    	
    	if (su != null) {
    		su.locale = locale ;
    	}
    	
    	t2.set(locale) ;
    }
    
    public static void removeLocale(){
    	t2.remove();
    }

    public void setUserLocale(Locale locale){
    	this.locale = locale ;
    }

    public Locale getUserLocale(){
    	return this.locale ;
    }

    //是否是系统管理员
    public boolean isAdmin() {
    	return role_codes != null && role_codes.contains("ADMIN");
    }
    
    //是否是客户
    public boolean isCustomer() {
    	return role_codes != null && role_codes.contains("CUSTOMER");
    }

    public boolean isManager() {
    	Long roleId = staff.getStaff_role_id();
    	return roleId == 1 || roleId == 10 || roleId == 11; //系统管理员/总经理/业务经理
    }
    
    public long[] getOrganization_ids() {
        return organization_ids;
    }
    public void setOrganization_ids(long[] organization_ids) {
        this.organization_ids = organization_ids;
    }
    
    /**
     * 直属组织ID
     * 
     * @return
     */
    public long getDirect_organization_id() {
    	if (organization_ids == null) {
    		return 0L;
    	}
    	return organization_ids[organization_ids.length - 1];
    }
    public Long getPro_organization_id() {
    	if (organization_ids == null) {
    		return 0L;
    	}
    	if (organization_ids.length > 1) {
    		return organization_ids[organization_ids.length - 2];
    	}
    	return 0L;
    }
    public Set<Short> getPrivilege_ids() {
        return privilege_ids;
    }
    public void setPrivilege_ids(Set<Short> privilege_ids) {
        this.privilege_ids = privilege_ids;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
	
	public String getShop_code() {
		return getUser().getShop_code();
	}
	
	public String getLogin_id() {
   	    return getUser().getLogin_id() ;  
    }
   public void setStaff(Staff staff) {
		this.staff = staff;
	}
	   public Staff getStaff() {
		return staff;
	}

    public ArrayPageList<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(ArrayPageList<Privilege> privileges) {
		this.privileges = privileges;
	}
	public Set<String> getRole_codes() {
		return role_codes;
	}
	public void setRole_codes(Set<String> role_codes) {
		this.role_codes = role_codes;
	}
	
	public String asXML() {
        
        return toXML().asXML() ;
    }

    public Element toXML() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root") ;
                
        try {
        	root.add(Convertor.object2Xml(user)) ;

        	
            root.add(Convertor.list2Xml(privilege_ids,"privilege_ids")) ;
            //root.add(Convertor.list2Xml(organization_ids,"organization_ids")) ;
        } catch (Exception e) {
            root.addElement("message").addText("Conver array page list to xml error!") ;
        }
        
        return root ;
    }
    
    
    public boolean hasPrivilege(short pid) {
        if (IS_VALIDE_PRIVILEGE) {
            return this.privilege_ids.contains(pid) ;            
        } else {
            return true ;
        }
    }
    
    /**
     * 只需要含其中一个，即为有权限
     * 
     * @param pids
     * @return
     */
    public boolean hasPrivilege(short[] pids) {
        for (short pid : pids){
            if (hasPrivilege(pid)) {
            	return true ;
            } 
        }
        
        return false;
    }
    
    //是否可购买商品
    public static boolean canBuy() {
    	SessionUser su = tl.get() ;
    	if (su == null) {
    		throw new BusinessException(1000L) ;
    	}
    	if (!su.hasPrivilege(Short.valueOf("5001").shortValue())) {
    		throw new BusinessException(2001L) ;
    	}

    	return true;
    }
    
	public int getVersionId() {
		return versionId;
	}
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}
	
    
}
