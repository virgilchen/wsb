package com.wsb.biz.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class DocumentSO extends BaseSO {

    @Column(name="id")
    private Long[] ids ;

    private String document_name        ;

    private String document_type        ;

	private Long order_id;
    

	public Long[] getIds() {
		return ids;
	}


	public void setIds(Long[] ids) {
		this.ids = ids;
	}


	public String getDocument_name() {
		if (document_name == null || "".equals(document_name)) {
			return null ;
		}
		return document_name;
	}


	public void setDocument_name(String document_name) {
		this.document_name = document_name;
	}


	public String getDocument_type() {
		if (document_type == null || "".equals(document_type)) {
			return null ;
		}
		return document_type;
	}


	public void setDocument_type(String document_type) {
		this.document_type = document_type;
	}


	public Long getOrder_id() {
		return order_id;
	}


	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}


	@Override
	public Class<?> getTableClass() {
		return Document.class;
	}
	
}
