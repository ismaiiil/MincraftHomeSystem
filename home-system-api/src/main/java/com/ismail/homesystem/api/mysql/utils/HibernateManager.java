package com.ismail.homesystem.api.mysql.utils;

import java.util.Properties;

import com.ismail.homesystem.api.gson.deserializers.JsonDeserializer;
import com.ismail.homesystem.api.gson.models.DatabaseConfig;
import com.ismail.homesystem.api.mysql.models.PlayerHouse;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateManager {
    public static String CONFIG_PATH = "config.json";
    private static SessionFactory sessionFactory;
    public static SessionFactory getHibernate() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                initializeConfig(configuration);

                //add database models here:
                configuration.addAnnotatedClass(PlayerHouse.class);

                buildConfiguration(configuration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void closeAllSessions(){
        sessionFactory.close();
    }

    private static void buildConfiguration(Configuration configuration) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    private static void initializeConfig(Configuration configuration) {
        Properties settings = new Properties();
        DatabaseConfig databaseConfig = new JsonDeserializer(CONFIG_PATH).getConfigWrapper().getDatabaseConfig();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, databaseConfig.getDatabaseURL());
        settings.put(Environment.USER, databaseConfig.getUser());
        settings.put(Environment.PASS, databaseConfig.getPassword());
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "drop-and-create");
        configuration.setProperties(settings);
    }

}
