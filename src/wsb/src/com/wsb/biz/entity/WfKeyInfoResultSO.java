package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;

public class WfKeyInfoResultSO extends BaseSO{
	@Column(name="wf_key_info_result_id")
    private Long[] ids ;
	private Long wf_key_info_id;
    private Long wf_key_info_details_id;
    private Long order_id;
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
	public Long getWf_key_info_details_id() {
		return wf_key_info_details_id;
	}
	public void setWf_key_info_details_id(Long wf_key_info_details_id) {
		this.wf_key_info_details_id = wf_key_info_details_id;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	@Override
	public Class<?> getTableClass() {
		return WfKeyInfoResult.class;
	}
}
