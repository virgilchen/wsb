package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;

public class RecommendationEngineSO extends BaseSO {
	
	@Column(name="recmdt_engine_id")
    private Long[] ids ;
	private Long recmdt_psdo_cust_id;
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	public Long getRecmdt_psdo_cust_id() {
		return recmdt_psdo_cust_id;
	}
	public void setRecmdt_psdo_cust_id(Long recmdt_psdo_cust_id) {
		this.recmdt_psdo_cust_id = recmdt_psdo_cust_id;
	}
	
	@Override
	public Class<?> getTableClass() {
		return RecommendationEngine.class;
	}

}
