package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;

public class CompanyOrgSO extends BaseSO {
	
	@Column(name="org_id")
	private Long org_id;
	private Long[] org_ids;

	public Long getOrg_id() {
		return org_id;
	}

	public Long[] getOrg_ids() {
		return org_ids;
	}

	public void setOrg_ids(Long[] org_ids) {
		this.org_ids = org_ids;
	}

	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}
	
	@Override
	public Class<?> getTableClass() {
		return CompanyOrg.class;
	}
}