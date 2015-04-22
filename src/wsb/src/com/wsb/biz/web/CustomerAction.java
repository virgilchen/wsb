package com.wsb.biz.web;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.AssetsHoldingSO;
import com.wsb.biz.entity.Car;
import com.wsb.biz.entity.CarSO;
import com.wsb.biz.entity.Customer;
import com.wsb.biz.entity.CustomerSO;
import com.wsb.biz.entity.Member;
import com.wsb.biz.entity.OrderSO;
import com.wsb.biz.entity.RecommendationEngine;
import com.wsb.biz.entity.RecommendationEngineSO;
import com.wsb.biz.service.AssetsHoldingBO;
import com.wsb.biz.service.CarBO;
import com.wsb.biz.service.CustomerBO;
import com.wsb.biz.service.DocumentBO;
import com.wsb.biz.service.MemberBO;
import com.wsb.biz.service.OrderBO;
import com.wsb.biz.service.RecommendationEngineBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_customerAction")
@Scope("prototype")
public class CustomerAction extends BaseAction implements Preparable {
	
private static final long serialVersionUID = 7244882365197775441L;
    
    private CustomerBO customerBO ;
    private Customer customer ;
    private CustomerSO customerSO ;
    private OrderSO orderSO;
    private List<Car> cars ;
    private CarBO carBO ;
    private Car car ;
    private CarSO carSO ; 
    private AssetsHoldingBO assetsHoldingBO;
    private AssetsHoldingSO assetsHoldingSO;
    private MemberBO memberBO;
    private List<RecommendationEngine> recommendationEngines ;
    private RecommendationEngine recommendationEngine ;
    
    public String execute() throws Exception { 
        return this.list(); 
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<Customer> pageList = customerBO.query(customerSO) ;
        
        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception { 
    	
    	Customer customer = customerBO.get(this.id) ;
    	CarSO carso = new CarSO();
    	carso.setPsdo_cust_id(customer.getId());
    	customer.setCars(carBO.query(carso));
    	customer.setDocuments(DocumentBO.getDocumentBO().query(customer.getId(), "C"));
    	
    	RecommendationEngineSO recommendationEngineSO = new RecommendationEngineSO();
    	recommendationEngineSO.setRecmdt_psdo_cust_id(customer.getId());
    	customer.setRecommendationEngines(RecommendationEngineBO.getRecommendationEngineBO().query(recommendationEngineSO));
    	
    	//如果是会员，查出会员信息
    	if(customer.getMember_id() != null && !customer.getMember_id().equals("")){
    		getRequest().setAttribute("mbid", customer.getMember_id());
    		Member member = new Member();
    		member = memberBO.get(customer.getMember_id());
    		customer.setMember(member);
    	}
    	renderObject(customer, null) ; 
    	
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        
    	
        Object newCustomer = customerBO.create(customer, cars) ;

        renderObject(newCustomer, ResponseMessage.KEY_CREATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

        customerBO.update(customer,cars) ;

        renderObject(customer, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            customerBO.delete(customer) ;
        } else {
            customerBO.deleteAll(ids) ;
        }

        
        renderObject(customer, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }
    
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String view360() throws Exception {  
    	ArrayPageList<Customer> pageList = customerBO.query(customerSO) ;

        renderList(pageList) ; 
        return "jsp" ;  
    }
    
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String getMyOrders() throws Exception {
    	//orderSO.setPsdo_cust_id(id);
    	
        renderList(OrderBO.getOrderBO().queryOrderHistories(orderSO)) ; 
        
        return null ;  
    }
    
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String getAssetsHolding() throws Exception {
    	
        renderList(assetsHoldingBO.getAssetsHoldingBO().queryAssetsHolding(assetsHoldingSO)) ; 
        
        return null ;  
    }
    
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String openNewView() throws Exception {

    	Customer customer = customerBO.get(this.id) ;
    	CarSO carso = new CarSO();
    	carso.setPsdo_cust_id(customer.getId());
    	customer.setCars(carBO.query(carso));
    	customer.setDocuments(DocumentBO.getDocumentBO().query(customer.getId(), "C"));
    	
    	//如果是会员，查出会员信息
    	if(customer.getMember_id() != null && !customer.getMember_id().equals("")){
    		getRequest().setAttribute("mbid", customer.getMember_id());
    		Member member = new Member();
    		member = memberBO.get(customer.getMember_id());
    		customer.setMember(member);
    	}
    	renderObject(customer, null) ; 
    	
        return null ;  
    }
    

    public void setCustomerBO(CustomerBO customerBO) {
		this.customerBO = customerBO;
	}

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerSO getCustomerSO() {
        return customerSO;
    }

    public void setCustomerSO(CustomerSO customerSO) {
        this.customerSO = customerSO;
    }

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public CarSO getCarSO() {
		return carSO;
	}

	public void setCarSO(CarSO carSO) {
		this.carSO = carSO;
	}

	public void setCarBO(CarBO carBO) {
		this.carBO = carBO;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	public OrderSO getOrderSO() {
		return orderSO;
	}
	public void setOrderSO(OrderSO orderSO) {
		this.orderSO = orderSO;
	}


	public AssetsHoldingSO getAssetsHoldingSO() {
		return assetsHoldingSO;
	}

	public void setAssetsHoldingSO(AssetsHoldingSO assetsHoldingSO) {
		this.assetsHoldingSO = assetsHoldingSO;
	}

	public void setAssetsHoldingBO(AssetsHoldingBO assetsHoldingBO) {
		this.assetsHoldingBO = assetsHoldingBO;
	}

	public void setMemberBO(MemberBO memberBO) {
		this.memberBO = memberBO;
	}


	public List<RecommendationEngine> getRecommendationEngines() {
		return recommendationEngines;
	}


	public void setRecommendationEngines(
			List<RecommendationEngine> recommendationEngines) {
		this.recommendationEngines = recommendationEngines;
	}


	public RecommendationEngine getRecommendationEngine() {
		return recommendationEngine;
	}


	public void setRecommendationEngine(RecommendationEngine recommendationEngine) {
		this.recommendationEngine = recommendationEngine;
	}

}
