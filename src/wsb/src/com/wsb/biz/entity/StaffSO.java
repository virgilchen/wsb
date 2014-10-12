package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;

public class StaffSO extends BaseSO {
	@Column(name="staff_id")
	private Long[] ids ;
	
	private String staff_status;
	private String staff_login_profile;
	private String staff_name;
	private Long staff_role_id;
	
	
	public Long[] getIds() {
		return ids;
	}
	
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
	public String getStaff_status() {
		return staff_status;
	}

	public void setStaff_status(String staff_status) {
		this.staff_status = staff_status;
	}

	public String getStaff_login_profile() {
		return staff_login_profile;
	}

	public void setStaff_login_profile(String staff_login_profile) {
		this.staff_login_profile = staff_login_profile;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public Long getStaff_role_id() {
		return staff_role_id;
	}

	public void setStaff_role_id(Long staff_role_id) {
		this.staff_role_id = staff_role_id;
	}

	@Override
	public Class<?> getTableClass() {
		return Staff.class;
	}
}
