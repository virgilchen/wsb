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
import com.globalwave.common.ArrayPageList;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "cust_demo_rt")
public class Customer extends BaseEntity {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="psdo_cust_id")
	private Long id;
    private String cust_code;
    private String cust_phone_no;
    private String cust_name;
    private Date cust_birthday;
    private String cust_home_address;
    private String cust_gender;
    private String other_contact_way;
    private String background;
    private String company_info;
    private String insurance_resource;
    private String contact_person;
    private String cust_src;
    private String cust_industry_type;
    private String company_name;
    private String cust_job_title;
    private String relationship_network;
    private String company_address;
    private String cust_age;
    private String member_idc;
    private Long member_id;
    private String cust_id;
    
    @Transient
    private ArrayPageList<Car> cars;
    
    @Transient
    private ArrayPageList<Document> documents;
    
    @Transient
    private Member member;
    
    //查询结果展示用的属性
    @Transient
    private String member_login_id;
    @Transient
    private String car_no;
    

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public Date getCust_birthday() {
		return cust_birthday;
	}
	public void setCust_birthday(Date cust_birthday) {
		this.cust_birthday = cust_birthday;
	}
	public String getCust_home_address() {
		return cust_home_address;
	}
	public void setCust_home_address(String cust_home_address) {
		this.cust_home_address = cust_home_address;
	}
	public String getCust_gender() {
		return cust_gender;
	}
	public void setCust_gender(String cust_gender) {
		this.cust_gender = cust_gender;
	}
	public String getOther_contact_way() {
		return other_contact_way;
	}
	public void setOther_contact_way(String other_contact_way) {
		this.other_contact_way = other_contact_way;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getCompany_info() {
		return company_info;
	}
	public void setCompany_info(String company_info) {
		this.company_info = company_info;
	}
	public String getInsurance_resource() {
		return insurance_resource;
	}
	public void setInsurance_resource(String insurance_resource) {
		this.insurance_resource = insurance_resource;
	}
	public String getContact_person() {
		return contact_person;
	}
	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}
	public String getCust_src() {
		return cust_src;
	}
	public void setCust_src(String cust_src) {
		this.cust_src = cust_src;
	}
	public String getCust_industry_type() {
		return cust_industry_type;
	}
	public void setCust_industry_type(String cust_industry_type) {
		this.cust_industry_type = cust_industry_type;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCust_job_title() {
		return cust_job_title;
	}
	public void setCust_job_title(String cust_job_title) {
		this.cust_job_title = cust_job_title;
	}
	public String getRelationship_network() {
		return relationship_network;
	}
	public void setRelationship_network(String relationship_network) {
		this.relationship_network = relationship_network;
	}
	public String getCompany_address() {
		return company_address;
	}
	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	public String getCust_age() {
		return cust_age;
	}
	public void setCust_age(String cust_age) {
		this.cust_age = cust_age;
	}
	public String getMember_idc() {
		return member_idc;
	}
	public void setMember_idc(String member_idc) {
		this.member_idc = member_idc;
	}
	public Long getMember_id() {
		return member_id;
	}
	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}
	public ArrayPageList<Car> getCars() {
		return cars;
	}
	public void setCars(ArrayPageList<Car> cars) {
		this.cars = cars;
	}
	public ArrayPageList<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(ArrayPageList<Document> documents) {
		this.documents = documents;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getMember_login_id() {
		return member_login_id;
	}
	public void setMember_login_id(String member_login_id) {
		this.member_login_id = member_login_id;
	}
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	
}



