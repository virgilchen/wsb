package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;

public class CarSO extends BaseSO {
	@Column(name="car_id")
    private Long[] ids ;
	private Long psdo_cust_id;

	public Long[] getIds() {
		return ids;
	}
	
	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Long getPsdo_cust_id() {
		return psdo_cust_id;
	}

	public void setPsdo_cust_id(Long psdo_cust_id) {
		this.psdo_cust_id = psdo_cust_id;
	}

	@Override
	public Class<?> getTableClass() {
		return Car.class;
	}
}
