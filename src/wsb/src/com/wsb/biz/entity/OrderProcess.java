package com.wsb.biz.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "order_procs_rt")
public class OrderProcess extends BaseEntity {
	
    //@Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    //@Column(name="business_id")
	private Long business_id;
 
    private Integer procs_step_no;
    private String procs_step_name;
    private Long procs_staff_role_id;
	
	public Long getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(Long business_id) {
		this.business_id = business_id;
	}
	public Integer getProcs_step_no() {
		return procs_step_no;
	}
	public void setProcs_step_no(Integer procs_step_no) {
		this.procs_step_no = procs_step_no;
	}
	public String getProcs_step_name() {
		return procs_step_name;
	}
	public void setProcs_step_name(String procs_step_name) {
		this.procs_step_name = procs_step_name;
	}
	public Long getProcs_staff_role_id() {
		return procs_staff_role_id;
	}
	public void setProcs_staff_role_id(Long procs_staff_role_id) {
		this.procs_staff_role_id = procs_staff_role_id;
	}
}



