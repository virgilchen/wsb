package com.wsb.biz.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.U;
import com.globalwave.system.web.annotations.Pid;
import com.opensymphony.xwork2.Preparable;
import com.wsb.biz.entity.Answer;
import com.wsb.biz.entity.AnswerSO;
import com.wsb.biz.entity.Car;
import com.wsb.biz.entity.CarSO;
import com.wsb.biz.entity.Customer;
import com.wsb.biz.entity.Member;
import com.wsb.biz.entity.MemberAppl;
import com.wsb.biz.entity.MemberApplSO;
import com.wsb.biz.entity.MemberSO;
import com.wsb.biz.entity.Question;
import com.wsb.biz.entity.QuestionSO;
import com.wsb.biz.service.AnswerBO;
import com.wsb.biz.service.CarBO;
import com.wsb.biz.service.CustomerBO;
import com.wsb.biz.service.MemberApplBO;
import com.wsb.biz.service.MemberBO;
import com.wsb.biz.service.QuestionBO;

@Service("biz_memberAction")
@Scope("prototype")
public class MemberAction extends BaseAction implements Preparable {
	
private static final long serialVersionUID = 7244882365197775441L;
    
    private MemberBO memberBO ;
    private Member member ;
    private MemberSO memberSO ; 
    private CustomerBO customerBO ;
    private CarBO carBO ;
    private AnswerBO answerBO;
    private QuestionBO questionBO;
    private List<Question> questions ;
    private MemberApplBO memberApplBO;
    
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
        Object newMember = memberBO.create(member,questions) ;
        
        Customer customer = customerBO.get(member.getPsdo_cust_id()) ;
        customer.setMember_id(member.getId());
        customer.setMember_idc("1");//是否会员，0不是会员，1是会员，2会员过期
        customerBO.update(customer);
        
        renderObject(newMember, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        memberBO.update(member,questions) ;
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
    	ArrayList<Long> ansIds = new ArrayList<Long>();
    	if(pageList.size()>0){
    		member = (Member) pageList.get(0);
    		car = carBO.get(member.getCar_id());
    		member.setCar(car);
    		
    		MemberApplSO memberApplSO = new MemberApplSO();
            memberApplSO.setMember_id(member.getId());
            ArrayPageList memberAppls = memberApplBO.query(memberApplSO);
            if(memberAppls.size()>0){
            	for(int i=0; i<memberAppls.size(); i++){
            		MemberAppl memberAppl = (MemberAppl) memberAppls.get(i);
            		Long ansId = memberAppl.getMember_appl_form_answer_id();
            		ansIds.add(ansId);
            	}
            }
            
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
    	QuestionSO questionso = new QuestionSO();
    	questionso.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE);
    	ArrayPageList _questionList = questionBO.query(questionso);
    	ArrayPageList<Question> questionList = new ArrayPageList<Question>();
    	if(_questionList.size()>0){
    		for(int i=0; i<_questionList.size(); i++){
    			Question e = (Question) _questionList.get(i);
    			AnswerSO answerso = new AnswerSO();
    			answerso.setAppl_form_question_id(e.getId());
    			answerso.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE);
    			ArrayPageList<Answer> _answerList = answerBO.query(answerso);
    			ArrayPageList<Answer> answerList = new ArrayPageList<Answer>();
    			if(ansIds != null){
    				for(Answer answer : _answerList){
        				for(int j=0; j<ansIds.size(); j++){
        					Long ansId = ansIds.get(j);
        					if(ansId.equals(answer.getId())){
        						answer.setCheckType("checked");
        						break;
        					}
        				}
        				answerList.add(answer);
        			}
    				e.setAnswers(answerList);
    			}else{
    				e.setAnswers(_answerList);
    			}
    			
    			
    			questionList.add(e);
    		}
    	}
    	member.setQuestions(questionList);
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

	public void setCustomerBO(CustomerBO customerBO) {
		this.customerBO = customerBO;
	}

	public void setCarBO(CarBO carBO) {
		this.carBO = carBO;
	}

	public void setAnswerBO(AnswerBO answerBO) {
		this.answerBO = answerBO;
	}

	public void setQuestionBO(QuestionBO questionBO) {
		this.questionBO = questionBO;
	}

	public void setMemberApplBO(MemberApplBO memberApplBO) {
		this.memberApplBO = memberApplBO;
	}


	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
