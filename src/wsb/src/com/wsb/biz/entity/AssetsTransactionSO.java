package com.wsb.biz.entity;


import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class AssetsTransactionSO extends BaseSO {

    @Column(name="id")
    private Long[] ids ;
    
	private Long biz_assets_holding_id;
	private Long order_id;
    private Short transaction_type;
    private Double amount;
    

	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	public Long getBiz_assets_holding_id() {
		return biz_assets_holding_id;
	}
	public void setBiz_assets_holding_id(Long biz_assets_holding_id) {
		this.biz_assets_holding_id = biz_assets_holding_id;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public Short getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(Short transaction_type) {
		this.transaction_type = transaction_type;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Override
	public Class<?> getTableClass() {
		return AssetsTransaction.class;
	}
    
}
