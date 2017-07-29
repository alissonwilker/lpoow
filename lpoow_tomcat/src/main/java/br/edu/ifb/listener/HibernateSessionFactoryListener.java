package br.edu.ifb.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import br.edu.ifb.model.Employee;

@WebListener
public class HibernateSessionFactoryListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	SessionFactory sessionFactory = (SessionFactory) servletContextEvent.getServletContext().getAttribute("SessionFactory");
    	if(sessionFactory != null && !sessionFactory.isClosed()){
    		sessionFactory.close();
    	}
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	Configuration configuration = new Configuration();
    	configuration.configure("hibernate.cfg.xml");
    	configuration.addAnnotatedClass(Employee.class);
    	
    	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
    	SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
    	
    	servletContextEvent.getServletContext().setAttribute("SessionFactory", sessionFactory);
    }
	
}