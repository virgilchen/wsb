package com.wsb.biz.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.entity.SessionUser;
import com.globalwave.util.DataFilterUtil;
import com.wsb.biz.entity.MemberAppl;
import com.wsb.biz.entity.MemberApplSO;

@Service("memberApplBO")
@Scope("prototype")
@Transactional

public class MemberApplBO extends BaseServiceImpl {
	public MemberAppl create(MemberAppl memberAppl) {  

    	MemberAppl newItem = (MemberAppl) jdbcDao.insert(memberAppl) ;
        
        return newItem;
    }
    
    public void update(MemberAppl memberAppl) {
    	
        jdbcDao.update(memberAppl) ;
    }
    
    public void update(List<MemberAppl> memberAppls, Long MemberId) {
    	for (MemberAppl memberAppl : memberAppls) {
    		if (memberAppl == null) {
    			continue ;
    		}

            memberAppl.setMember_id(MemberId);

    		if (memberAppl.getMember_id() == null) {
    			continue ;
    		}
    		
    		MemberAppl oldMemberAppl = (MemberAppl)this.jdbcDao.get(memberAppl);
            
    	}
    	
    	MemberApplSO memberApplSO = new MemberApplSO();
    	memberApplSO.setMember_id(MemberId);
    	jdbcDao.delete(MemberAppl.class, memberApplSO);
    	
    	for (MemberAppl memberAppl : memberAppls) {
    		if (memberAppl == null) {
    			continue ;
    		}

       		jdbcDao.insert(memberAppl);
    	}
    }
    

    public void delete(MemberAppl memberAppl) {
    	
        jdbcDao.delete(memberAppl) ;
        
    }

    
    public ArrayPageList<MemberAppl> query(MemberApplSO memberApplSO) {

        if (memberApplSO == null) {
        	memberApplSO = new MemberApplSO() ;
        }
        memberApplSO.addAsc("member_id") ;
        
        ArrayPageList<MemberAppl> result = (ArrayPageList<MemberAppl>)jdbcDao.query(memberApplSO, MemberAppl.class);

        return result ;
    }


    public static MemberApplBO getMemberApplBO() {
    	return (MemberApplBO)CodeHelper.getAppContext().getBean("memberApplBO");
    }

}
