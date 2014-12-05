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
@Table(name = "appl_form_dim")
public class Question extends BaseEntity {
	
	@Id
    @Column(name="appl_form_question_id")
	private Long id;
    private String appl_form_type;
    private String appl_form_question_name;
    private String appl_form_answer_type;
    
    @Transient
    private ArrayPageList<Answer> answers;
    @Transient
    private Long[] ansIds;
	public ArrayPageList<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayPageList<Answer> answers) {
		this.answers = answers;
	}
	public Long[] getAnsIds() {
		return ansIds;
	}
	public void setAnsIds(Long[] ansIds) {
		this.ansIds = ansIds;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
}
