package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class CustomerSO extends BaseSO {

    @Column(name="psdo_cust_id")
    private Long[] ids ;
    private String cust_name;
    private String cust_code;
    private String cust_phone_no;
    private String member_id;

	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getCust_code() {
		return cust_code;
	}
	public void setCust_code(String cust_code) {
		this.cust_code = cust_code;
	}
	public String getCust_phone_no() {
		return cust_phone_no;
	}
	public void setCust_phone_no(String cust_phone_no) {
		this.cust_phone_no = cust_phone_no;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	@Override
	public Class<?> getTableClass() {
		return Customer.class;
	}
	
}
