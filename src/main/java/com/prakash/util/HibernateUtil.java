package com.prakash.util;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.prakash.entity.Employee;
 
@Repository
public class HibernateUtil  {
	
	@Autowired
    private SessionFactory sessionFactory;
		
    public Serializable   create(final Employee entity) {
        return sessionFactory.getCurrentSession().save(entity);        
    }
    
    public Employee update(final Employee entity) {
        sessionFactory.getCurrentSession().update(entity);   
        return entity;
    }
    
	public  void delete(final Employee employee) {
		sessionFactory.getCurrentSession().delete(employee);
	}

	public void delete(Serializable id, Class<Employee> entityClass) {
		Employee entity = fetchById(id, entityClass);
		delete(entity);
	}
    
    @SuppressWarnings("unchecked")	
    public  List<Employee> fetchAll(Class<Employee> entityClass) {        
        return sessionFactory.getCurrentSession().createQuery(" FROM "+entityClass.getName()).list();        
    }
  
    @SuppressWarnings("rawtypes")
	public <Employee> List fetchAll(String query) {        
        return sessionFactory.getCurrentSession().createSQLQuery(query).list();        
    }
    
    @SuppressWarnings("unchecked")
	public Employee fetchById(Serializable id, Class<Employee> entityClass) {
        return (Employee)sessionFactory.getCurrentSession().get(entityClass, id);
    }
    
    
	
}
