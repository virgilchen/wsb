package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class CustomerSO extends BaseSO {

    @Column(name="psdo_cust_id")
    private Long[] ids ;
    private String cust_name;
    private String cust_code;
    private String cust_phone_no;
    private String member_login_id;
    
    private String car_framework_no;//车架号
    private String procs_step_name;//业务环节
    private String business_name;//业务类型
    private String prod_pack_name;//商品包名
    private String cust_id;//身份证号
    private String car_engine_no;//发动机号
    private String car_no;//车牌号
    
    private String prod_pack_expire_date_begin;//到期时间开始
    private String prod_pack_expire_date_end;//到期时间结束
//    private String prod_pack_expire_date_year;//到期年
//    private String prod_pack_expire_date_month;//到期月
//    private String prod_pack_expire_date_day;//到期日
    
    

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
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getCar_framework_no() {
		return car_framework_no;
	}
	public void setCar_framework_no(String car_framework_no) {
		this.car_framework_no = car_framework_no;
	}
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getProd_pack_name() {
		return prod_pack_name;
	}
	public void setProd_pack_name(String prod_pack_name) {
		this.prod_pack_name = prod_pack_name;
	}
	public String getCar_engine_no() {
		return car_engine_no;
	}
	public void setCar_engine_no(String car_engine_no) {
		this.car_engine_no = car_engine_no;
	}
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	public String getMember_login_id() {
		return member_login_id;
	}
	public void setMember_login_id(String member_login_id) {
		this.member_login_id = member_login_id;
	}
	public String getProd_pack_expire_date_begin() {
		return prod_pack_expire_date_begin;
	}
	public void setProd_pack_expire_date_begin(String prod_pack_expire_date_begin) {
		this.prod_pack_expire_date_begin = prod_pack_expire_date_begin;
	}
	public String getProd_pack_expire_date_end() {
		return prod_pack_expire_date_end;
	}
	public void setProd_pack_expire_date_end(String prod_pack_expire_date_end) {
		this.prod_pack_expire_date_end = prod_pack_expire_date_end;
	}
//	public String getProd_pack_expire_date_year() {
//		return prod_pack_expire_date_year;
//	}
//	public void setProd_pack_expire_date_year(String prod_pack_expire_date_year) {
//		this.prod_pack_expire_date_year = prod_pack_expire_date_year;
//	}
//	public String getProd_pack_expire_date_month() {
//		return prod_pack_expire_date_month;
//	}
//	public void setProd_pack_expire_date_month(String prod_pack_expire_date_month) {
//		this.prod_pack_expire_date_month = prod_pack_expire_date_month;
//	}
//	public String getProd_pack_expire_date_day() {
//		return prod_pack_expire_date_day;
//	}
//	public void setProd_pack_expire_date_day(String prod_pack_expire_date_day) {
//		this.prod_pack_expire_date_day = prod_pack_expire_date_day;
//	}
	@Override
	public Class<?> getTableClass() {
		return Customer.class;
	}
	
}
