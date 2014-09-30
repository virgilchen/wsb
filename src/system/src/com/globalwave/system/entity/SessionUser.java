package com.globalwave.system.entity;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.globalwave.common.ArrayPageList;
import com.globalwave.common.Convertor;
import com.globalwave.common.U;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.util.TimerTaskUtil;

public class SessionUser {

    final public static String SESSION_PK = "USER_SESSION_ID_07611239" ;
    final public static boolean IS_VALIDE_PRIVILEGE = true ;
    
    private User user ;
    private long[] organization_ids ;
    private Set<Short> privilege_ids ;
    private Set<String> role_codes ;
    
    private ArrayPageList<Privilege> privileges ;

	private Integer sale_date ;
    private String terminal_code ;
    private Integer comany_type;
    private String qq_account ;
    
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
    
    //是否是店铺管理员
    public boolean isSalesAdmin() {
    	return role_codes != null && role_codes.contains("ORG-AD");
    }
    
    //是否是店员
    public boolean isSales() {
    	return (role_codes != null && role_codes.contains("SALES")) || isSalesAdmin();
    }
    
    //是否是省级店员
    public boolean isProvinceSales() {
    	return isSales() && (CodeHelper.getInteger("Organization", "level_", user.getOrganization_id()).intValue() == 1);
    }
    
    //是否是市级店员
    public boolean isCitySales() {
    	return isSales() && (CodeHelper.getInteger("Organization", "level_", user.getOrganization_id()).intValue() == 2);
    }
    
    //是否是区级店员
    public boolean isAreaSales() {
    	return isSales() && (CodeHelper.getInteger("Organization", "level_", user.getOrganization_id()).intValue() == 3);
    }
    
    //是否是终端代理
    public boolean isAgentSales() {
    	return role_codes != null && role_codes.contains("Teminal");
    }
    
    //是否是系统用户
//    public boolean isOperator() {
//    	return User.TYPE_OTHER.equals(user.getType_());
//    }
    
    //是否是订单管理员
    public boolean isOrderAdmin() {
    	return role_codes != null && role_codes.contains("ORDER");
    }
    
    /*
    //获取下属用户id
    public Set<Long> getUnderlings() {
    	if (underLines != null) {
    		return underLines;
    	}
    	Set<Long> result = new HashSet<Long>() ;
    	Set<Long> ids = new HashSet<Long>() ;
    	ids.add(this.user.getId()) ;
    	List<Organization> orgs = (List<Organization>)CodeHelper.query("Organization", "leader_id", ids, Organization.class) ;
    	if (orgs == null || orgs.size() == 0) {
    		return result ;
    	}
    	
    	orgs.addAll(this.subOrganization(orgs)) ;
    	
    	for (Organization org:orgs) {
    		ids.clear() ;
    		ids.add(org.getId()) ;
    		List<User> users = (List<User>)CodeHelper.query("Sales", "organization_id", ids, User.class) ;
    		for (User user:users) {
    			result.add(user.getId()) ;
    		}
    	}
    	
    	underLines = result;
		return underLines;
	}

    private List<Organization> subOrganization(List<Organization> orgs) {
    	List<Organization> result = new ArrayList<Organization>() ;
    	for (Organization org:orgs) {
    		result.addAll(this.subOrganization(org.getId())) ;
    	}
    	return result ;
    }

    private List<Organization> subOrganization(Long proId) {
    	Set<Long> ids = new HashSet<Long>() ;
    	ids.add(proId) ;
    	List<Organization> orgs = (List<Organization>)CodeHelper.query("Organization", "pro_organization_id", ids, Organization.class) ;

    	for (int i = orgs.size() - 1 ; i >= 0 ; i --) {
    		Organization org = orgs.get(i) ;
    		if (proId.equals(org.getId())) {
    			orgs.remove(i) ;
    		} else {
    			if (orgs.size() < 20) {// 防止死循环    				
    				orgs.addAll(this.subOrganization(org.getId())) ;
    			}
    		}
    	}
    	
    	return orgs ;
    }*/
    
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
   
	public Integer getSale_date() {
		return sale_date;
	}
	public void setSale_date(Date sale_date) {
		this.sale_date = Long.valueOf(U.date2int(sale_date)/1000000).intValue();
	}
	public String getTerminal_code() {
		return terminal_code;
	}
	public void setTerminal_code(String terminal_code) {
		this.terminal_code = terminal_code;
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

        	Convertor.addProperty(root, "sale_date", sale_date == null?"":sale_date) ;
        	Convertor.addProperty(root, "terminal_code", terminal_code == null?"":terminal_code) ;
        	
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
	
	public Integer getComany_type() {
		return comany_type;
	}
	public void setComany_type(Integer comany_type) {
		this.comany_type = comany_type;
	}
	public String getQq_account() {
		return qq_account;
	}
	public void setQq_account(String qq_account) {
		this.qq_account = qq_account;
	}
    
}
