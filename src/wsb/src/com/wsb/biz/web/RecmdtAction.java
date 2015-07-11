package com.wsb.biz.web;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.CarSO;
import com.wsb.biz.entity.Customer;
import com.wsb.biz.entity.Recmdt;
import com.wsb.biz.entity.RecmdtInventory;
import com.wsb.biz.entity.RecmdtInventorySO;
import com.wsb.biz.entity.RecmdtSO;
import com.wsb.biz.entity.RecommendationOprMapDimSO;
import com.wsb.biz.service.RecmdtBO;
import com.wsb.biz.service.RecmdtInventoryBO;
import com.opensymphony.xwork2.Preparable;



@Service("biz_recmdtAction")
@Scope("prototype")
public class RecmdtAction extends BaseAction implements Preparable {
	
	private static final long serialVersionUID = 7244882365197775441L;
	
	private Recmdt recmdt;
	private RecmdtSO recmdtSO;
	private RecmdtBO recmdtBO;
	private List<RecmdtInventory> recmdtInventorys;
	private RecmdtInventoryBO recmdtInventoryBO;
	
	
	public String execute() throws Exception { 
        return this.list(); 
    }
	
	
	@Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<Recmdt> pageList = recmdtBO.query(recmdtSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }
	
	
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	Recmdt recmdt = recmdtBO.get(this.id) ;
    	RecmdtInventorySO recmdtInventorySO = new RecmdtInventorySO();
    	recmdtInventorySO.setRecmdt_code(recmdt.getId());
    	ArrayPageList<RecmdtInventory> recmdtInventoryList = recmdtInventoryBO.query(recmdtInventorySO);
    	recmdt.setRecmdtInventorys(recmdtInventoryList);
    	
    	//资料类型下拉菜单
    	getRequest().setAttribute("keyTypeList", recmdtBO.queryRecommendationKeyType());
    	
    	//资料内容下拉菜单
    	String key = "";
    	getRequest().setAttribute("keyValueList", recmdtBO.queryRecommendationByKey(key));
    	
    	//运算符下拉菜单
    	RecommendationOprMapDimSO recommendationOprMapDimSO =  new RecommendationOprMapDimSO();
    	getRequest().setAttribute("oprValueList", recmdtBO.queryRecommendationOprMapDim(recommendationOprMapDimSO));
    	
    	renderObject(recmdt, null) ; 
        return null ;  
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newRecmdt = recmdtBO.create(recmdt,recmdtInventorys) ;

        renderObject(newRecmdt, ResponseMessage.KEY_CREATE_OK) ;
        
        Recmdt recmdt = (Recmdt) newRecmdt;
        String cmds = "rt_recmdt_engine_rule.sh " + recmdt.getId();
		try {
			boolean flag = this.ExeShell(cmds);
			if(flag){
				System.out.println("=========Engine finish!========");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return null;    
        
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
    	recmdtBO.update(recmdt,recmdtInventorys) ;

        renderObject(recmdt, ResponseMessage.KEY_UPDATE_OK) ;
        
        String cmds = "rt_recmdt_engine_rule.sh " + recmdt.getId();
		try {
			
			boolean flag = this.ExeShell(cmds);
			if(flag){
				System.out.println("=========Operation succeeded!========");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return null;    
        
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
        	recmdtBO.delete(recmdt) ;
        } else {
        	recmdtBO.deleteAll(ids) ;
        }

        
        renderObject(recmdt, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }
    
    public static boolean ExeShell(String cmd){  
		String shellPath = "/usr/local/apache-tomcat-wsb/webapps/wsb/shelljob/script/";
		cmd = shellPath + cmd;
        System.out.println("===============>"+cmd);
        Runtime run = Runtime.getRuntime();
        String result = "";
        BufferedReader br=null;
        BufferedInputStream in=null;
        try {
        	System.out.println(">>>>>>>>>>>>Start Engin<<<<<<<<<<<<<");
        	Process p = run.exec(cmd);
        	if(p.waitFor() != 0){  
        		result+="No process ID";
                return false;  
        	}    
        	in = new BufferedInputStream(p.getInputStream());
        	br = new BufferedReader(new InputStreamReader(in));
        	String lineStr;
        	while ((lineStr = br.readLine()) != null) {
        		result += lineStr;
        	}
        	System.out.println(">>>>>>>>>>>>End Engin<<<<<<<<<<<<<");
        } catch (Exception e) {
        	e.printStackTrace();
        	return false;
        }finally{
        	if(br!=null){
        		try {
        			br.close();
        			in.close();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
//        	logger.info("ShellUtil.ExeShell=>"+result);
        	System.out.println(">>>>>>>>>>>>result:<<<<<<<<<<<<<"+result);
        }
        return true;
    }

	public Recmdt getRecmdt() {
		return recmdt;
	}


	public void setRecmdt(Recmdt recmdt) {
		this.recmdt = recmdt;
	}


	public RecmdtSO getRecmdtSO() {
		return recmdtSO;
	}


	public void setRecmdtSO(RecmdtSO recmdtSO) {
		this.recmdtSO = recmdtSO;
	}


	public RecmdtBO getRecmdtBO() {
		return recmdtBO;
	}


	public void setRecmdtBO(RecmdtBO recmdtBO) {
		this.recmdtBO = recmdtBO;
	}


	public List<RecmdtInventory> getRecmdtInventorys() {
		return recmdtInventorys;
	}


	public void setRecmdtInventorys(List<RecmdtInventory> recmdtInventorys) {
		this.recmdtInventorys = recmdtInventorys;
	}


	public void setRecmdtInventoryBO(RecmdtInventoryBO recmdtInventoryBO) {
		this.recmdtInventoryBO = recmdtInventoryBO;
	}


	public RecmdtInventoryBO getRecmdtInventoryBO() {
		return recmdtInventoryBO;
	}
    
    
    
    
}