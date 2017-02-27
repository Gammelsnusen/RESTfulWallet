package se.leotest.db.query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hittat på nätet för att få Hibernate att snurra
 * 
 * Skapar upp en gemensam instans av Hibernates SessionFactory utifrån dess config-fil
 * 
 * @author Andreas
 *
 */
public class DBConnect {
    
    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
  
    private static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
  
    private static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
    
    /**
     * Returnerar en session mot DB
     * 
     * @return Session
     */
    public static Session getSession() {
        return getSessionFactory().openSession();
    }
}
