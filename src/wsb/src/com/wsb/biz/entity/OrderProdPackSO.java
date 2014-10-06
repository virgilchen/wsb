package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class OrderProdPackSO extends BaseSO {

    @Column(name="order_prod_pack_id")
    private Long[] ids ;
    private Long order_id ;
    
	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}


	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	@Override
	public Class<?> getTableClass() {
		return OrderProdPack.class;
	}
	
}
