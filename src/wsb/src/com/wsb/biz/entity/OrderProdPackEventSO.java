package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class OrderProdPackEventSO extends BaseSO {

    @Column(name="order_prod_pack_event_id")
    private Long[] ids ;
 
	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	@Override
	public Class<?> getTableClass() {
		return OrderProdPackEvent.class;
	}
	
}
