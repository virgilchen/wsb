package com.wsb.biz.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.globalwave.base.BaseEntity;

@Entity
@Table(name = "car_info_rt")

public class Car extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="car_id")
	private Long id;
	
	private Long psdo_cust_id;
    private String car_no;
    private String car_district;
    private String car_band;
    private String car_type;
    private String car_color;
    private String car_pai_liang;
    private String car_framework_no;
    private String car_engine_no;
    private Date car_init_register_date;
    private Double car_miles;
    private Double car_price;
    private String car_used_city;
    private String car_service_city;
    private String car_sales;
    private String insurance_assistant;
    
    
	public Long getPsdo_cust_id() {
		return psdo_cust_id;
	}
	public void setPsdo_cust_id(Long psdo_cust_id) {
		this.psdo_cust_id = psdo_cust_id;
	}
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	public String getCar_district() {
		return car_district;
	}
	public void setCar_district(String car_district) {
		this.car_district = car_district;
	}
	public String getCar_band() {
		return car_band;
	}
	public void setCar_band(String car_band) {
		this.car_band = car_band;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public String getCar_color() {
		return car_color;
	}
	public void setCar_color(String car_color) {
		this.car_color = car_color;
	}
	public String getCar_pai_liang() {
		return car_pai_liang;
	}
	public void setCar_pai_liang(String car_pai_liang) {
		this.car_pai_liang = car_pai_liang;
	}
	public String getCar_framework_no() {
		return car_framework_no;
	}
	public void setCar_framework_no(String car_framework_no) {
		this.car_framework_no = car_framework_no;
	}
	public String getCar_engine_no() {
		return car_engine_no;
	}
	public void setCar_engine_no(String car_engine_no) {
		this.car_engine_no = car_engine_no;
	}
	public Double getCar_miles() {
		return car_miles;
	}
	public void setCar_miles(Double car_miles) {
		this.car_miles = car_miles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCar_init_register_date() {
		return car_init_register_date;
	}
	public void setCar_init_register_date(Date car_init_register_date) {
		this.car_init_register_date = car_init_register_date;
	}
	public Double getCar_price() {
		return car_price;
	}
	public void setCar_price(Double car_price) {
		this.car_price = car_price;
	}
	public String getCar_used_city() {
		return car_used_city;
	}
	public void setCar_used_city(String car_used_city) {
		this.car_used_city = car_used_city;
	}
	public String getCar_service_city() {
		return car_service_city;
	}
	public void setCar_service_city(String car_service_city) {
		this.car_service_city = car_service_city;
	}
	public String getCar_sales() {
		return car_sales;
	}
	public void setCar_sales(String car_sales) {
		this.car_sales = car_sales;
	}
	public String getInsurance_assistant() {
		return insurance_assistant;
	}
	public void setInsurance_assistant(String insurance_assistant) {
		this.insurance_assistant = insurance_assistant;
	}
    
}
