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
import com.globalwave.common.ArrayPageList;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "wf_key_info")

public class WfKeyInfo extends BaseEntity{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="wf_key_info_id")
	private Long id;
	private String wf_key_info_type;
    private String wf_key_info_name;
    private String is_required;
    private String is_active;
    
    @Transient
    private ArrayPageList<WfKeyInfoDetails> wfKeyInfoDetailsList;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public ArrayPageList<WfKeyInfoDetails> getWfKeyInfoDetailsList() {
		return wfKeyInfoDetailsList;
	}
	public void setWfKeyInfoDetailsList(
			ArrayPageList<WfKeyInfoDetails> wfKeyInfoDetailsList) {
		this.wfKeyInfoDetailsList = wfKeyInfoDetailsList;
	}
	public String getIs_required() {
		return is_required;
	}
	public void setIs_required(String is_required) {
		this.is_required = is_required;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
    
}
