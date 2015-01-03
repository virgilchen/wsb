package com.wsb.biz.service;


import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.C;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.system.entity.SessionUser;
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
	        holding.setRemain_amount(holding.getTotal_amount());
	        
	        holding.setEffect_date(orderProdPack.getOrder_prod_pack_effect_date());
	        holding.setExpire_date(orderProdPack.getOrder_prod_pack_expire_date());
	        
	        holding.setRecord_status(C.RECORD_STATUS_ACTIVE);
	        
	        jdbcDao.insert(holding);
	        
	    	AssetsTransaction trans = new AssetsTransaction();
	    	trans.setAssets_holding_id(holding.getId());
	    	trans.setOrder_id(holding.getOrder_id());
	    	trans.setAmount(holding.getTotal_amount());
	    	trans.setTransaction_type(AssetsTransaction.TRANSACTION_TYPE_ADD);
	        
	    	trans.setRecord_status(C.RECORD_STATUS_ACTIVE);

	        jdbcDao.insert(trans);
    	}
    }

    public AssetsHolding get(Long id) {  
    	AssetsHolding holding = new AssetsHolding() ;
    	holding.setId(id) ;
        holding = (AssetsHolding) jdbcDao.get(holding) ;
        
        
        return holding;
    }

    public void pendingProductAmount(Long orderId, Long customer_id, OrderProdPack orderProdPack) {  
    	AssetsTransactionBO tranBO = AssetsTransactionBO.getAssetsTransactionBO();
    	
		for (int i = 0 ; i < orderProdPack.getProduct_ids().length ; i ++) {
			Long product_id = orderProdPack.getProduct_ids()[i];
			Double amount = orderProdPack.getAmounts()[i];
			
			if (amount == 0) {
				continue ;
			}
			
			AssetsHoldingSO assetsHoldingSO = new AssetsHoldingSO();
			
			assetsHoldingSO.setAssets_type(AssetsHolding.ASSETS_TYPE_PRODUCT);
			assetsHoldingSO.setAssets_id(product_id);
			assetsHoldingSO.setCustomer_id(customer_id);
			assetsHoldingSO.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE);
			
			ArrayPageList<AssetsHolding> holdings = 
					(ArrayPageList<AssetsHolding>)this.jdbcDao.queryName(
							"bizSQLs:queryAvailableAssetsHolding", assetsHoldingSO, AssetsHolding.class);

			for (AssetsHolding holding : holdings) {
				AssetsTransaction tran = new AssetsTransaction();
				tran.setAssets_holding_id(holding.getId());
				tran.setOrder_id(orderId);
				tran.setTransaction_type(AssetsTransaction.TRANSACTION_TYPE_PENDING);
				tran.setRecord_status(C.RECORD_STATUS_ACTIVE);
				
				double remain_amount = holding.getRemain_amount();
				
				if (remain_amount >= amount) {
					holding.setRemain_amount(remain_amount - amount) ;
					holding.setPending_amount(holding.getPending_amount() + amount);
					
					tran.setAmount(amount);
					
					amount = 0D ; 
					//break ;
				} else {
					holding.setRemain_amount(0D) ;
					holding.setPending_amount(holding.getPending_amount() + remain_amount);

					tran.setAmount(remain_amount);
					
					amount -= remain_amount ; 
				}
				
				holding.addInclusions("remain_amount");
				holding.addInclusions("pending_amount");
				jdbcDao.update(holding);

				tranBO.create(tran);
				
				if (amount == 0) {
					break;
				}
			}
	    	
			if (amount > 0) {
				throw new BusinessException(11201L);// 11201', '帐户上没有足够的已购买的产品！
			}
		}
        
    }

    public void confirmProductAmount(Long orderId) {
    	
    	AssetsTransaction tran = new AssetsTransaction();
    	tran.setOrder_id(orderId);
    	tran.setCreated_by(SessionUser.get().getLogin_id());
    	tran.setUpdated_by(tran.getCreated_by());
    	
        this.jdbcDao.executeName("bizSQLs:assetsHoldingConfirm", tran);
        this.jdbcDao.executeName("bizSQLs:assetsTransactionConfirm", tran);
    }
    
    public ArrayPageList<HashMap<String, Object>> queryAssetsHolding(AssetsHoldingSO assetsHoldingSO) {
    	ArrayPageList<HashMap<String, Object>> result = (ArrayPageList<HashMap<String, Object>>)jdbcDao.queryName("bizSQLs:queryAssetsHolding", assetsHoldingSO, HashMap.class);
        return result;
    }
    
    
    public static AssetsHoldingBO getAssetsHoldingBO() {
    	return (AssetsHoldingBO) CodeHelper.getAppContext().getBean("assetsHoldingBO");
    }
}
