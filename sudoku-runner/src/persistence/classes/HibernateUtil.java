package persistence.classes;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Util for using Hibernate
 * 
 * @author iandreeva
 * 
 */
public class HibernateUtil {
	private static SessionFactory sessionFactory = buildSessionFactory();
	private static ServiceRegistry serviceRegistry;

	/**
	 * Builds a session factory
	 * 
	 * @return the session factory
	 */
	private static SessionFactory buildSessionFactory() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration();
			configuration.configure();
			serviceRegistry = new ServiceRegistryBuilder().applySettings(
					configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			return sessionFactory;
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Gets the session factory
	 * 
	 * @return the session factory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Shuts down
	 */
	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
}