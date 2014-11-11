package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;

public class MemberSO extends BaseSO {
	
	@Column(name="member_id")
    private Long[] ids ;
	private String psdo_cust_id;
	private String car_id;
	private String member_login_id;
	
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	public String getMember_login_id() {
		return member_login_id;
	}
	public void setMember_login_id(String member_login_id) {
		this.member_login_id = member_login_id;
	}
	public String getPsdo_cust_id() {
		return psdo_cust_id;
	}
	public void setPsdo_cust_id(String psdo_cust_id) {
		this.psdo_cust_id = psdo_cust_id;
	}
	public String getCar_id() {
		return car_id;
	}
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	@Override
	public Class<?> getTableClass() {
		return Member.class;
	}

}
