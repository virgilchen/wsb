package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class ProductPackSO extends BaseSO {

    @Column(name="notice_id")
    private Long[] ids ;
 
    private String prod_pack_name  ;

    
	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}



	public String getProd_pack_name() {
		return prod_pack_name;
	}

	public void setProd_pack_name(String prod_pack_name) {
		this.prod_pack_name = prod_pack_name;
	}

	@Override
	public Class<?> getTableClass() {
		return ProductPack.class;
	}
	
}
