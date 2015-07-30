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
@Table(name = "wf_key_info_rel")
public class WfKeyInfoRel extends BaseEntity{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="rel_id")
	private Long id;
	private Long business_id;
    private Integer procs_step_no;
    private Long wf_key_info_id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Long getWf_key_info_id() {
		return wf_key_info_id;
	}
	public void setWf_key_info_id(Long wf_key_info_id) {
		this.wf_key_info_id = wf_key_info_id;
	}

}
