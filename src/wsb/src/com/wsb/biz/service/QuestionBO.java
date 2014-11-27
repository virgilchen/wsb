package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.wsb.biz.entity.Question;
import com.wsb.biz.entity.QuestionSO;

@Service("questionBO")
@Scope("prototype")
@Transactional

public class QuestionBO extends BaseServiceImpl {
	
	public Question create(Question question) {  

    	Question newItem = (Question) jdbcDao.insert(question) ;
        
        return newItem;
    }
    
    public void update(Question question) {
    	
        jdbcDao.update(question) ;
    }
    

    public void delete(Question question) {
    	
        jdbcDao.delete(question) ;
        
    }

    public void deleteAll(Long[] questionIds) {
    	
        QuestionSO question = new QuestionSO() ;
        question.setIds(questionIds) ;
        jdbcDao.delete(Question.class, question) ;
        
    }
    
    
    public ArrayPageList<Question> query(QuestionSO questionSO) {

        if (questionSO == null) {
        	questionSO = new QuestionSO() ;
        }
        questionSO.addAsc("appl_form_question_id") ;
        
        return (ArrayPageList<Question>)jdbcDao.query(questionSO, Question.class);
    }


    public Question get(Long id) {  
    	Question question = new Question() ;
    	question.setId(id) ;
    	question = (Question) jdbcDao.get(question) ;
        
        
        return question;
    }

}
