package com.wsb.biz.web;

import org.apache.struts2.ServletActionContext;

import com.globalwave.base.BaseAction;
import com.wsb.biz.entity.Staff;

public class LoginAction extends BaseAction {
	private static final long serialVersionUID = 7244882365197775441L;
	
private String user_name;//login.jsp in
	
	private String user_password;//login.jsp in
	
	private String user_code;
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	@Override
	public String execute() throws Exception {
		{System.out.println("进入com.wsb.biz.web.loginAction.java");}
		 /**
		   * 实施一系列的用户登陆判定功能，若成功就返回一个真正的用户
		   */
		Staff staff = new Staff();//这是登陆用户的输入和前面构造性质不同
		staff.setStaff_login_profile(user_name);
		staff.setStaff_login_pwd(user_password);
		
		ServletActionContext.getRequest().getSession().setAttribute("login", staff);
		System.out.println("=====================user_name is========================"+staff.getStaff_login_profile());
		return "login_success";
	}
}
