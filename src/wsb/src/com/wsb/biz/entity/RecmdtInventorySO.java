package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;

public class RecmdtInventorySO extends BaseSO {
	
	@Column(name="recmdt_id")
    private Long[] ids ;
	
	private Long recmdt_code;

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Long getRecmdt_code() {
		return recmdt_code;
	}

	public void setRecmdt_code(Long recmdt_code) {
		this.recmdt_code = recmdt_code;
	}
	
	@Override
	public Class<?> getTableClass() {
		return RecmdtInventory.class;
	}

}
