package com.globalwave.util;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataFilterUtil {

	private static Log log = LogFactory.getLog(DataFilterUtil.class);
	
    public static void maskStar4List(List<?> list, String[] columnArray, int starSize) {
    	for(Object src : list) {
    		maskStar(src, columnArray, starSize);
    	}
    }
    public static void maskStar(Object src, String[] columnArray, int starSize) {
    	for(String column : columnArray) {
    		// starSize
    		try {
    		    BeanUtils.setProperty(src, column, null);
    		    BeanUtils.setProperty(src, column, "******");// 如果是非数据字段，则打星星
    		} catch (Exception e) {
    			log.error(e);
    		}
    	}
    }

    public static void updateFilter(Object target, Object source, String[] ignoreProperties) {
    	for (String pro : ignoreProperties) {
    		try {
    		    PropertyUtils.setProperty(target, pro, PropertyUtils.getProperty(source, pro));
    		} catch (Exception e) {
    			log.error(e);
    		}
    	}
    	
    }
}
