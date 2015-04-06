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
    private String cust_phone_no ;
    private Long psdo_cust_id  ;
    private String order_cur_status  ;
    private String[] order_cur_status_nq  ;
    private String event_staff_name ;
    private String business_name ;
    private String order_type  ;
    private String order_by ;
    
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
		if ("".equals(this.order_cur_status)) return null;
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
    public Long getPsdo_cust_id() {
		return psdo_cust_id;
	}
    public void setPsdo_cust_id(Long psdo_cust_id) {
		this.psdo_cust_id = psdo_cust_id;
	}
	public String getOrder_by() {
		if (order_by == null) {
			return " o.order_id " ;
		}
		return order_by;
	}
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}

	public String getOrder_type() {
		if ("".equals(this.order_type)) return null;
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getEvent_staff_name() {
		return event_staff_name;
	}

	public void setEvent_staff_name(String event_staff_name) {
		this.event_staff_name = event_staff_name;
	}

	public String getBusiness_name() {
		return business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	public String getCust_phone_no() {
		return cust_phone_no;
	}

	public void setCust_phone_no(String cust_phone_no) {
		this.cust_phone_no = cust_phone_no;
	}
	public String[] getOrder_cur_status_nq() {
		return order_cur_status_nq;
	}
	
	public void setOrder_cur_status_nq(String[] order_cur_status_nq) {
		this.order_cur_status_nq = order_cur_status_nq;
	}

	@Override
	public Class<?> getTableClass() {
		return Order.class;
	}
	
}
