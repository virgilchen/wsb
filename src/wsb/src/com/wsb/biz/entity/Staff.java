package com.wsb.biz.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.globalwave.base.BaseEntity;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "staff_demo_rt")
public class Staff extends BaseEntity {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="staff_id")
	private Long id;
 
    private String staff_status;
    private String staff_login_profile;
	private String staff_login_pwd;
	private String staff_name;
	private String staff_gender;
	private Long staff_phone_no;
	private String staff_id_card;
	private Long staff_role_id;
	private Timestamp staff_last_login_time;
	

	@Transient
	private String old_password;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStaff_status() {
		return staff_status;
	}
	public void setStaff_status(String staff_status) {
		this.staff_status = staff_status;
	}
	public String getStaff_login_profile() {
		return staff_login_profile;
	}
	public void setStaff_login_profile(String staff_login_profile) {
		this.staff_login_profile = staff_login_profile;
	}
	public String getStaff_login_pwd() {
		return staff_login_pwd;
	}
	public void setStaff_login_pwd(String staff_login_pwd) {
		this.staff_login_pwd = staff_login_pwd;
	}
	public String getStaff_name() {
		return staff_name;
	}
	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}
	public String getStaff_gender() {
		return staff_gender;
	}
	public void setStaff_gender(String staff_gender) {
		this.staff_gender = staff_gender;
	}
	public Long getStaff_phone_no() {
		return staff_phone_no;
	}
	public void setStaff_phone_no(Long staff_phone_no) {
		this.staff_phone_no = staff_phone_no;
	}
	public String getStaff_id_card() {
		return staff_id_card;
	}
	public void setStaff_id_card(String staff_id_card) {
		this.staff_id_card = staff_id_card;
	}
	public Long getStaff_role_id() {
		return staff_role_id;
	}
	public void setStaff_role_id(Long staff_role_id) {
		this.staff_role_id = staff_role_id;
	}
	public Timestamp getStaff_last_login_time() {
		return staff_last_login_time;
	}
	public void setStaff_last_login_time(Timestamp staff_last_login_time) {
		this.staff_last_login_time = staff_last_login_time;
	}
    public String getOld_password() {
		return old_password;
	}
    public void setOld_password(String old_password) {
		this.old_password = old_password;
	}
}