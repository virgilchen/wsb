package com.wsb.biz.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.Notice;
import com.wsb.biz.entity.NoticeSO;
import com.wsb.biz.service.NoticeBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_noticeAction")
@Scope("prototype")
public class NoticeAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private NoticeBO noticeBO ;
    private Notice notice ;
    private NoticeSO noticeSO ; 
    
    public String execute() throws Exception {        
        
        return this.list();        
        
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<Notice> pageList = noticeBO.query(noticeSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	Notice org = noticeBO.get(this.id) ;

    	renderObject(org, null) ; 
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newNotice = noticeBO.create(notice) ;

        renderObject(newNotice, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        noticeBO.update(notice) ;

        renderObject(notice, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            noticeBO.delete(notice) ;
        } else {
            noticeBO.deleteAll(ids) ;
        }

        
        renderObject(notice, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }


    public void setNoticeBO(NoticeBO noticeBO) {
		this.noticeBO = noticeBO;
	}

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public NoticeSO getNoticeSO() {
        return noticeSO;
    }

    public void setNoticeSO(NoticeSO noticeSO) {
        this.noticeSO = noticeSO;
    }

}
