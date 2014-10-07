package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class OrderSO extends BaseSO {

    @Column(name="order_id")
    private Long[] ids ;
 
    private Long event_staff_id;
    
	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Long getEvent_staff_id() {
		return event_staff_id;
	}

	public void setEvent_staff_id(Long event_staff_id) {
		this.event_staff_id = event_staff_id;
	}

	@Override
	public Class<?> getTableClass() {
		return Order.class;
	}
	
}
