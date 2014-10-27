package com.wsb.biz.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.globalwave.base.BaseEntity;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "role_page_rt")
public class RolePage extends BaseEntity {

	private Long staff_role_id;

	private Long page_id;

	@Transient
	private String page_name;

	public Long getStaff_role_id() {
		return staff_role_id;
	}

	public void setStaff_role_id(Long staff_role_id) {
		this.staff_role_id = staff_role_id;
	}

	public Long getPage_id() {
		return page_id;
	}

	public void setPage_id(Long page_id) {
		this.page_id = page_id;
	}
	public String getPage_name() {
		return page_name;
	}
	public void setPage_name(String page_name) {
		this.page_name = page_name;
	}
 
}



