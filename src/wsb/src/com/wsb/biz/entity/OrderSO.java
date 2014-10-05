package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class OrderSO extends BaseSO {

    @Column(name="order_id")
    private Long[] ids ;
 
	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}


	@Override
	public Class<?> getTableClass() {
		return Order.class;
	}
	
}
