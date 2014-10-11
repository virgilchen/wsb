package com.wsb.biz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "product_pack_rt")
public class ProductPack extends BaseEntity {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="prod_pack_id")
	private Long id;
    
    private String prod_pack_name;
    private String prod_pack_owner;
    private String prod_pack_picture;
    private Double prod_pack_orignal_price;
    private Double prod_pack_selling_price;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProd_pack_name() {
		return prod_pack_name;
	}
	public void setProd_pack_name(String prod_pack_name) {
		this.prod_pack_name = prod_pack_name;
	}
	public String getProd_pack_owner() {
		return prod_pack_owner;
	}
	public void setProd_pack_owner(String prod_pack_owner) {
		this.prod_pack_owner = prod_pack_owner;
	}
	public String getProd_pack_picture() {
		return prod_pack_picture;
	}
	public void setProd_pack_picture(String prod_pack_picture) {
		this.prod_pack_picture = prod_pack_picture;
	}
	public Double getProd_pack_orignal_price() {
		return prod_pack_orignal_price;
	}
	public void setProd_pack_orignal_price(Double prod_pack_orignal_price) {
		this.prod_pack_orignal_price = prod_pack_orignal_price;
	}
	public Double getProd_pack_selling_price() {
		return prod_pack_selling_price;
	}
	public void setProd_pack_selling_price(Double prod_pack_selling_price) {
		this.prod_pack_selling_price = prod_pack_selling_price;
	}
 
    
    
	
}



