package com.wsb.biz.web;

import java.sql.Timestamp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.U;
import com.globalwave.system.web.annotations.Pid;
import com.opensymphony.xwork2.Preparable;
import com.wsb.biz.entity.Customer;
import com.wsb.biz.entity.Member;
import com.wsb.biz.entity.MemberSO;
import com.wsb.biz.service.MemberBO;

@Service("biz_memberAction")
@Scope("prototype")
public class MemberAction extends BaseAction implements Preparable {
	
private static final long serialVersionUID = 7244882365197775441L;
    
    private MemberBO memberBO ;
    private Member member ;
    private MemberSO memberSO ; 
    
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
    	member.setMember_status("有效");
        Object newMember = memberBO.create(member) ;

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
    	
    	MemberSO memberSO = new MemberSO();
    	String carId = getRequest().getParameter("carId");
    	String customerId = getRequest().getParameter("customerId");
    	memberSO.setCar_id(carId);
    	memberSO.setPsdo_cust_id(customerId);
    	
    	ArrayPageList<Member> pageList = memberBO.query(memberSO) ;
    	Member member = new Member();
    	if(pageList.size()>0){
    		member = (Member) pageList.get(0);
    	}
    	getRequest().setAttribute("carId", carId);
    	getRequest().setAttribute("customerId", customerId);
    	renderObject(member, null) ; 
        return "jsp" ;  
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

}
