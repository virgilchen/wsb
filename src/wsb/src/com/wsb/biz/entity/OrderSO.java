package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class OrderSO extends BaseSO {

    @Column(name="order_id")
    private Long[] ids ;
 
    private Long event_staff_id;
    private Long procs_staff_role_id;
    
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
	
	public Long getProcs_staff_role_id() {
		return procs_staff_role_id;
	}

	public void setProcs_staff_role_id(Long procs_staff_role_id) {
		this.procs_staff_role_id = procs_staff_role_id;
	}

	@Override
	public Class<?> getTableClass() {
		return Order.class;
	}
	
}
