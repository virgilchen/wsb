package com.wsb.biz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;

@Entity
@Table(name = "recommendation_engine_dt")

public class RecommendationEngine extends BaseEntity {
	
	@Id
    @Column(name="recmdt_engine_id")
	private Long id;
	
	private Long recmdt_psdo_cust_id;
	private Long recmdt_engine_priority;
    private String recmdt_engine_detail;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRecmdt_psdo_cust_id() {
		return recmdt_psdo_cust_id;
	}
	public void setRecmdt_psdo_cust_id(Long recmdt_psdo_cust_id) {
		this.recmdt_psdo_cust_id = recmdt_psdo_cust_id;
	}
	public Long getRecmdt_engine_priority() {
		return recmdt_engine_priority;
	}
	public void setRecmdt_engine_priority(Long recmdt_engine_priority) {
		this.recmdt_engine_priority = recmdt_engine_priority;
	}
	public String getRecmdt_engine_detail() {
		return recmdt_engine_detail;
	}
	public void setRecmdt_engine_detail(String recmdt_engine_detail) {
		this.recmdt_engine_detail = recmdt_engine_detail;
	}
    
}
