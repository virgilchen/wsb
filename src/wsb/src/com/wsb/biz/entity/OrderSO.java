package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class OrderSO extends BaseSO {

    @Column(name="order_id")
    private Long[] ids ;
 
    private Long event_staff_id;
    private Long procs_staff_role_id;
    
    private String order_init_staff_name  ;
    private Long order_init_staff_id;
    private String order_no;
    private String psdo_cust_name  ;
    private String order_cur_status  ;
    
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

	public String getOrder_init_staff_name() {
		return order_init_staff_name;
	}

	public void setOrder_init_staff_name(String order_init_staff_name) {
		this.order_init_staff_name = order_init_staff_name;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getPsdo_cust_name() {
		return psdo_cust_name;
	}

	public void setPsdo_cust_name(String psdo_cust_name) {
		this.psdo_cust_name = psdo_cust_name;
	}

	public String getOrder_cur_status() {
		return order_cur_status;
	}

	public void setOrder_cur_status(String order_cur_status) {
		this.order_cur_status = order_cur_status;
	}
	
	public Long getOrder_init_staff_id() {
		return order_init_staff_id;
	}
	
	public void setOrder_init_staff_id(Long order_init_staff_id) {
		this.order_init_staff_id = order_init_staff_id;
	}

	@Override
	public Class<?> getTableClass() {
		return Order.class;
	}
	
}
