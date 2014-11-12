package com.wsb.biz.service;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.wsb.biz.entity.OrderProdPack;
import com.wsb.biz.entity.OrderProdPackSO;


@Service("orderProdPackBO")
@Scope("prototype")
@Transactional
public class OrderProdPackBO extends BaseServiceImpl {

    public OrderProdPack create(OrderProdPack orderProdPack) {  

    	OrderProdPack newItem = (OrderProdPack) jdbcDao.insert(orderProdPack) ;
        
        return newItem;
    }
    
    public void update(OrderProdPack orderProdPack) {
    	
        jdbcDao.update(orderProdPack) ;
    }
    

    public void delete(OrderProdPack orderProdPack) {
    	
        jdbcDao.delete(orderProdPack) ;
        
    }

    public void deleteAll(Long[] orderProdPackIds) {
    	
        OrderProdPackSO criterion = new OrderProdPackSO() ;
        criterion.setIds(orderProdPackIds) ;
        jdbcDao.delete(OrderProdPack.class, criterion) ;
        
    }
    
    
    public ArrayPageList<OrderProdPack> query(OrderProdPackSO orderProdPackSO) {

        if (orderProdPackSO == null) {
        	orderProdPackSO = new OrderProdPackSO() ;
        }
        orderProdPackSO.addDesc("orderProdPack_timestamp") ;
        
        return (ArrayPageList<OrderProdPack>)jdbcDao.query(orderProdPackSO, OrderProdPack.class);
    }
    

    public ArrayPageList<OrderProdPack> queryByOrderId(Long order_id) {

    	OrderProdPackSO orderProdPackSO = new OrderProdPackSO() ;
    	orderProdPackSO.setOrder_id(order_id);
        orderProdPackSO.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE);
        
        return (ArrayPageList<OrderProdPack>)jdbcDao.queryName("bizSQLs:queryOrderProdPackByOrderId", orderProdPackSO, OrderProdPack.class);
    }
    public ArrayPageList<HashMap> queryOrderProdByOrderId(Long order_id) {

    	OrderProdPackSO orderProdPackSO = new OrderProdPackSO() ;
    	orderProdPackSO.setOrder_id(order_id);
        
        return (ArrayPageList<HashMap>)jdbcDao.queryName("bizSQLs:queryOrderProdByOrderId", orderProdPackSO, HashMap.class);
    }


    public OrderProdPack get(Long id) {  
    	OrderProdPack org = new OrderProdPack() ;
    	org.setId(id) ;
        org = (OrderProdPack) jdbcDao.get(org) ;
        
        
        return org;
    }
    public static OrderProdPackBO getOrderProdPackBO() {
    	return (OrderProdPackBO)CodeHelper.getAppContext().getBean("orderProdPackBO");
    }
}
