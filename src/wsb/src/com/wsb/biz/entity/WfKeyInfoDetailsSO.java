package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;

public class WfKeyInfoDetailsSO extends BaseSO{
	
	@Column(name="wf_key_info_details_id")
    private Long[] ids ;
	private Long wf_key_info_id;
    private String isActive;
    
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	public Long getWf_key_info_id() {
		return wf_key_info_id;
	}
	public void setWf_key_info_id(Long wf_key_info_id) {
		this.wf_key_info_id = wf_key_info_id;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Override
	public Class<?> getTableClass() {
		return WfKeyInfoDetails.class;
	}
}
