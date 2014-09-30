package com.globalwave.system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.globalwave.base.BaseEntity;


@Entity
@Table(name = "sys_privilege")
public class Privilege extends BaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id ;
    private Long pro_privilege_id ;
    private Short code_ ;
    private String name_ ; 
    private String desc_ ;
    private String url_ ;
    @Transient
    private Long organization_id ;
    @Transient
    private Long role_id ;
    @Transient
    private Long user_id ;
    
    
    public Short getCode_() {
        return code_;
    }
    public void setCode_(Short code_) {
        this.code_ = code_;
    }
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
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPro_privilege_id() {
        return pro_privilege_id;
    }
    public void setPro_privilege_id(Long pro_privilege_id) {
        this.pro_privilege_id = pro_privilege_id;
    }
    public String getUrl_() {
        return url_;
    }
    public void setUrl_(String url_) {
        this.url_ = url_;
    }
    public Long getOrganization_id() {
        return organization_id;
    }
    public void setOrganization_id(Long organization_id) {
        this.organization_id = organization_id;
    }
    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
    
}
