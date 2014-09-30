package com.globalwave.system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.globalwave.base.BaseEntity;
import com.globalwave.base.annotations.Versionable;
import com.globalwave.common.ArrayPageList;

@Entity
@Table(name = "sys_role")
@Versionable
public class Role extends BaseEntity {
	

	final public static String CODE_ADMIN="ADMIN" ;
	final public static String CODE_ORG_ADMIN="ORG-AD" ;
	final public static String CODE_ORDER_ADMIN="ORDER" ;
	final public static String CODE_CUSTOMER="CUSTOMER" ;
	final public static String CODE_FINANCIAL_ANALYSIS="FA" ;
	 
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id ;
    private Long pro_role_id ;
    private String code_ ;
    private String name_ ;
    private String desc_ ;
    private String record_status ;
    
    /**
     * 查询用户所拥有的角色时，非空，表示，user拥有本角色
     */
    @Transient
    private Long user_id ;
    
    @Transient
    private ArrayPageList<Privilege> privileges ;
    
    public String getDesc_() {
        return desc_;
    }
    public void setDesc_(String desc_) {
        this.desc_ = desc_;
    }
    public String getName_() {
        return name_;
    }
    public void setName_(String name_) {
        this.name_ = name_;
    }
    public Long getUser_id() {
        return user_id;
    }
    
    public Long getPro_role_id() {
		return pro_role_id;
	}
	public void setPro_role_id(Long pro_role_id) {
		this.pro_role_id = pro_role_id;
	}
	public String getCode_() {
		return code_;
	}
	public void setCode_(String code_) {
		this.code_ = code_;
	}
	public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ArrayPageList<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(ArrayPageList<Privilege> privileges) {
		this.privileges = privileges;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
    
}
