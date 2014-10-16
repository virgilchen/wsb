package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.Customer;
import com.wsb.biz.entity.CustomerSO;


@Service("customerBO")
@Scope("prototype")
@Transactional
public class CustomerBO extends BaseServiceImpl {

    public Customer create(Customer customer) {  

    	Customer newItem = (Customer) jdbcDao.insert(customer) ;
        
        return newItem;
    }
    
    public void update(Customer customer) {
    	
        jdbcDao.update(customer) ;
    }
    

    public void delete(Customer customer) {
    	
        jdbcDao.delete(customer) ;
        
    }

    public void deleteAll(Long[] customerIds) {
    	
        CustomerSO customer = new CustomerSO() ;
        customer.setIds(customerIds) ;
        jdbcDao.delete(Customer.class, customer) ;
        
    }
    
    
    public ArrayPageList<Customer> query(CustomerSO customerSO) {

        if (customerSO == null) {
        	customerSO = new CustomerSO() ;
        }
        //customerSO.addDesc("customer_timestamp") ;
        
        return (ArrayPageList<Customer>)jdbcDao.query(customerSO, Customer.class);
    }



    public Customer get(Long id) {  
    	Customer customer = new Customer() ;
    	customer.setId(id) ;
    	customer = (Customer) jdbcDao.get(customer) ;
        
        
        return customer;
    }
    
}
