package com.wsb.biz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;
import com.globalwave.base.annotations.Versionable;

@Entity
@Table(name = "biz_assets_transaction")
@Versionable
public class AssetsTransaction extends BaseEntity {

	public static Short TRANSACTION_TYPE_ADD = (short) 1;
	public static Short TRANSACTION_TYPE_PENDING = (short) -1;
	public static Short TRANSACTION_TYPE_CONFIRM = (short) 0;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Long assets_holding_id;
	private Long order_id;
    private Short transaction_type;
    private Double amount;
    
    private String record_status;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAssets_holding_id() {
		return assets_holding_id;
	}
	public void setAssets_holding_id(Long assets_holding_id) {
		this.assets_holding_id = assets_holding_id;
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

	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
}
