package com.wsb.biz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "staff_role_demo_rt")
public class StaffRole extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="staff_role_id")
	private Long id;
	
	private String staff_role_page_id;
	private String staff_role_status;
	private String staff_role_name;
	private String staff_role_remark;
	private Long staff_role_org_id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStaff_role_page_id() {
		return staff_role_page_id;
	}
	public void setStaff_role_page_id(String staff_role_page_id) {
		this.staff_role_page_id = staff_role_page_id;
	}
	public String getStaff_role_status() {
		return staff_role_status;
	}
	public void setStaff_role_status(String staff_role_status) {
		this.staff_role_status = staff_role_status;
	}
	public String getStaff_role_name() {
		return staff_role_name;
	}
	public void setStaff_role_name(String staff_role_name) {
		this.staff_role_name = staff_role_name;
	}
	public String getStaff_role_remark() {
		return staff_role_remark;
	}
	public void setStaff_role_remark(String staff_role_remark) {
		this.staff_role_remark = staff_role_remark;
	}
	public Long getStaff_role_org_id() {
		return staff_role_org_id;
	}
	public void setStaff_role_org_id(Long staff_role_org_id) {
		this.staff_role_org_id = staff_role_org_id;
	}
	
	
}