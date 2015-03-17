package com.wsb.biz.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;

@Entity
@Table(name = "recommendation_key_map_dim")
public class RecommendationKeyMapDim extends BaseEntity {
	
	private String recmdt_key_in_inventory;
    private String recmdt_key_type;
    private String recmdt_table_name;
    private String recmdt_col_name;
    private String recmdt_col_type;
	public String getRecmdt_key_in_inventory() {
		return recmdt_key_in_inventory;
	}
	public void setRecmdt_key_in_inventory(String recmdt_key_in_inventory) {
		this.recmdt_key_in_inventory = recmdt_key_in_inventory;
	}
	public String getRecmdt_key_type() {
		return recmdt_key_type;
	}
	public void setRecmdt_key_type(String recmdt_key_type) {
		this.recmdt_key_type = recmdt_key_type;
	}
	public String getRecmdt_table_name() {
		return recmdt_table_name;
	}
	public void setRecmdt_table_name(String recmdt_table_name) {
		this.recmdt_table_name = recmdt_table_name;
	}
	public String getRecmdt_col_name() {
		return recmdt_col_name;
	}
	public void setRecmdt_col_name(String recmdt_col_name) {
		this.recmdt_col_name = recmdt_col_name;
	}
	public String getRecmdt_col_type() {
		return recmdt_col_type;
	}
	public void setRecmdt_col_type(String recmdt_col_type) {
		this.recmdt_col_type = recmdt_col_type;
	}
}
