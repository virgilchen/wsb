package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.wsb.biz.entity.Page;
import com.wsb.biz.entity.PageSO;
import com.wsb.biz.entity.RolePage;


@Service("pageBO")
@Scope("prototype")
@Transactional
public class PageBO extends BaseServiceImpl {

    public Page create(Page page) {  

    	Page newItem = (Page) jdbcDao.insert(page) ;
        
        return newItem;
    }
    
    public void update(Page page) {
    	
        jdbcDao.update(page) ;
    }
    

    public void delete(Page page) {
    	
        jdbcDao.delete(page) ;
        
    }

    public void deleteAll(Long[] pageIds) {
    	
        PageSO criterion = new PageSO() ;
        criterion.setIds(pageIds) ;
        jdbcDao.delete(Page.class, criterion) ;
        
    }
    
    
    public ArrayPageList<Page> query(PageSO pageSO) {

        if (pageSO == null) {
        	pageSO = new PageSO() ;
        }
        //pageSO.addDesc("page_timestamp") ;
        
        return (ArrayPageList<Page>)jdbcDao.query(pageSO, Page.class);
    }
    

    public ArrayPageList<Page> queryByRole(Long roleId) {


    	PageSO pageSO = new PageSO() ;
    	pageSO.setStaff_role_id(roleId);
        
        return (ArrayPageList<Page>)jdbcDao.queryName("bizSQLs:queryPagesByRole", pageSO, Page.class);
    }

    public ArrayPageList<RolePage> queryAccessablePagesByRole(Long roleId) {

    	PageSO pageSO = new PageSO() ;
    	pageSO.setStaff_role_id(roleId);
        
        return (ArrayPageList<RolePage>)jdbcDao.queryName("bizSQLs:queryAccessablePagesByRole", pageSO, RolePage.class);
    }


    public Page get(Long id) {  
    	Page page = new Page() ;
    	page.setId(id) ;
        page = (Page) jdbcDao.get(page) ;
        
        
        return page;
    }
    
    public static PageBO getPageBO() {
    	return (PageBO)CodeHelper.getAppContext().getBean("pageBO");
    }
    
}
