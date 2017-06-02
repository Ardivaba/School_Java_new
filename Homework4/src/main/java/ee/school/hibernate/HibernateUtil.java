package ee.school.hibernate;

import java.io.File;
import java.util.Set;

import javax.persistence.Entity;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;

/**
 * Used by HibernateStraightUsageExample
 * @author Mikk Mangus
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {

        try {
            Configuration configuration = new Configuration()
                .configure(new File("hibernate.cfg.xml"));
                // Using Reflections (org.reflections->reflections)
                // to find annotated classes automatically
                //.addAnnotatedClass(Auto.class);
            
            Reflections reflections = new Reflections("ee.itcollege.hibernate.entity");
            Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
            for (Class<?> entity : entities) {
                configuration.addAnnotatedClass(entity);
            }
            
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
