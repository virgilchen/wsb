package com.globalwave.system.web.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Pid {
	// 1-search/2-create/3-update/4-delete
	// System Module - 0 ~ 5000
	// user/org/priv - 100/110/120
	// dict - 130
	// ElectricityProject Module - 9000
	/**
	 * 不作权限检查
	 */
    public final static short DO_NOT_CHECK = -1 ;
    
    /**
     * 登录后，即可访问权限
     */
    public final static short LOGINED = -2 ;
    
    /**
     * N/A
     */
    public final static short NA = -3 ;
    
    /**
     * 用于二次登录
     */
    public final static short LOGIN_FORWARD = 9999 ;
    
    public boolean ignore() default false ;
    
    public boolean log() default true ;

    /**
     * Max Value is 32767 
     * @return
     */
    public short value() default NA;
    /**
     * Max Value is 32767 
     * @return
     */
    public short[] values() default {};
}
