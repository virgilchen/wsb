package com.wsb.biz.service;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.C;
import com.globalwave.common.cache.CodeHelper;
import com.wsb.biz.entity.AssetsHolding;
import com.wsb.biz.entity.AssetsHoldingSO;
import com.wsb.biz.entity.AssetsTransaction;
import com.wsb.biz.entity.Order;
import com.wsb.biz.entity.OrderProdPack;
import com.wsb.biz.entity.Product;


@Service("assetsHoldingBO")
@Scope("prototype")
@Transactional
public class AssetsHoldingBO extends BaseServiceImpl {

    public AssetsHolding create(AssetsHolding assetsHolding) {  

    	AssetsHolding newItem = (AssetsHolding) jdbcDao.insert(assetsHolding) ;
        
        return newItem;
    }
    
    public void update(AssetsHolding assetsHolding) {
    	
        jdbcDao.update(assetsHolding) ;
    }
    

    public void delete(AssetsHolding assetsHolding) {
    	
        jdbcDao.delete(assetsHolding) ;
        
    }

    public void deleteAll(Long[] assetsHoldingIds) {
    	
        AssetsHoldingSO criterion = new AssetsHoldingSO() ;
        criterion.setIds(assetsHoldingIds) ;
        jdbcDao.delete(AssetsHolding.class, criterion) ;
        
    }
    
    
    public ArrayPageList<AssetsHolding> query(AssetsHoldingSO assetsHoldingSO) {

        if (assetsHoldingSO == null) {
        	assetsHoldingSO = new AssetsHoldingSO() ;
        }
        assetsHoldingSO.addDesc("assetsHolding_timestamp") ;
        
        return (ArrayPageList<AssetsHolding>)jdbcDao.query(assetsHoldingSO, AssetsHolding.class);
    }

    public void add(Order order, OrderProdPack orderProdPack) {
    	
    	ArrayPageList<Product> products = 
    			ProductBO.getProductBO().queryProdByProdPackId(orderProdPack.getProd_pack_id());
    	
    	for (Product product:products) {
	    	AssetsHolding holding = new AssetsHolding();
	    	
	    	holding.setAssets_type(AssetsHolding.ASSETS_TYPE_PRODUCT);
	    	holding.setCustomer_id(order.getPsdo_cust_id());
	    	holding.setAssets_id(product.getId());
	    	holding.setAssets_unit(null);
	    	holding.setOrder_id(order.getId());
	    	
	        holding.setTotal_amount(new Double(orderProdPack.getNo_of_order_prod_pack()));
	        holding.setUsed_amount(C.ZERO_DOUBLE);
	        holding.setPending_amount(C.ZERO_DOUBLE);
	        holding.setRemain_amount(holding.getPending_amount());
	        
	        holding.setEffect_date(orderProdPack.getOrder_prod_pack_effect_date());
	        holding.setExpire_date(orderProdPack.getOrder_prod_pack_expire_date());
	        
	        holding.setRecord_status(C.RECORD_STATUS_ACTIVE);
	        
	        jdbcDao.insert(holding);
	        
	    	AssetsTransaction trans = new AssetsTransaction();
	    	trans.setBiz_assets_holding_id(holding.getId());
	    	trans.setOrder_id(holding.getOrder_id());
	    	trans.setAmount(holding.getTotal_amount());
	    	trans.setTransaction_type(AssetsTransaction.TRANSACTION_TYPE_ADD);
	        
	    	trans.setRecord_status(C.RECORD_STATUS_ACTIVE);

	        jdbcDao.insert(trans);
    	}
    }

    public AssetsHolding get(Long id) {  
    	AssetsHolding org = new AssetsHolding() ;
    	org.setId(id) ;
        org = (AssetsHolding) jdbcDao.get(org) ;
        
        
        return org;
    }
    
    
    public static AssetsHoldingBO getAssetsHoldingBO() {
    	return (AssetsHoldingBO) CodeHelper.getAppContext().getBean("assetsHoldingBO");
    }
}
