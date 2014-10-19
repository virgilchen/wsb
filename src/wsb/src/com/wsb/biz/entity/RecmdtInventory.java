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
@Table(name = "recommendation_inventory_dt")
public class RecmdtInventory extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="recmdt_id")
	private Long id;
	
	private String recmdt_code;
	private String recmdt_name;
	private String recmdt_status;
	private String recmdt_remark;
	private String recmdt_detail;
	private String recmdt_condition_operator;
	private Long recmdt_condition_no;
	private String recmdt_type;
	private String recmdt_key;
	private String recmdt_operator;
	private String recmdt_value;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRecmdt_name() {
		return recmdt_name;
	}
	public void setRecmdt_name(String recmdt_name) {
		this.recmdt_name = recmdt_name;
	}
	public String getRecmdt_status() {
		return recmdt_status;
	}
	public void setRecmdt_status(String recmdt_status) {
		this.recmdt_status = recmdt_status;
	}
	public String getRecmdt_remark() {
		return recmdt_remark;
	}
	public void setRecmdt_remark(String recmdt_remark) {
		this.recmdt_remark = recmdt_remark;
	}
	public String getRecmdt_detail() {
		return recmdt_detail;
	}
	public void setRecmdt_detail(String recmdt_detail) {
		this.recmdt_detail = recmdt_detail;
	}
	public String getRecmdt_condition_operator() {
		return recmdt_condition_operator;
	}
	public void setRecmdt_condition_operator(String recmdt_condition_operator) {
		this.recmdt_condition_operator = recmdt_condition_operator;
	}
	public Long getRecmdt_condition_no() {
		return recmdt_condition_no;
	}
	public void setRecmdt_condition_no(Long recmdt_condition_no) {
		this.recmdt_condition_no = recmdt_condition_no;
	}
	public String getRecmdt_type() {
		return recmdt_type;
	}
	public void setRecmdt_type(String recmdt_type) {
		this.recmdt_type = recmdt_type;
	}
	public String getRecmdt_key() {
		return recmdt_key;
	}
	public void setRecmdt_key(String recmdt_key) {
		this.recmdt_key = recmdt_key;
	}
	public String getRecmdt_operator() {
		return recmdt_operator;
	}
	public void setRecmdt_operator(String recmdt_operator) {
		this.recmdt_operator = recmdt_operator;
	}
	public String getRecmdt_value() {
		return recmdt_value;
	}
	public void setRecmdt_value(String recmdt_value) {
		this.recmdt_value = recmdt_value;
	}
	public String getRecmdt_code() {
		return recmdt_code;
	}
	public void setRecmdt_code(String recmdt_code) {
		this.recmdt_code = recmdt_code;
	}
	
	
}