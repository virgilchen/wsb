package com.wsb.biz.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;
import com.globalwave.base.annotations.Versionable;

@Entity
@Table(name = "biz_assets_holding")
@Versionable
public class AssetsHolding extends BaseEntity {
	
	public static Short ASSETS_TYPE_PRODUCT = (short)1;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long customer_id;
    private Short assets_type;
    private Long pro_assets_id;
	private Long assets_id;
	private Short assets_unit;
	private Long order_id;

    private Double total_amount;
    private Double used_amount;
    private Double pending_amount;
    private Double remain_amount;
    

    private Date effect_date;
    private Date expire_date;
    
    private String record_status ;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	public Short getAssets_type() {
		return assets_type;
	}
	public void setAssets_type(Short assets_type) {
		this.assets_type = assets_type;
	}
	public Long getAssets_id() {
		return assets_id;
	}
	public void setAssets_id(Long assets_id) {
		this.assets_id = assets_id;
	}
	public Short getAssets_unit() {
		return assets_unit;
	}
	public void setAssets_unit(Short assets_unit) {
		this.assets_unit = assets_unit;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public Double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}
	public Double getUsed_amount() {
		return used_amount;
	}
	public void setUsed_amount(Double used_amount) {
		this.used_amount = used_amount;
	}
	public Double getPending_amount() {
		return pending_amount;
	}
	public void setPending_amount(Double pending_amount) {
		this.pending_amount = pending_amount;
	}
	public Double getRemain_amount() {
		return remain_amount;
	}
	public void setRemain_amount(Double remain_amount) {
		this.remain_amount = remain_amount;
	}
	public Date getEffect_date() {
		return effect_date;
	}
	public void setEffect_date(Date effect_date) {
		this.effect_date = effect_date;
	}
	public Date getExpire_date() {
		return expire_date;
	}
	public void setExpire_date(Date expire_date) {
		this.expire_date = expire_date;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
	public Long getPro_assets_id() {
		return pro_assets_id;
	}
	public void setPro_assets_id(Long pro_assets_id) {
		this.pro_assets_id = pro_assets_id;
	}
    
    
    
}
