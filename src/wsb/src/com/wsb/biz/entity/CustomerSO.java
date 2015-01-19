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
    
    private String car_framework_no;
    private String procs_step_name;//业务环节
    private String business_name;//业务类型
    private String prod_pack_name;//商品包名
    private String order_prod_pack_expire_date;//到期时间
    private String cust_id;//身份证号
    

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
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	@Override
	public Class<?> getTableClass() {
		return Customer.class;
	}
	
}
