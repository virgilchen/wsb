package com.globalwave.system.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;
import com.globalwave.base.annotations.Comparison;


public class OrganizationSO extends BaseSO {
	/*
    public final static String USER_BELONG_TO_ORGANIZATION = 
        "  select org.organization_id organization_id, " +
        //"         org.sys_organization_id sys_organization_id, " +
        "         org.pro_organization_id pro_organization_id, " +
        "         org.name_ name_, " +
        "         org.desc_ desc_, " +
        "         uo.user_id user_id" +
        "    from system_organization org " +
        "         left join system_user_organization uo " +
        "                on org.organization_id = uo.organization_id " +
        "               and uo.user_id='%s' " +
        "order by org.organization_id" ;
    */
    
    @Column(name="organization_id")
    private Long[] organizationIds ;

    @Column(name="pro_organization_id")
    private Long pro_organization_id ;
    
    @Column(name="pro_organization_id")
    @Comparison(operator=Comparison.NE)
    private Long pro_organization_id_exclude ;

    @Column(name="id")
    private Long[] ids ;

    private String name_ ;

    private Long company_id ;

    @Comparison(operator=Comparison.NE)
    private String record_status ;
    
    private Integer level_ ;
    
    public Long[] getOrganizationIds() {
        return organizationIds;
    }
    public void setOrganizationIds(Long[] organizationIds) {
        this.organizationIds = organizationIds;
    } 
    
    public String getName_() {
    	if (name_ == null) {
    		return null;
    	}
    	
    	if (name_.trim().equals("")) {
    		return null ;
    	}
    	
		return name_;
	}
    
    public void setName_(String name_) {
		this.name_ = name_;
	}
	@Override
	public Class<?> getTableClass() {
		return Organization.class;
	}
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	public Long getPro_organization_id_exclude() {
		return pro_organization_id_exclude;
	}
	public void setPro_organization_id_exclude(Long pro_organization_id_exclude) {
		this.pro_organization_id_exclude = pro_organization_id_exclude;
	}
	public String getRecord_status() {
		if (record_status == null) {
			return null ;
		}
		if (record_status.length() == 0) {
			return null ;
		}
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
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
	public Long getPro_organization_id() {
		return pro_organization_id;
	}
	public void setPro_organization_id(Long pro_organization_id) {
		this.pro_organization_id = pro_organization_id;
	}
	
	
}
