package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class BusinessSO extends BaseSO {

    @Column(name="business_id")
    private Long[] ids ;

    private String business_name  ;

    
	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}


	public String getBusiness_name() {
		return business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	@Override
	public Class<?> getTableClass() {
		return Business.class;
	}
	
}
