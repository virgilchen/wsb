package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;

public class AnswerSO extends BaseSO {
	
	@Column(name="appl_form_answer_id")
    private Long[] ids ;
	private Long appl_form_question_id;
    private String appl_form_answer_name;
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	public Long getAppl_form_question_id() {
		return appl_form_question_id;
	}
	public void setAppl_form_question_id(Long appl_form_question_id) {
		this.appl_form_question_id = appl_form_question_id;
	}
	public String getAppl_form_answer_name() {
		return appl_form_answer_name;
	}
	public void setAppl_form_answer_name(String appl_form_answer_name) {
		this.appl_form_answer_name = appl_form_answer_name;
	}
	@Override
	public Class<?> getTableClass() {
		return Answer.class;
	}

}
