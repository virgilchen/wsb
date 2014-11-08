package com.wsb.biz.entity;


import java.sql.Timestamp;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;
import com.globalwave.base.annotations.Comparison;


public class NoticeSO extends BaseSO {

    @Column(name="notice_id")
    private Long[] ids ;
 
    private String notice_subject  ;
    private String notice_content  ;
    private Timestamp notice_timestamp;
    
    @Column(name="notice_timestamp")
    @Comparison(operator=">")
    private Timestamp notice_timestamp_start;

    
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

	public Timestamp getNotice_timestamp_start() {
		return notice_timestamp_start;
	}
	public void setNotice_timestamp_start(Timestamp notice_timestamp_start) {
		this.notice_timestamp_start = notice_timestamp_start;
	}
	public Timestamp getNotice_timestamp() {
		return notice_timestamp;
	}

	public void setNotice_timestamp(Timestamp notice_timestamp) {
		this.notice_timestamp = notice_timestamp;
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
