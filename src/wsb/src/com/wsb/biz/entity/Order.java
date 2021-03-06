package com.wsb.biz.entity;

import java.sql.Timestamp;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.globalwave.base.BaseEntity;
import com.globalwave.common.ArrayPageList;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "order_rt")
public class Order extends BaseEntity {

	final public static String STATUS_CANCEL = "C";
	final public static String STATUS_START = "S";
	final public static String STATUS_INIT = "I";
	final public static String STATUS_END = "E";

	final public static String ORDER_TYPE_PURCHASE = "P";
	final public static String ORDER_TYPE_BUSINESS = "B";
	
	
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="order_id")
	private Long id;

    private String order_type  ;
    private Timestamp order_init_time_stamp  ;
    private Long order_init_staff_id  ;
    private String order_no;
    private Long psdo_cust_id  ;
    private String order_cur_status  ;
    private String order_remark  ;
    private Double selling_price;
    
    private Integer urgent_levent ;
    
    
    @Transient
    private String order_init_staff_name;
    
    @Transient
    private Customer customer ;
    
    @Transient
    private ArrayPageList<OrderProdPack> orderProdPacks;
    
    @Transient
    private ArrayPageList<HashMap> orderProdPackEvents;

    @Transient
    private ArrayPageList<HashMap> products ;

    @Transient
    private ArrayPageList<Document> documents;
    
    @Transient
    private ArrayPageList<WfKeyInfo> wfKeyInfos ;
    
    @Transient
    private ArrayPageList<WfKeyInfoResult> wfKeyInfoResults;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getOrder_init_time_stamp() {
		return order_init_time_stamp;
	}
	public void setOrder_init_time_stamp(Timestamp order_init_time_stamp) {
		this.order_init_time_stamp = order_init_time_stamp;
	}
	public Long getOrder_init_staff_id() {
		return order_init_staff_id;
	}
	public void setOrder_init_staff_id(Long order_init_staff_id) {
		this.order_init_staff_id = order_init_staff_id;
	}
	public Long getPsdo_cust_id() {
		return psdo_cust_id;
	}
	public void setPsdo_cust_id(Long psdo_cust_id) {
		this.psdo_cust_id = psdo_cust_id;
	}
	public String getOrder_cur_status() {
		return order_cur_status;
	}
	public void setOrder_cur_status(String order_cur_status) {
		this.order_cur_status = order_cur_status;
	}
	public String getOrder_remark() {
		return order_remark;
	}
	public void setOrder_remark(String order_remark) {
		this.order_remark = order_remark;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public ArrayPageList<OrderProdPack> getOrderProdPacks() {
		return orderProdPacks;
	}
	public void setOrderProdPacks(ArrayPageList<OrderProdPack> orderProdPacks) {
		this.orderProdPacks = orderProdPacks;
	}
	public ArrayPageList<HashMap> getOrderProdPackEvents() {
		return orderProdPackEvents;
	}
	public void setOrderProdPackEvents(
			ArrayPageList<HashMap> orderProdPackEvents) {
		this.orderProdPackEvents = orderProdPackEvents;
	}
	public ArrayPageList<HashMap> getProducts() {
		return products;
	}
	public void setProducts(ArrayPageList<HashMap> products) {
		this.products = products;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getOrder_init_staff_name() {
		return order_init_staff_name;
	}
	public void setOrder_init_staff_name(String order_init_staff_name) {
		this.order_init_staff_name = order_init_staff_name;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public ArrayPageList<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(ArrayPageList<Document> documents) {
		this.documents = documents;
	}
	public Double getSelling_price() {
		return selling_price;
	}
	public void setSelling_price(Double selling_price) {
		this.selling_price = selling_price;
	}

	public Integer getUrgent_levent() {
		return urgent_levent;
	}
	
	public void setUrgent_levent(Integer urgent_levent) {
		this.urgent_levent = urgent_levent;
	}
	public ArrayPageList<WfKeyInfo> getWfKeyInfos() {
		return wfKeyInfos;
	}
	public void setWfKeyInfos(ArrayPageList<WfKeyInfo> wfKeyInfos) {
		this.wfKeyInfos = wfKeyInfos;
	}
	public ArrayPageList<WfKeyInfoResult> getWfKeyInfoResults() {
		return wfKeyInfoResults;
	}
	public void setWfKeyInfoResults(ArrayPageList<WfKeyInfoResult> wfKeyInfoResults) {
		this.wfKeyInfoResults = wfKeyInfoResults;
	}
	
}



