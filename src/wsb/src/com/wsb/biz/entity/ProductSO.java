package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class ProductSO extends BaseSO {

    @Column(name="prod_id")
    private Long[] ids ;
 
    private String prod_name;
    private String prod_desc;

	private Long business_id;
	private Long prod_pack_id;
	
    @Column(name="prod_id")
	private Long[] prod_ids;

    
	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	
	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getProd_desc() {
		return prod_desc;
	}

	public void setProd_desc(String prod_desc) {
		this.prod_desc = prod_desc;
	}

	public Long getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(Long business_id) {
		this.business_id = business_id;
	}

	public Long getProd_pack_id() {
		return prod_pack_id;
	}

	public void setProd_pack_id(Long prod_pack_id) {
		this.prod_pack_id = prod_pack_id;
	}


	public Long[] getProd_ids() {
		return prod_ids;
	}

	public void setProd_ids(Long[] prod_ids) {
		this.prod_ids = prod_ids;
	}

	@Override
	public Class<?> getTableClass() {
		return Product.class;
	}
	
}
