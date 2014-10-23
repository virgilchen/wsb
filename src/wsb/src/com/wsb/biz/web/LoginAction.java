package com.wsb.biz.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.common.Util;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.entity.SessionUser;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.Staff;
import com.wsb.biz.entity.StaffSO;
import com.wsb.biz.service.StaffBO;

@Service("biz_loginAction")
@Scope("prototype")
public class LoginAction extends BaseAction {
	private static final long serialVersionUID = 7244882365197775441L;
	
	private StaffBO staffBO ;
    private Staff staff ;
    private StaffSO staffSO ;
	
	@Override
	public String execute() throws Exception {
		 /**
		   * 实施一系列的用户登陆判定功能，若成功就返回一个真正的用户
		   */
		//System.out.println(Util.hash(staffSO.getStaff_login_pwd()));
		SessionUser sessionUser = staffBO.login(staffSO);

    	sessionUser.setVersionId(CodeHelper.getVersionId()) ;
        
        this.getSession().setAttribute(SessionUser.SESSION_PK, sessionUser);
        
		//this.getSession().setAttribute("Staff", staff);
		
		renderObject(staff, 1106L) ;
		
		return null;
	}


    @Pid(value=Pid.DO_NOT_CHECK)
    public String logout() throws Exception {

        this.getSession().removeAttribute(SessionUser.SESSION_PK);
        
    	renderObject(null, 1113L) ;// 1113', '用户已经成功登出！
        
        return null;
    }

	public StaffBO getStaffBO() {
		return staffBO;
	}

	public void setStaffBO(StaffBO staffBO) {
		this.staffBO = staffBO;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public StaffSO getStaffSO() {
		return staffSO;
	}

	public void setStaffSO(StaffSO staffSO) {
		this.staffSO = staffSO;
	}
	
}
