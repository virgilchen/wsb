package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;

public class WfKeyInfoSO extends BaseSO{
	
	@Column(name="wf_key_info_id")
    private Long[] ids ;
	private String wf_key_info_type;
    private String wf_key_info_name;
    
    
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
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
    
	@Override
	public Class<?> getTableClass() {
		return WfKeyInfo.class;
	}
    
}
