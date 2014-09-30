package com.globalwave.system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.globalwave.base.BaseEntity;


/**
 *@Transient
 * @author Virgil.Chan
 */
@Entity
@Table(name = "cfg_dict")
public class Dict extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="increment")
    @GenericGenerator(name="dict_id_seq", strategy = "increment")
	private Long id  ;
    private String domain_ ;
    private String code_ ;
    private String name_ ;
    private String desc_ ;
    private String status_ ;
    private Integer order_  ;
    
    public Dict() {
    }

    public String getCode_() {
        return code_;
    }

    public void setCode_(String code_) {
        this.code_ = code_;
    }

    public String getDesc_() {
        return desc_;
    }

    public void setDesc_(String desc_) {
        this.desc_ = desc_;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDomain_() {
        return domain_;
    }

    public void setDomain_(String domain_) {
        this.domain_ = domain_;
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

	public Integer getOrder_() {
		return order_;
	}

	public void setOrder_(Integer order_) {
		this.order_ = order_;
	}

}
