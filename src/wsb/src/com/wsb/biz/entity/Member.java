package com.wsb.biz.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.globalwave.base.BaseEntity;

@Entity
@Table(name = "member_info_rt")

public class Member extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="member_id")
	private Long id;
	
	private Long psdo_cust_id;
    private String member_login_id;
    private String member_login_pwd;
    private String member_status;
    private Date member_expiry_date;
    private String member_type;
    private String member_internal_level;
    private Long member_duration;
    private String member_expectation;
    private Timestamp member_create_time;
    private Long car_id;
    
    @Transient
    private Car car;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPsdo_cust_id() {
		return psdo_cust_id;
	}

	public void setPsdo_cust_id(Long psdo_cust_id) {
		this.psdo_cust_id = psdo_cust_id;
	}

	public String getMember_login_id() {
		return member_login_id;
	}

	public void setMember_login_id(String member_login_id) {
		this.member_login_id = member_login_id;
	}

	public String getMember_login_pwd() {
		return member_login_pwd;
	}

	public void setMember_login_pwd(String member_login_pwd) {
		this.member_login_pwd = member_login_pwd;
	}

	public String getMember_status() {
		return member_status;
	}

	public void setMember_status(String member_status) {
		this.member_status = member_status;
	}

	public Date getMember_expiry_date() {
		return member_expiry_date;
	}

	public void setMember_expiry_date(Date member_expiry_date) {
		this.member_expiry_date = member_expiry_date;
	}

	public String getMember_type() {
		return member_type;
	}

	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}

	public String getMember_internal_level() {
		return member_internal_level;
	}

	public void setMember_internal_level(String member_internal_level) {
		this.member_internal_level = member_internal_level;
	}

	public Long getMember_duration() {
		return member_duration;
	}

	public void setMember_duration(Long member_duration) {
		this.member_duration = member_duration;
	}

	public String getMember_expectation() {
		return member_expectation;
	}

	public void setMember_expectation(String member_expectation) {
		this.member_expectation = member_expectation;
	}

	public Timestamp getMember_create_time() {
		return member_create_time;
	}

	public void setMember_create_time(Timestamp member_create_time) {
		this.member_create_time = member_create_time;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Long getCar_id() {
		return car_id;
	}

	public void setCar_id(Long car_id) {
		this.car_id = car_id;
	}
    
}
