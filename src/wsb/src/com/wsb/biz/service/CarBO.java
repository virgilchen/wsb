package com.wsb.biz.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.entity.SessionUser;
import com.globalwave.util.DataFilterUtil;
import com.wsb.biz.entity.Car;
import com.wsb.biz.entity.CarSO;


@Service("carBO")
@Scope("prototype")
@Transactional
public class CarBO extends BaseServiceImpl {
	
	public Car create(Car car) {  

    	Car newItem = (Car) jdbcDao.insert(car) ;
        
        return newItem;
    }
    
    public void update(Car car) {
    	
        jdbcDao.update(car) ;
    }
    
    public void update(List<Car> cars, Long customerId) {
    	for (Car car : cars) {
    		if (car == null) {
    			continue ;
    		}

            car.setPsdo_cust_id(customerId);

    		if (car.getId() == null) {
    			continue ;
    		}
    		
    		Car oldCar = (Car)this.jdbcDao.get(car);
            
            DataFilterUtil.updateFilter(car, oldCar, this.getRestrictedProperties());
    	}
    	
    	CarSO carSO = new CarSO();
    	carSO.setPsdo_cust_id(customerId);
    	jdbcDao.delete(Car.class, carSO);
    	
    	for (Car car : cars) {
    		if (car == null) {
    			continue ;
    		}

       		jdbcDao.insert(car);
    	}
    }
    

    public void delete(Car car) {
    	
        jdbcDao.delete(car) ;
        
    }

    public void deleteAll(Long[] carIds) {
    	
        CarSO car = new CarSO() ;
        car.setIds(carIds) ;
        jdbcDao.delete(Car.class, car) ;
        
    }
    
    
    public ArrayPageList<Car> query(CarSO carSO) {

        if (carSO == null) {
        	carSO = new CarSO() ;
        }
        carSO.addAsc("car_id") ;
        
        ArrayPageList<Car> result = (ArrayPageList<Car>)jdbcDao.query(carSO, Car.class);

        DataFilterUtil.maskStar4List(result, getRestrictedProperties(), 0);
        
        return result ;
    }



    public Car get(Long id) {  
    	Car car = new Car() ;
    	car.setId(id) ;
    	car = (Car) jdbcDao.get(car) ;
        
        DataFilterUtil.maskStar(car, getRestrictedProperties(), 0);
        
        return car;
    }
    

    private String[] getRestrictedProperties() {
    	SessionUser su = SessionUser.get();
    	String authority = StaffRoleBO.getStaffRoleBO().get(su.getStaff().getStaff_role_id()).getStaff_role_authority();
    	
    	String pros = CodeHelper.getString("CUS.CAR.R", "desc_", authority);
    	
    	if (pros == null) {
    		return new String[] {};
    	} else {
    		return pros.split(",");
    	}
    }
    

    public static CarBO getCarBO() {
    	return (CarBO)CodeHelper.getAppContext().getBean("carBO");
    }
}
