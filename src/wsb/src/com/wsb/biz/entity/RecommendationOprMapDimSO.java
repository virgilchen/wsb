package com.wsb.biz.entity;

import com.globalwave.base.BaseSO;

public class RecommendationOprMapDimSO extends BaseSO {
	
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
	
	@Override
	public Class<?> getTableClass() {
		return RecommendationOprMapDim.class;
	}
}
