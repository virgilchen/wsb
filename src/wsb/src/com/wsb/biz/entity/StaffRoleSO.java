package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class StaffRoleSO extends BaseSO {
	
	@Column(name="staff_role_id")
    private Long[] ids ;
	
	private String staff_role_name;
	private String staff_role_status;
	
	
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String getStaff_role_name() {
		return staff_role_name;
	}
	public void setStaff_role_name(String staff_role_name) {
		this.staff_role_name = staff_role_name;
	}
	public String getStaff_role_status() {
		return staff_role_status;
	}
	public void setStaff_role_status(String staff_role_status) {
		this.staff_role_status = staff_role_status;
	}
	
	
	@Override
	public Class<?> getTableClass() {
		return StaffRole.class;
	}
	
}