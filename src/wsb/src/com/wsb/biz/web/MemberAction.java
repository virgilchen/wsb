package com.wsb.biz.web;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.U;
import com.globalwave.system.web.annotations.Pid;
import com.opensymphony.xwork2.Preparable;
import com.wsb.biz.entity.Car;
import com.wsb.biz.entity.CarSO;
import com.wsb.biz.entity.Customer;
import com.wsb.biz.entity.CustomerSO;
import com.wsb.biz.entity.Member;
import com.wsb.biz.entity.MemberSO;
import com.wsb.biz.service.CarBO;
import com.wsb.biz.service.CustomerBO;
import com.wsb.biz.service.MemberBO;

@Service("biz_memberAction")
@Scope("prototype")
public class MemberAction extends BaseAction implements Preparable {
	
private static final long serialVersionUID = 7244882365197775441L;
    
    private MemberBO memberBO ;
    private Member member ;
    private MemberSO memberSO ; 
    private CustomerBO customerBO ;
    private Customer customer ;
    private CustomerSO customerSO ; 
    private CarBO carBO ;
    private Car car ;
    private CarSO carSO ;
    
    public String execute() throws Exception { 
        return this.list(); 
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<Member> pageList = memberBO.query(memberSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	Member member = memberBO.get(this.id) ;

    	renderObject(member, null) ; 
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        
    	
    	member.setMember_create_time(U.currentTimestamp());
    	member.setMember_status("1");//会员状态，默认0不是会员，1是会员，2会员过期
        Object newMember = memberBO.create(member) ;
        
        Customer customer = customerBO.get(member.getPsdo_cust_id()) ;
        customer.setMember_id(member.getId());
        customer.setMember_idc("1");//是否会员，0不是会员，1是会员，2会员过期
        customerBO.update(customer);
        
        renderObject(newMember, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        memberBO.update(member) ;

        renderObject(member, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            memberBO.delete(member) ;
        } else {
            memberBO.deleteAll(ids) ;
        }

        
        renderObject(member, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }
    
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String toMember() throws Exception {  
    	
    	HttpServletRequest request = getRequest();
    	MemberSO memberSO = new MemberSO();
    	String customerId = request.getParameter("customerId");
    	memberSO.setPsdo_cust_id(customerId);
    	ArrayPageList<Member> pageList = memberBO.query(memberSO) ;
    	Member member = null;
    	Car car = null;
    	if(pageList.size()>0){
    		member = (Member) pageList.get(0);
    		car = carBO.get(member.getCar_id());
    		member.setCar(car);
    	} else {
    		member = new Member();
    		member.setPsdo_cust_id(Long.parseLong(customerId));
    		CarSO carso = new CarSO();
    		carso.setPsdo_cust_id(Long.parseLong(customerId));
    		ArrayPageList _cars = carBO.query(carso);
    		if(_cars.size() > 0){
        		car = (Car) _cars.get(0);
        		member.setCar_id(car.getId());
        	}else{
        		car = new Car();
        	}
    		member.setCar(car);
    	}
    	renderObject(member, null) ; 
        return null ;  
    }

    public void setMemberBO(MemberBO memberBO) {
		this.memberBO = memberBO;
	}

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public MemberSO getMemberSO() {
        return memberSO;
    }

    public void setMemberSO(MemberSO memberSO) {
        this.memberSO = memberSO;
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

	public void setCustomerBO(CustomerBO customerBO) {
		this.customerBO = customerBO;
	}

	public void setCarBO(CarBO carBO) {
		this.carBO = carBO;
	}

}
