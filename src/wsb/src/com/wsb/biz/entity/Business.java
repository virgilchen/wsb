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
@Table(name = "business_rt")
public class Business extends BaseEntity {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="business_id")
	private Long id;
 
    private String business_name  ;
    private String business_name_upper  ;
	private Long business_id_upper;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getBusiness_name_upper() {
		return business_name_upper;
	}
	public void setBusiness_name_upper(String business_name_upper) {
		this.business_name_upper = business_name_upper;
	}
	public Long getBusiness_id_upper() {
		return business_id_upper;
	}
	public void setBusiness_id_upper(Long business_id_upper) {
		this.business_id_upper = business_id_upper;
	}
	
}



