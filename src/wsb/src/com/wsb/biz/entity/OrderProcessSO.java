package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class OrderProcessSO extends BaseSO {

    @Column(name="business_id")
    private Long business_id ;
    
	public Long getBusiness_id() {
		return business_id;
	}
	
	public void setBusiness_id(Long business_id) {
		this.business_id = business_id;
	}
	
	@Override
	public Class<?> getTableClass() {
		return OrderProcess.class;
	}
	
}
