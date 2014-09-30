package com.globalwave.system.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.entity.EventLog;
import com.globalwave.common.entity.EventLogSO;
import com.globalwave.common.service.EventLogBO;
import com.globalwave.system.web.annotations.Pid;
import com.opensymphony.xwork2.Preparable;

@Service("system_eventLogAction")
@Scope("prototype")
public class EventLogAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private EventLogBO eventLogBO ;
    private EventLog eventLog ;
    private EventLogSO eventLogSO ; 
    
    public String execute() throws Exception {        
        
        return this.list();        
        
    }
    

    @Pid(value=2130, log=false)
    public String list() throws Exception {  
    	//eventLogSO.setPro_eventLog_id_exclude(0L) ;// Just for loss, 取二级组织
        ArrayPageList<EventLog> pageList = eventLogBO.query(eventLogSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

/*
    //@Pid(value=134)
    @Pid(value=131)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            eventLogBO.delete(eventLog) ;
        } else {
            eventLogBO.deleteAll(ids) ;
        }
        
        renderObject(eventLog, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }
*/
    public void prepare() throws Exception {
        // TODO Auto-generated method stub
        
    }

    public void setEventLogBO(EventLogBO eventLogBO) {
		this.eventLogBO = eventLogBO;
	}

    public EventLog getEventLog() {
        return eventLog;
    }

    public void setEventLog(EventLog eventLog) {
        this.eventLog = eventLog;
    }

    public EventLogSO getEventLogSO() {
        return eventLogSO;
    }

    public void setEventLogSO(EventLogSO eventLogSO) {
        this.eventLogSO = eventLogSO;
    }

}
