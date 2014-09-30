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
@Table(name = "sys_organization")
@Versionable
public class Organization extends BaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="increment")
    private Long id ;
    @Transient
    private Long sys_organization_id ;
    private Long pro_organization_id ;
    private Long sponsor_id;
    private Long company_id ;
    private String site_address;
    private String contact_name;
    private String contact_mobile;
    private String name_ ;
    private String code_ ;
    private String desc_ ;
    private Long leader_id ;
    
    private Integer level_ ;
    private Integer order_ ;

    private Double ar_rate ;
    private Double commission_rate ;
    
    private String record_status ;
    
    
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
    public Long getPro_organization_id() {
        return pro_organization_id;
    }
    public void setPro_organization_id(Long pro_organization_id) {
        this.pro_organization_id = pro_organization_id;
    }
    public Long getSys_organization_id() {
        return sys_organization_id;
    }
    public void setSys_organization_id(Long sys_organization_id) {
        this.sys_organization_id = sys_organization_id;
    }
    public Long getUser_id() {
        return user_id;
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
	public Long getLeader_id() {
		return leader_id;
	}
	public void setLeader_id(Long leader_id) {
		this.leader_id = leader_id;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
    public String getCode_() {
		return code_;
	}
    public void setCode_(String code_) {
		this.code_ = code_;
	}
	public Long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
	public Integer getLevel_() {
		return level_;
	}
	public void setLevel_(Integer level_) {
		this.level_ = level_;
	}
	public Integer getOrder_() {
		return order_;
	}
	public void setOrder_(Integer order_) {
		this.order_ = order_;
	}
	public Double getAr_rate() {
		return ar_rate;
	}
	public void setAr_rate(Double ar_rate) {
		this.ar_rate = ar_rate;
	}
	public Double getCommission_rate() {
		return commission_rate;
	}
	public void setCommission_rate(Double commission_rate) {
		this.commission_rate = commission_rate;
	}
	public String getSite_address() {
		return site_address;
	}
	public void setSite_address(String site_address) {
		this.site_address = site_address;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getContact_mobile() {
		return contact_mobile;
	}
	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}
	public Long getSponsor_id() {
		return sponsor_id;
	}
	public void setSponsor_id(Long sponsor_id) {
		this.sponsor_id = sponsor_id;
	}
	
	
}
