package com.wsb.biz.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "product_pack_detail_rt")
public class ProductPackDetail extends BaseEntity {
	
    @Id
	private Long prod_pack_id;
    @Id
	private Long prod_id;
	private Integer quantity;
	
	
	public Long getProd_pack_id() {
		return prod_pack_id;
	}
	public void setProd_pack_id(Long prod_pack_id) {
		this.prod_pack_id = prod_pack_id;
	}
	public Long getProd_id() {
		return prod_id;
	}
	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}



