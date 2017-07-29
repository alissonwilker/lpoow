package br.edu.ifb.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import br.edu.ifb.model.Employee;

@WebListener
public class HibernateSessionFactoryListener implements ServletContextListener {

	private final Logger logger = Logger.getLogger(HibernateSessionFactoryListener.class);
	
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	SessionFactory sessionFactory = (SessionFactory) servletContextEvent.getServletContext().getAttribute("SessionFactory");
    	if(sessionFactory != null && !sessionFactory.isClosed()){
    		logger.info("Fechando o sessionFactory");
    		sessionFactory.close();
    	}
		logger.info("Liberado o recurso Hibernate sessionFactory");
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	Configuration configuration = new Configuration();
    	configuration.configure("hibernate.cfg.xml");
    	configuration.addAnnotatedClass(Employee.class);
		logger.info("Hibernate Configuration criada com sucesso");
    	
    	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
    	logger.info("Hibernate ServiceRegistry criado com sucesso");
    	
    	SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
    	logger.info("Hibernate SessionFactory criada com sucesso");
    	servletContextEvent.getServletContext().setAttribute("SessionFactory", sessionFactory);
    	
    	logger.info("Hibernate SessionFactory configurada com sucesso");
    }
	
}