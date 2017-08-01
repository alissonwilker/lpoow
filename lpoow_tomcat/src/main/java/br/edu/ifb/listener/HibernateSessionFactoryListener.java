package br.edu.ifb.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@WebListener
public class HibernateSessionFactoryListener implements ServletContextListener {

	private final Logger logger = Logger.getLogger(HibernateSessionFactoryListener.class);

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		SessionFactory sessionFactory = (SessionFactory) servletContextEvent.getServletContext()
				.getAttribute("SessionFactory");
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			logger.info("Fechando o sessionFactory");
			sessionFactory.close();
		}
		logger.info("Liberado o recurso Hibernate sessionFactory");
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// A SessionFactory is set up once for an application! Configures
		// settings from hibernate.cfg.xml
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		logger.info("Hibernate ServiceRegistry criado com sucesso");
		try {
			SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			servletContextEvent.getServletContext().setAttribute("SessionFactory", sessionFactory);
			logger.info("Hibernate SessionFactory criada com sucesso");
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

}