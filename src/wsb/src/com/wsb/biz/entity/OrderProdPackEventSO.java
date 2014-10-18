package com.wsb.biz.entity;

import java.util.Set;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class OrderProdPackEventSO extends BaseSO {

    @Column(name="order_prod_pack_event_id")
    private Long[] ids ;
    private Set<Long> prod_pack_ids ;
    
    private Long prod_pack_id ;
    
    private Long order_id ;
 
	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}



	public Set<Long> getProd_pack_ids() {
		return prod_pack_ids;
	}

	public void setProd_pack_ids(Set<Long> prod_pack_ids) {
		this.prod_pack_ids = prod_pack_ids;
	}

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	
	public Long getProd_pack_id() {
		return prod_pack_id;
	}
	public void setProd_pack_id(Long prod_pack_id) {
		this.prod_pack_id = prod_pack_id;
	}

	@Override
	public Class<?> getTableClass() {
		return OrderProdPackEvent.class;
	}
	
}
