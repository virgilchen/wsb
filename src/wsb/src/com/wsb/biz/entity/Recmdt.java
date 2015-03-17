package com.wsb.biz.entity;

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
@Table(name = "recommendation_dt")
public class Recmdt extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="recmdt_id")
	private Long id;
	
	private String recmdt_name;
	private String recmdt_status;
	private String recmdt_remark;
	private String recmdt_detail;
	private String recmdt_condition_operator;
	
	@Transient
    private ArrayPageList<RecmdtInventory> recmdtInventorys;
	
	@Transient
    private ArrayPageList<RecommendationKeyMapDim> keyTypes;
	
	@Transient
    private ArrayPageList<RecommendationKeyMapDim> keyInfos;
	
	@Transient
    private ArrayPageList<RecommendationOprMapDim> oprInfos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecmdt_name() {
		return recmdt_name;
	}

	public void setRecmdt_name(String recmdt_name) {
		this.recmdt_name = recmdt_name;
	}

	public String getRecmdt_status() {
		return recmdt_status;
	}

	public void setRecmdt_status(String recmdt_status) {
		this.recmdt_status = recmdt_status;
	}

	public String getRecmdt_remark() {
		return recmdt_remark;
	}

	public void setRecmdt_remark(String recmdt_remark) {
		this.recmdt_remark = recmdt_remark;
	}

	public String getRecmdt_detail() {
		return recmdt_detail;
	}

	public void setRecmdt_detail(String recmdt_detail) {
		this.recmdt_detail = recmdt_detail;
	}

	public String getRecmdt_condition_operator() {
		return recmdt_condition_operator;
	}

	public void setRecmdt_condition_operator(String recmdt_condition_operator) {
		this.recmdt_condition_operator = recmdt_condition_operator;
	}

	public ArrayPageList<RecmdtInventory> getRecmdtInventorys() {
		return recmdtInventorys;
	}

	public void setRecmdtInventorys(ArrayPageList<RecmdtInventory> recmdtInventorys) {
		this.recmdtInventorys = recmdtInventorys;
	}

	public ArrayPageList<RecommendationKeyMapDim> getKeyTypes() {
		return keyTypes;
	}

	public void setKeyTypes(ArrayPageList<RecommendationKeyMapDim> keyTypes) {
		this.keyTypes = keyTypes;
	}

	public ArrayPageList<RecommendationKeyMapDim> getKeyInfos() {
		return keyInfos;
	}

	public void setKeyInfos(ArrayPageList<RecommendationKeyMapDim> keyInfos) {
		this.keyInfos = keyInfos;
	}

	public ArrayPageList<RecommendationOprMapDim> getOprInfos() {
		return oprInfos;
	}

	public void setOprInfos(ArrayPageList<RecommendationOprMapDim> oprInfos) {
		this.oprInfos = oprInfos;
	}
	
}
