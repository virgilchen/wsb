package com.wsb.biz.entity;

import java.sql.Timestamp;

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
@Table(name = "order_prod_pack_event_rt")
public class OrderProdPackEvent extends BaseEntity {

	final public static String STATUS_CONTINUE = "C";
	final public static String STATUS_BACK = "B";
	final public static String STATUS_SUCCESSFULLY = "S";
	final public static String STATUS_FAIL = "F";
	final public static String STATUS_INIT = "I";
	final public static String STATUS_READY = "R";
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="event_id")
	private Long id;
 
    private Timestamp event_time_stamp  ;
    
	private Long order_id;
	private Long prod_pack_id;
	private Long prod_id;
	
	private Long business_id;
	private Integer procs_step_no;
	
    private String event_type  ;
    private Long event_staff_id  ;
    private Timestamp event_duration  ;
    private String event_status  ;
    private String event_remark  ;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getEvent_time_stamp() {
		return event_time_stamp;
	}
	public void setEvent_time_stamp(Timestamp event_time_stamp) {
		this.event_time_stamp = event_time_stamp;
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
	public Long getProd_id() {
		return prod_id;
	}
	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}
	public Long getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(Long business_id) {
		this.business_id = business_id;
	}
	public Integer getProcs_step_no() {
		return procs_step_no;
	}
	public void setProcs_step_no(Integer procs_step_no) {
		this.procs_step_no = procs_step_no;
	}
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public Long getEvent_staff_id() {
		return event_staff_id;
	}
	public void setEvent_staff_id(Long event_staff_id) {
		this.event_staff_id = event_staff_id;
	}
	public Timestamp getEvent_duration() {
		return event_duration;
	}
	public void setEvent_duration(Timestamp event_duration) {
		this.event_duration = event_duration;
	}
	public String getEvent_status() {
		return event_status;
	}
	public void setEvent_status(String event_status) {
		this.event_status = event_status;
	}
	public String getEvent_remark() {
		return event_remark;
	}
	public void setEvent_remark(String event_remark) {
		this.event_remark = event_remark;
	}

    
}



