package com.wsb.biz.web;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.Util;
import com.itextpdf.text.log.SysoLogger;
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
		//{System.out.println("进入com.wsb.biz.web.loginAction.java");}
		 /**
		   * 实施一系列的用户登陆判定功能，若成功就返回一个真正的用户
		   */
		System.out.println(Util.hash(staffSO.getStaff_login_pwd()));
		staff = staffBO.login(staffSO);
		
		this.getSession().setAttribute("Staff", staff);
		
		renderObject(staff, 0L) ;
		
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
