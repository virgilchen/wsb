package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
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
        
        return (ArrayPageList<Car>)jdbcDao.query(carSO, Car.class);
    }



    public Car get(Long id) {  
    	Car car = new Car() ;
    	car.setId(id) ;
    	car = (Car) jdbcDao.get(car) ;
        
        
        return car;
    }
}
