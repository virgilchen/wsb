package com.wsb.biz.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.Util;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.Answer;
import com.wsb.biz.entity.Member;
import com.wsb.biz.entity.MemberAppl;
import com.wsb.biz.entity.MemberApplSO;
import com.wsb.biz.entity.MemberSO;
import com.wsb.biz.entity.Question;


@Service("memberBO")
@Scope("prototype")
@Transactional

public class MemberBO extends BaseServiceImpl{
	
	public Member create(Member member,List<Question> questions) {  
		Member memberSO = new Member();
		memberSO.setMember_login_id(member.getMember_login_id());
		if (this.jdbcDao.find(memberSO) != null) {
			throw new BusinessException(1115L);//1115', '会员号已经被使用，请使用其它会员号
		}
		member.setMember_login_pwd(Util.hash(member.getMember_login_pwd()).toLowerCase());
    	Member newItem = (Member) jdbcDao.insert(member) ;
    	if(questions != null){
    		for(Question question : questions){
            	if(question.getAnsIds() != null){
            		for(int i=0; i<question.getAnsIds().length; i++){
            			Long ansId = question.getAnsIds()[i];
            			Answer ans = new Answer();
            			ans = AnswerBO.getAnswerBO().get(ansId);
            			MemberAppl memberAppl = new MemberAppl();
            			memberAppl.setMember_id(newItem.getId());
            			memberAppl.setMember_appl_form_question_id(Long.parseLong(ans.getAppl_form_question_id()));
            			memberAppl.setMember_appl_form_answer_id(ans.getId());
            			jdbcDao.insert(memberAppl);
            		}
            	}
            }
    	}
        
        return newItem;
    }
    
    public void update(Member member,List<Question> questions) {
    	Member memberSO = new Member();
		memberSO.setMember_login_id(member.getMember_login_id());
		Member membervo = (Member) jdbcDao.find(memberSO);
		if(membervo.getMember_login_pwd().equals(member.getMember_login_pwd())){
			member.addExclusions("member_login_pwd");
		}else{
			member.setMember_login_pwd(Util.hash(member.getMember_login_pwd()).toLowerCase());
		}
        jdbcDao.update(member) ;
        
        MemberApplSO memberApplSO = new MemberApplSO();
        memberApplSO.setMember_id(member.getId());
        jdbcDao.delete(MemberAppl.class, memberApplSO);
        if(questions != null){
        	for(Question question : questions){
            	if(question.getAnsIds() != null){
            		for(int i=0; i<question.getAnsIds().length; i++){
            			Long ansId = question.getAnsIds()[i];
            			Answer ans = new Answer();
            			ans = AnswerBO.getAnswerBO().get(ansId);
            			MemberAppl memberAppl = new MemberAppl();
            			memberAppl.setMember_id(member.getId());
            			memberAppl.setMember_appl_form_question_id(Long.parseLong(ans.getAppl_form_question_id()));
            			memberAppl.setMember_appl_form_answer_id(ans.getId());
            			jdbcDao.insert(memberAppl);
            		}
            	}
            }
        }
    }
    

    public void delete(Member member) {
    	
        jdbcDao.delete(member) ;
        
    }

    public void deleteAll(Long[] memberIds) {
    	
        MemberSO member = new MemberSO() ;
        member.setIds(memberIds) ;
        jdbcDao.delete(Member.class, member) ;
        
    }
    
    
    public ArrayPageList<Member> query(MemberSO memberSO) {

        if (memberSO == null) {
        	memberSO = new MemberSO() ;
        }
        memberSO.addAsc("member_id") ;
        
        return (ArrayPageList<Member>)jdbcDao.query(memberSO, Member.class);
    }



    public Member get(Long id) {  
    	Member member = new Member() ;
    	member.setId(id) ;
    	member = (Member) jdbcDao.get(member) ;
        
        
        return member;
    }
    
    public static MemberBO getMemberBO() {
    	return (MemberBO)CodeHelper.getAppContext().getBean("memberBO");
    }

}
