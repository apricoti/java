package jpa.web;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Only one instance of EntityManagerFactory is needed per application.
 * It is used by individual requests to create an EntityManager for the
 * scope of that request. 
 * 
 * This class creates the EntityManagerFactory when the application
 * starts and closes it when the application stops. 
 * 
 * Other classes can access the EntityManagerFactory like this:
 * 
 * EntityManager em;
 * try {
 *   em = PersistenceManager.getEntityManagerFactory().createEntityManager();
 *   // use the entity manager
 * }
 * finally {
 *   if( em != null )
 *     em.close();
 * }
 * 
 */
public class PersistenceManager implements ServletContextListener {
    private static final EntityManagerFactory entityManagerFactory;

    static {
        // use commons-configuration to populate a Properties object
    	// to pass to the the createEntityManagerFactory method...
        entityManagerFactory = Persistence.createEntityManagerFactory("unit name"/*,properties*/);
    }

    public static EntityManagerFactory getEntityManagerFactory() { return entityManagerFactory; }
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println(String.format("PersistenceManager: Context initialized, EntityManagerFactory is %s", entityManagerFactory.isOpen() ? "open" : "closed"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if( entityManagerFactory.isOpen() ) {
            entityManagerFactory.close();
        }
        System.out.println("PersistenceManager: Closed EntityManagerFactory");
    }

}
