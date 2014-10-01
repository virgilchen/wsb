package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class NoticeSO extends BaseSO {

    @Column(name="notice_id")
    private Long[] ids ;
 
    private String notice_subject  ;
    private String notice_content  ;

    
	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String getNotice_subject() {
		return notice_subject;
	}

	public void setNotice_subject(String notice_subject) {
		this.notice_subject = notice_subject;
	}


	public String getNotice_content() {
		return notice_content;
	}

	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}


	@Override
	public Class<?> getTableClass() {
		return Notice.class;
	}
	
}
