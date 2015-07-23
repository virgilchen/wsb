package com.wsb.biz.entity;

import java.sql.Timestamp;

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
@Table(name = "wf_key_info_details")

public class WfKeyInfoDetails extends BaseEntity{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="wf_key_info_details_id")
	private Long id;
	private Long wf_key_info_id;
	private String detail_name;
    private String can_input;
    private String detail_val;
    private Long detail_sn;
    private Long max_length;
    private Long min_length;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getWf_key_info_id() {
		return wf_key_info_id;
	}
	public void setWf_key_info_id(Long wf_key_info_id) {
		this.wf_key_info_id = wf_key_info_id;
	}
	public String getDetail_name() {
		return detail_name;
	}
	public void setDetail_name(String detail_name) {
		this.detail_name = detail_name;
	}
	public String getCan_input() {
		return can_input;
	}
	public void setCan_input(String can_input) {
		this.can_input = can_input;
	}
	public String getDetail_val() {
		return detail_val;
	}
	public void setDetail_val(String detail_val) {
		this.detail_val = detail_val;
	}
	public Long getDetail_sn() {
		return detail_sn;
	}
	public void setDetail_sn(Long detail_sn) {
		this.detail_sn = detail_sn;
	}
	public Long getMax_length() {
		return max_length;
	}
	public void setMax_length(Long max_length) {
		this.max_length = max_length;
	}
	public Long getMin_length() {
		return min_length;
	}
	public void setMin_length(Long min_length) {
		this.min_length = min_length;
	}
}
