package com.wsb.biz.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;

/**
 * 
 * @author Toni
 *
 */
@Entity
@Table(name = "company_org_rt")
public class CompanyOrg extends BaseEntity {
	
	//@Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    //@Column(name="org_id")
	
	private Long org_id;
	private Long org_id_upper;
	private Long org_level;
	private String org_name;
	private Long org_owner_staff_id;
	private String org_province;
	private String org_city;
	private Long org_owner_phone_no;
	private String org_remark;
	public Long getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}
	public Long getOrg_id_upper() {
		return org_id_upper;
	}
	public void setOrg_id_upper(Long org_id_upper) {
		this.org_id_upper = org_id_upper;
	}
	public Long getOrg_level() {
		return org_level;
	}
	public void setOrg_level(Long org_level) {
		this.org_level = org_level;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public Long getOrg_owner_staff_id() {
		return org_owner_staff_id;
	}
	public void setOrg_owner_staff_id(Long org_owner_staff_id) {
		this.org_owner_staff_id = org_owner_staff_id;
	}
	public String getOrg_province() {
		return org_province;
	}
	public void setOrg_province(String org_province) {
		this.org_province = org_province;
	}
	public String getOrg_city() {
		return org_city;
	}
	public void setOrg_city(String org_city) {
		this.org_city = org_city;
	}
	public Long getOrg_owner_phone_no() {
		return org_owner_phone_no;
	}
	public void setOrg_owner_phone_no(Long org_owner_phone_no) {
		this.org_owner_phone_no = org_owner_phone_no;
	}
	public String getOrg_remark() {
		return org_remark;
	}
	public void setOrg_remark(String org_remark) {
		this.org_remark = org_remark;
	}
	
	
}