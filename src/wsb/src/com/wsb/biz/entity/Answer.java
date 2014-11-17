package com.wsb.biz.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "answer_demo_rt")
public class Answer extends BaseEntity {
	
	private Long answer_id;
	private String answer_name;
    private String question_id;
    private String is_checked;
	public Long getAnswer_id() {
		return answer_id;
	}
	public void setAnswer_id(Long answer_id) {
		this.answer_id = answer_id;
	}
	public String getAnswer_name() {
		return answer_name;
	}
	public void setAnswer_name(String answer_name) {
		this.answer_name = answer_name;
	}
	public String getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}
	public String getIs_checked() {
		return is_checked;
	}
	public void setIs_checked(String is_checked) {
		this.is_checked = is_checked;
	}

}
