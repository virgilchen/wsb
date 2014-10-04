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
@Table(name = "product_rt")
public class Product extends BaseEntity {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="prod_id")
	private Long id;
 
    private String prod_name;
    private String prod_desc;

	private Long business_id;
	private Long prod_quantity;
    
	private String prod_unit; 
	private String prod_picture; 
	private double product_original_price;
	private double prodcut_selling_price; 
    
	private int no_of_product_effective_day; 
	private int no_of_product_effective_mth;
	private int no_of_day_remind_advance;
	private int no_of_mth_remind_advance;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getProd_quantity() {
		return prod_quantity;
	}
	public void setProd_quantity(Long prod_quantity) {
		this.prod_quantity = prod_quantity;
	}
	public String getProd_unit() {
		return prod_unit;
	}
	public void setProd_unit(String prod_unit) {
		this.prod_unit = prod_unit;
	}
	public String getProd_picture() {
		return prod_picture;
	}
	public void setProd_picture(String prod_picture) {
		this.prod_picture = prod_picture;
	}
	public double getProduct_original_price() {
		return product_original_price;
	}
	public void setProduct_original_price(double product_original_price) {
		this.product_original_price = product_original_price;
	}
	public double getProdcut_selling_price() {
		return prodcut_selling_price;
	}
	public void setProdcut_selling_price(double prodcut_selling_price) {
		this.prodcut_selling_price = prodcut_selling_price;
	}
	public int getNo_of_product_effective_day() {
		return no_of_product_effective_day;
	}
	public void setNo_of_product_effective_day(int no_of_product_effective_day) {
		this.no_of_product_effective_day = no_of_product_effective_day;
	}
	public int getNo_of_product_effective_mth() {
		return no_of_product_effective_mth;
	}
	public void setNo_of_product_effective_mth(int no_of_product_effective_mth) {
		this.no_of_product_effective_mth = no_of_product_effective_mth;
	}
	public int getNo_of_day_remind_advance() {
		return no_of_day_remind_advance;
	}
	public void setNo_of_day_remind_advance(int no_of_day_remind_advance) {
		this.no_of_day_remind_advance = no_of_day_remind_advance;
	}
	public int getNo_of_mth_remind_advance() {
		return no_of_mth_remind_advance;
	}
	public void setNo_of_mth_remind_advance(int no_of_mth_remind_advance) {
		this.no_of_mth_remind_advance = no_of_mth_remind_advance;
	}
    

	
}



