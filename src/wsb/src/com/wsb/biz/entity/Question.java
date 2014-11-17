package com.wsb.biz.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;
import com.globalwave.common.ArrayPageList;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "question_demo_rt")
public class Question extends BaseEntity {
	
	private Long question_id;
    private String question_name;
    private String form_type;
    private String answer_type;
    private ArrayPageList<Answer> answers;
	public Long getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(Long question_id) {
		this.question_id = question_id;
	}
	public String getQuestion_name() {
		return question_name;
	}
	public void setQuestion_name(String question_name) {
		this.question_name = question_name;
	}
	public String getForm_type() {
		return form_type;
	}
	public void setForm_type(String form_type) {
		this.form_type = form_type;
	}
	public String getAnswer_type() {
		return answer_type;
	}
	public void setAnswer_type(String answer_type) {
		this.answer_type = answer_type;
	}
	public ArrayPageList<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayPageList<Answer> answers) {
		this.answers = answers;
	}

}
