package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.Util;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.Member;
import com.wsb.biz.entity.MemberSO;


@Service("memberBO")
@Scope("prototype")
@Transactional

public class MemberBO extends BaseServiceImpl{
	
	public Member create(Member member) {  
		Member memberSO = new Member();
		memberSO.setMember_login_id(member.getMember_login_id());
		if (this.jdbcDao.find(memberSO) != null) {
			throw new BusinessException(1110L);//1110', '会员登录账号已经被使用，请使用其它登录账号
		}
//		member.setMember_login_pwd(Util.hash(member.getMember_login_pwd()));
    	Member newItem = (Member) jdbcDao.insert(member) ;
        
        return newItem;
    }
    
    public void update(Member member) {
    	
        jdbcDao.update(member) ;
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

}
