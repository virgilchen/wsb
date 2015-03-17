package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class RecmdtSO extends BaseSO {
	
	@Column(name="recmdt_id")
    private Long[] ids ;
	
	private String recmdt_name;
	private String recmdt_status;
	
	
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	public String getRecmdt_name() {
		return recmdt_name;
	}
	public void setRecmdt_name(String recmdt_name) {
		this.recmdt_name = recmdt_name;
	}
	public String getRecmdt_status() {
		return recmdt_status;
	}
	public void setRecmdt_status(String recmdt_status) {
		this.recmdt_status = recmdt_status;
	}
	
	
	@Override
	public Class<?> getTableClass() {
		return Recmdt.class;
	}
	
	
}