package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;

public class QuestionSO extends BaseSO {
	@Column(name="appl_form_question_id")
    private Long[] ids ;
	private String appl_form_type;
    private String appl_form_question_name;
    private String appl_form_answer_type;
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	public String getAppl_form_type() {
		return appl_form_type;
	}
	public void setAppl_form_type(String appl_form_type) {
		this.appl_form_type = appl_form_type;
	}
	public String getAppl_form_question_name() {
		return appl_form_question_name;
	}
	public void setAppl_form_question_name(String appl_form_question_name) {
		this.appl_form_question_name = appl_form_question_name;
	}
	public String getAppl_form_answer_type() {
		return appl_form_answer_type;
	}
	public void setAppl_form_answer_type(String appl_form_answer_type) {
		this.appl_form_answer_type = appl_form_answer_type;
	}
	@Override
	public Class<?> getTableClass() {
		return Question.class;
	}

}
