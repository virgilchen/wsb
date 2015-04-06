package com.wsb.biz.entity;

import java.sql.Date;

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
	
	private Date order_prod_pack_purchase_date;	
	private Date order_prod_pack_effect_date;	
	private Date order_prod_pack_expire_date;
	private String order_prod_pack_remark;
	
	private String prod_names;

	@Transient
	private Double prod_pack_selling_price;
	@Transient
	private String prod_pack_name;

	@Transient
    private Long[] product_ids;

	@Transient
    private String[] event_prod_types;

	@Transient
    private Long[] business_ids;
	
	@Transient
    private Long[] event_staff_ids;

	@Transient
    private Double[] amounts;
   
    
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
	public Date getOrder_prod_pack_purchase_date() {
		return order_prod_pack_purchase_date;
	}
	public void setOrder_prod_pack_purchase_date(
			Date order_prod_pack_purchase_date) {
		this.order_prod_pack_purchase_date = order_prod_pack_purchase_date;
	}
	public Date getOrder_prod_pack_effect_date() {
		return order_prod_pack_effect_date;
	}
	public void setOrder_prod_pack_effect_date(Date order_prod_pack_effect_date) {
		this.order_prod_pack_effect_date = order_prod_pack_effect_date;
	}
	public Date getOrder_prod_pack_expire_date() {
		return order_prod_pack_expire_date;
	}
	public void setOrder_prod_pack_expire_date(Date order_prod_pack_expire_date) {
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
	public Long[] getBusiness_ids() {
		return business_ids;
	}
	public void setBusiness_ids(Long[] business_ids) {
		this.business_ids = business_ids;
	}
	public Long[] getEvent_staff_ids() {
		return event_staff_ids;
	}
	public void setEvent_staff_ids(Long[] event_staff_ids) {
		this.event_staff_ids = event_staff_ids;
	}
	public Long[] getProduct_ids() {
		return product_ids;
	}
	public void setProduct_ids(Long[] product_ids) {
		this.product_ids = product_ids;
	}
	public Double[] getAmounts() {
		return amounts;
	}
	public void setAmounts(Double[] amounts) {
		this.amounts = amounts;
	}
	public String getProd_names() {
		return prod_names;
	}
	public void setProd_names(String prod_names) {
		this.prod_names = prod_names;
	}
    public String[] getEvent_prod_types() {
		return event_prod_types;
	}
    public void setEvent_prod_types(String[] event_prod_types) {
		this.event_prod_types = event_prod_types;
	}
	public Double getProd_pack_selling_price() {
		return prod_pack_selling_price;
	}
	public void setProd_pack_selling_price(Double prod_pack_selling_price) {
		this.prod_pack_selling_price = prod_pack_selling_price;
	}
}



