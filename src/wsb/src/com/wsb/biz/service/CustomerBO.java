package com.wsb.biz.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.system.entity.SessionUser;
import com.globalwave.util.DataFilterUtil;
import com.wsb.biz.entity.Car;
import com.wsb.biz.entity.CarSO;
import com.wsb.biz.entity.Customer;
import com.wsb.biz.entity.CustomerSO;
import com.wsb.biz.entity.MemberSO;
import com.wsb.biz.entity.Member;

@Service("customerBO")
@Scope("prototype")
@Transactional
public class CustomerBO extends BaseServiceImpl {

	public Customer create(Customer customer, List<Car> cars) {

		Customer customerSO = new Customer();
		customerSO.setCust_code((customer.getCust_code()));
		if (this.jdbcDao.find(customerSO) != null) {
			throw new BusinessException(1114L);// 1114', '客户号已经被使用，请使用其它客户号
		}

		Customer newItem = (Customer) jdbcDao.insert(customer);
		if (cars != null) {
			for (int i = 0; i < cars.size(); i++) {
				cars.get(i).setPsdo_cust_id(newItem.getId());
				jdbcDao.insert(cars.get(i));
			}
		}

		String cmds = "sh /usr/local/apache-tomcat-wsb/webapps/shelljob/script/rt_recmdt_engine_cust.sh "
				+ newItem.getId();
//		String[] cmd = {
//				"/usr/local/apache-tomcat-wsb/webapps/shelljob/script/", "-c",
//				"rt_recmdt_engine_cust_test.sh" +newItem.getId()};
		// String cmds =
		// "/usr/local/apache-tomcat-wsb/webapps/shelljob/script/rt_recmdt_engine_cust_test.sh"+newItem.getId();
		try {
			
			boolean flag = this.ExeShell(cmds);
			if(flag){
				System.out.println("=========Engine finish!========");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return newItem;
	}

	/*
	 * public void update(Customer customer, List<Car> cars) { CarSO carSO = new
	 * CarSO(); carSO.setPsdo_cust_id(customer.getId());
	 * jdbcDao.delete(Car.class, carSO);
	 * 
	 * if(cars != null){ jdbcDao.update(customer) ;
	 * 
	 * for(int i=0;i<cars.size();i++){ if(cars.get(i) != null){
	 * cars.get(i).setPsdo_cust_id(customer.getId());
	 * jdbcDao.insert(cars.get(i)); } } } }
	 */

	public void update(Customer customer, List<Car> cars) {

		this.update(customer);

		if (cars != null) {
			CarBO.getCarBO().update(cars, customer.getId());
		}

		String cmds = "sh /usr/local/apache-tomcat-wsb/webapps/shelljob/script/rt_recmdt_engine_cust.sh "
				+ customer.getId();
//		String[] cmd = {
//				"/usr/local/apache-tomcat-wsb/webapps/shelljob/script/", "-c",
//				"rt_recmdt_engine_cust_test.sh" +customer.getId()};
		// String cmds =
		// "/usr/local/apache-tomcat-wsb/webapps/shelljob/script/rt_recmdt_engine_cust_test.sh"+customer.getId();
		try {
			boolean flag = this.ExeShell(cmds);
			if(flag){
				System.out.println("=========Engine finish!========");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Customer customer) {
		Customer oldCustomer = (Customer) this.jdbcDao.get(customer);

		DataFilterUtil.updateFilter(customer, oldCustomer,
				this.getRestrictedProperties());

		jdbcDao.update(customer);
	}

	public void delete(Customer customer) {
		CarSO carSO = new CarSO();
		carSO.setPsdo_cust_id(customer.getId());
		jdbcDao.delete(Car.class, carSO);

		MemberSO memberSO = new MemberSO();
		memberSO.setPsdo_cust_id(customer.getId().toString());
		jdbcDao.delete(Member.class, memberSO);

		jdbcDao.delete(customer);

	}

	public void deleteAll(Long[] customerIds) {
		for (int i = 0; i < customerIds.length; i++) {
			CarSO carSO = new CarSO();
			carSO.setPsdo_cust_id(customerIds[i]);
			jdbcDao.delete(Car.class, carSO);

			MemberSO memberSO = new MemberSO();
			memberSO.setPsdo_cust_id(customerIds[i].toString());
			jdbcDao.delete(Member.class, memberSO);
		}

		CustomerSO customer = new CustomerSO();
		customer.setIds(customerIds);
		jdbcDao.delete(Customer.class, customer);

	}

	public ArrayPageList<Customer> query(CustomerSO customerSO) {

		if (customerSO == null) {
			customerSO = new CustomerSO();
		}

		if ("".equalsIgnoreCase(customerSO.getBusiness_name())) {
			customerSO.setBusiness_name(null);
		}
		if ("".equalsIgnoreCase(customerSO.getCar_engine_no())) {
			customerSO.setCar_engine_no(null);
		}
		if ("".equalsIgnoreCase(customerSO.getCar_framework_no())) {
			customerSO.setCar_framework_no(null);
		}
		if ("".equalsIgnoreCase(customerSO.getCust_id())) {
			customerSO.setCust_id(null);
		}
		if ("".equalsIgnoreCase(customerSO.getProd_pack_name())) {
			customerSO.setProd_pack_name(null);
		}

		// 组装年月日字段，查询条件商品包到期时间查询
		// if(!"".equalsIgnoreCase(customerSO.getProd_pack_expire_date_year())){
		// if(!"".equalsIgnoreCase(customerSO.getProd_pack_expire_date_month())){
		// if(!"".equalsIgnoreCase(customerSO.getProd_pack_expire_date_day())){
		// customerSO.setProd_pack_expire_date_begin(customerSO.getProd_pack_expire_date_year()+"-"
		// +customerSO.getProd_pack_expire_date_month()+"-"+customerSO.getProd_pack_expire_date_day());
		// customerSO.setProd_pack_expire_date_end(customerSO.getProd_pack_expire_date_year()+"-"
		// +customerSO.getProd_pack_expire_date_month()+"-"+customerSO.getProd_pack_expire_date_day());
		// }else{
		// customerSO.setProd_pack_expire_date_begin(customerSO.getProd_pack_expire_date_year()+"-"
		// +customerSO.getProd_pack_expire_date_month()+"-01");
		// customerSO.setProd_pack_expire_date_end(customerSO.getProd_pack_expire_date_year()+"-"
		// +customerSO.getProd_pack_expire_date_month()+"-31");
		// }
		// }else{
		// customerSO.setProd_pack_expire_date_begin(customerSO.getProd_pack_expire_date_year()+"-01-01");
		// customerSO.setProd_pack_expire_date_end(customerSO.getProd_pack_expire_date_year()+"-12-31");
		// }
		// }else{
		// customerSO.setProd_pack_expire_date_begin(null);
		// customerSO.setProd_pack_expire_date_end(null);
		// }

		if ("".equalsIgnoreCase(customerSO.getProd_pack_expire_date_begin())) {
			customerSO.setProd_pack_expire_date_begin(null);
		}
		if ("".equalsIgnoreCase(customerSO.getProd_pack_expire_date_end())) {
			customerSO.setProd_pack_expire_date_end(null);
		}

		// customerSO.addDesc("customer_timestamp") ;

		// ArrayPageList<Customer> result =
		// (ArrayPageList<Customer>)jdbcDao.query(customerSO, Customer.class);

		ArrayPageList<Customer> result = (ArrayPageList<Customer>) jdbcDao
				.queryName("bizSQLs:queryCustomerHighClass", customerSO,
						Customer.class);
		// ArrayPageList<Customer> newResult = new ArrayPageList<Customer>();
		// for(Customer customer : result){
		// String car_no = "";
		// car_no = CarBO.getCarBO().getCar_no(customer.getId());
		// customer.setCar_no(car_no);
		//
		// if(customer.getMember_id() != null){
		// String member_login_id = "";
		// Member member = new Member();
		// member = MemberBO.getMemberBO().get(customer.getMember_id());
		// if(member != null){
		// member_login_id = member.getMember_login_id();
		// }
		// customer.setMember_login_id(member_login_id);
		// }
		// newResult.add(customer);
		// }

		DataFilterUtil.maskStar4List(result, getRestrictedProperties(), 0);

		return result;
	}

	private String[] getRestrictedProperties() {
		SessionUser su = SessionUser.get();
		String authority = StaffRoleBO.getStaffRoleBO()
				.get(su.getStaff().getStaff_role_id())
				.getStaff_role_authority();

		String pros = CodeHelper.getString("CUS.PROS.R", "desc_", authority);

		if (pros == null) {
			return new String[] {};
		} else {
			return pros.split(",");
		}
	}

	public Customer get(Long id) {
		Customer customer = new Customer();
		customer.setId(id);
		customer = (Customer) jdbcDao.get(customer);

		DataFilterUtil.maskStar(customer, getRestrictedProperties(), 0);

		return customer;
	}

	public boolean callShell(String[] cmds) throws Exception {
		
		// String cmd = "d:\\java.bat";
		System.out.println("=========Start Engine========[" + cmds + "]");
		Process p = Runtime.getRuntime().exec(cmds);
		
		p.waitFor();
		return true;
	}

	public static boolean ExeShell(String shellPath){  
//      String cmd = "/usr/bin/rsync -av /var/log/www.solive.kv/access_log testuser@10.0.1.219::store --password-file=/etc/client/rsync.pwd";
//        String cmd = "sh /www/www.itvpad.com/www/manager/synRes.sh";
		String cmd = shellPath;
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

	public static CustomerBO getCustomerBO() {
		return (CustomerBO) CodeHelper.getAppContext().getBean("customerBO");
	}

	public static void main(String[] args) throws Exception {
		String cmd = "cmd /c dir";

		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String s = "";
		while ((s = in.readLine()) != null) {
			System.out.println(s);
		}
		System.out.println("finished...");

	}

}
