package com.wsb.biz.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.globalwave.base.BaseEntity;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "order_prod_pack_rt")
public class OrderProdPack extends BaseEntity {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="order_prod_pack_id")
	private Long id;

	private Long order_id;
	private Long prod_pack_id;
	private Integer no_of_order_prod_pack;
	
	private Timestamp order_prod_pack_purchase_date;	
	private Timestamp order_prod_pack_effect_date;	
	private Timestamp order_prod_pack_expire_date;
	private String order_prod_pack_remark;

	@Transient
	private String prod_pack_name;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getNo_of_order_prod_pack() {
		return no_of_order_prod_pack;
	}
	public void setNo_of_order_prod_pack(Integer no_of_order_prod_pack) {
		this.no_of_order_prod_pack = no_of_order_prod_pack;
	}
	public Timestamp getOrder_prod_pack_purchase_date() {
		return order_prod_pack_purchase_date;
	}
	public void setOrder_prod_pack_purchase_date(
			Timestamp order_prod_pack_purchase_date) {
		this.order_prod_pack_purchase_date = order_prod_pack_purchase_date;
	}
	public Timestamp getOrder_prod_pack_effect_date() {
		return order_prod_pack_effect_date;
	}
	public void setOrder_prod_pack_effect_date(Timestamp order_prod_pack_effect_date) {
		this.order_prod_pack_effect_date = order_prod_pack_effect_date;
	}
	public Timestamp getOrder_prod_pack_expire_date() {
		return order_prod_pack_expire_date;
	}
	public void setOrder_prod_pack_expire_date(Timestamp order_prod_pack_expire_date) {
		this.order_prod_pack_expire_date = order_prod_pack_expire_date;
	}
	public String getOrder_prod_pack_remark() {
		return order_prod_pack_remark;
	}
	public void setOrder_prod_pack_remark(String order_prod_pack_remark) {
		this.order_prod_pack_remark = order_prod_pack_remark;
	}
	public String getProd_pack_name() {
		return prod_pack_name;
	}
	public void setProd_pack_name(String prod_pack_name) {
		this.prod_pack_name = prod_pack_name;
	}
    
	
}



