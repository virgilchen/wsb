package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class PageSO extends BaseSO {

    @Column(name="page_id")
    private Long[] ids ;
    
    private Long staff_role_id;
 
    
	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
	public Long getStaff_role_id() {
		return staff_role_id;
	}
	
	public void setStaff_role_id(Long staff_role_id) {
		this.staff_role_id = staff_role_id;
	}

	@Override
	public Class<?> getTableClass() {
		return Page.class;
	}
	
}
