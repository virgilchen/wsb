package com.wsb.biz.entity;

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
@Table(name = "wf_key_info_result")
public class WfKeyInfoResult extends BaseEntity{
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="wf_key_info_result_id")
	private Long id;
	private String wf_key_info_id;
	private String wf_key_info_details_id;
	private String wf_key_info_type;
    private String wf_key_info_name;
    private String is_required;
    private String detail_name;
    private String detail_val;
    private Long order_id;
    
    @Transient
    private String detail_ids;
    @Transient
    private String detail_vals;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWf_key_info_id() {
		return wf_key_info_id;
	}
	public void setWf_key_info_id(String wf_key_info_id) {
		this.wf_key_info_id = wf_key_info_id;
	}
	public String getWf_key_info_details_id() {
		return wf_key_info_details_id;
	}
	public void setWf_key_info_details_id(String wf_key_info_details_id) {
		this.wf_key_info_details_id = wf_key_info_details_id;
	}
	public String getWf_key_info_type() {
		return wf_key_info_type;
	}
	public void setWf_key_info_type(String wf_key_info_type) {
		this.wf_key_info_type = wf_key_info_type;
	}
	public String getWf_key_info_name() {
		return wf_key_info_name;
	}
	public void setWf_key_info_name(String wf_key_info_name) {
		this.wf_key_info_name = wf_key_info_name;
	}
	public String getIs_required() {
		return is_required;
	}
	public void setIs_required(String is_required) {
		this.is_required = is_required;
	}
	public String getDetail_name() {
		return detail_name;
	}
	public void setDetail_name(String detail_name) {
		this.detail_name = detail_name;
	}
	public String getDetail_val() {
		return detail_val;
	}
	public void setDetail_val(String detail_val) {
		this.detail_val = detail_val;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public String getDetail_ids() {
		return detail_ids;
	}
	public void setDetail_ids(String detail_ids) {
		this.detail_ids = detail_ids;
	}
	public String getDetail_vals() {
		return detail_vals;
	}
	public void setDetail_vals(String detail_vals) {
		this.detail_vals = detail_vals;
	}

}
