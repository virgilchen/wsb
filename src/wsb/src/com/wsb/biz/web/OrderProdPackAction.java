package com.wsb.biz.web;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.wsb.biz.entity.OrderProdPack;
import com.wsb.biz.service.OrderProdPackBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_orderProdPackAction")
@Scope("prototype")
public class OrderProdPackAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private OrderProdPackBO orderProdPackBO ;
    private OrderProdPack orderProdPack ;
   
    


	public OrderProdPackBO getOrderProdPackBO() {
		return orderProdPackBO;
	}

	public void setOrderProdPackBO(OrderProdPackBO orderProdPackBO) {
		this.orderProdPackBO = orderProdPackBO;
	}

	public OrderProdPack getOrderProdPack() {
		return orderProdPack;
	}

	public void setOrderProdPack(OrderProdPack orderProdPack) {
		this.orderProdPack = orderProdPack;
	}
    
    
}
