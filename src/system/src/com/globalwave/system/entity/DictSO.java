package com.globalwave.system.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;
import com.globalwave.base.annotations.Comparison;

public class DictSO extends BaseSO {
	
    @Column(name="id")
    private Long[] ids ;
    
    private String domain_ ;
    private String code_ ;
    @Column(name="domain_")
    @Comparison(operator=Comparison.EQ)
    private String domain_eq ;
    @Column(name="code_")
    @Comparison(operator=Comparison.EQ)
    private String code_eq ;
    private String name_ ;
    private String status_ ;
    /**
     * 通用
     */
    private String term ;
    
    public DictSO() {
    }

    public String getDomain_() {
		return domain_;
	}

	public void setDomain_(String domain_) {
		this.domain_ = domain_;
	}

	public String getCode_() {
		return code_;
	}

	public void setCode_(String code_) {
		this.code_ = code_;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getStatus_() {
		return status_;
	}

	public void setStatus_(String status_) {
		this.status_ = status_;
	}

	public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] dictIds) {
        this.ids = dictIds;
    }

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getDomain_eq() {
		return domain_eq;
	}

	public void setDomain_eq(String domain_eq) {
		this.domain_eq = domain_eq;
	}

	public String getCode_eq() {
		return code_eq;
	}

	public void setCode_eq(String code_eq) {
		this.code_eq = code_eq;
	}

	@Override
	public Class<?> getTableClass() {
		return Dict.class;
	}
    
    
    
}
