package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.wsb.biz.entity.Answer;
import com.wsb.biz.entity.AnswerSO;


@Service("answerBO")
@Scope("prototype")
@Transactional

public class AnswerBO extends BaseServiceImpl {
	
	public Answer create(Answer answer) {  

    	Answer newItem = (Answer) jdbcDao.insert(answer) ;
        
        return newItem;
    }
    
    public void update(Answer answer) {
    	
        jdbcDao.update(answer) ;
    }
    

    public void delete(Answer answer) {
    	
        jdbcDao.delete(answer) ;
        
    }

    public void deleteAll(Long[] answerIds) {
    	
        AnswerSO answer = new AnswerSO() ;
        answer.setIds(answerIds) ;
        jdbcDao.delete(Answer.class, answer) ;
        
    }
    
    
    public ArrayPageList<Answer> query(AnswerSO answerSO) {

        if (answerSO == null) {
        	answerSO = new AnswerSO() ;
        }
        answerSO.addAsc("appl_form_answer_id") ;
        
        return (ArrayPageList<Answer>)jdbcDao.query(answerSO, Answer.class);
    }



    public Answer get(Long id) {  
    	Answer answer = new Answer() ;
    	answer.setId(id) ;
    	answer = (Answer) jdbcDao.get(answer) ;
        
        
        return answer;
    }

}
