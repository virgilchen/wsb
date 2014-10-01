package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.Notice;
import com.wsb.biz.entity.NoticeSO;


@Service("noticeBO")
@Scope("prototype")
@Transactional
public class NoticeBO extends BaseServiceImpl {

    public Notice create(Notice notice) {  

    	Notice newItem = (Notice) jdbcDao.insert(notice) ;
        
        return newItem;
    }
    
    public void update(Notice notice) {
    	
        jdbcDao.update(notice) ;
    }
    

    public void delete(Notice notice) {
    	
        jdbcDao.delete(notice) ;
        
    }

    public void deleteAll(Long[] noticeIds) {
    	
        NoticeSO criterion = new NoticeSO() ;
        criterion.setIds(noticeIds) ;
        jdbcDao.delete(Notice.class, criterion) ;
        
    }
    
    
    public ArrayPageList<Notice> query(NoticeSO noticeSO) {

        if (noticeSO == null) {
        	noticeSO = new NoticeSO() ;
        }
        noticeSO.addDesc("notice_timestamp") ;
        
        return (ArrayPageList<Notice>)jdbcDao.query(noticeSO, Notice.class);
    }



    public Notice get(Long id) {  
    	Notice org = new Notice() ;
    	org.setId(id) ;
        org = (Notice) jdbcDao.get(org) ;
        
        
        return org;
    }
    
}
