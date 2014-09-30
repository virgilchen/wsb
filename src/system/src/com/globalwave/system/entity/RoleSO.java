package com.globalwave.system.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class RoleSO extends BaseSO {
    
    @Column(name="role_id")
    private Long[] roleIds ;

    @Column(name="id")
    private Long[] ids ;

    private String name_ ;

    private String code_ ;
    
    @Column(name="code_")
    private String[] codes ;

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
		return Role.class;
	}
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public String[] getCodes() {
		return codes;
	}

	public void setCodes(String[] codes) {
		this.codes = codes;
	}

	public String getCode_() {
    	if (code_ == null) {
    		return null;
    	}
    	
    	if (code_.trim().equals("")) {
    		return null ;
    	}
		return code_;
	}

	public void setCode_(String code_) {
		this.code_ = code_;
	}
}
