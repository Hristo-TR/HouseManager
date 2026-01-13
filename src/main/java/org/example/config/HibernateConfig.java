package org.example.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateConfig {
    private static final Logger logger = LoggerFactory.getLogger(HibernateConfig.class);
    private static SessionFactory sessionFactory;

    static {
        try {
            logger.info("Initializing Hibernate SessionFactory...");
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
            logger.info("Hibernate SessionFactory initialized successfully");
        } catch (Exception e) {
            logger.error("SessionFactory creation failed", e);
            System.err.println("SessionFactory creation failed: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            logger.info("Shutting down Hibernate SessionFactory...");
            sessionFactory.close();
            logger.info("Hibernate SessionFactory closed");
        }
    }
}
