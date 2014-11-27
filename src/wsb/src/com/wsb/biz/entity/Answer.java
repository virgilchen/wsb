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
@Table(name = "appl_form_answer_dim")
public class Answer extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="appl_form_answer_id")
	private Long id;
	private String appl_form_question_id;
    private String appl_form_answer_name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAppl_form_question_id() {
		return appl_form_question_id;
	}
	public void setAppl_form_question_id(String appl_form_question_id) {
		this.appl_form_question_id = appl_form_question_id;
	}
	public String getAppl_form_answer_name() {
		return appl_form_answer_name;
	}
	public void setAppl_form_answer_name(String appl_form_answer_name) {
		this.appl_form_answer_name = appl_form_answer_name;
	}

}
