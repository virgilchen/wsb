package com.wsb.biz.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;

@Entity
@Table(name = "recommendation_opr_map_dim")
public class RecommendationOprMapDim extends BaseEntity {
	
	private String recommendation_opr_in_inventory;
    private String recommendation_opr_symbol;
	public String getRecommendation_opr_in_inventory() {
		return recommendation_opr_in_inventory;
	}
	public void setRecommendation_opr_in_inventory(
			String recommendation_opr_in_inventory) {
		this.recommendation_opr_in_inventory = recommendation_opr_in_inventory;
	}
	public String getRecommendation_opr_symbol() {
		return recommendation_opr_symbol;
	}
	public void setRecommendation_opr_symbol(String recommendation_opr_symbol) {
		this.recommendation_opr_symbol = recommendation_opr_symbol;
	}
}
