package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class ProductPackSO extends BaseSO {

    @Column(name="prod_pack_id")
    private Long[] ids ;
    
    @Column(name="prod_pack_id")
	private Long id;
 
    private String prod_pack_name  ;

    private Long customer_id;
    
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
	

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Class<?> getTableClass() {
		return ProductPack.class;
	}
	
}
