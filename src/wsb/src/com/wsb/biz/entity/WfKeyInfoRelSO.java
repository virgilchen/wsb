package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;

public class WfKeyInfoRelSO extends BaseSO{
	
	@Column(name="rel_id")
    private Long[] ids ;
	private Long business_id;
    private Integer procs_step_no;
    private Long wf_key_info_id;
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
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
    
	@Override
	public Class<?> getTableClass() {
		return WfKeyInfoRel.class;
	}

}
