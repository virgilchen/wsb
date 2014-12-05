package com.wsb.biz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.globalwave.base.BaseEntity;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "member_appl_form_rt")
public class MemberAppl extends BaseEntity {
	
	private Long member_id;
    private String member_appl_form_type;
    private Long member_appl_form_question_id;
    private Long member_appl_form_answer_id;
	public Long getMember_id() {
		return member_id;
	}
	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}
	public String getMember_appl_form_type() {
		return member_appl_form_type;
	}
	public void setMember_appl_form_type(String member_appl_form_type) {
		this.member_appl_form_type = member_appl_form_type;
	}
	public Long getMember_appl_form_question_id() {
		return member_appl_form_question_id;
	}
	public void setMember_appl_form_question_id(Long member_appl_form_question_id) {
		this.member_appl_form_question_id = member_appl_form_question_id;
	}
	public Long getMember_appl_form_answer_id() {
		return member_appl_form_answer_id;
	}
	public void setMember_appl_form_answer_id(Long member_appl_form_answer_id) {
		this.member_appl_form_answer_id = member_appl_form_answer_id;
	}

}
