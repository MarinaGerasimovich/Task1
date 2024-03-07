package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String username = "postgres";
    private static final String password = "postgres";


    private Util() {

    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static SessionFactory sessionFactory;
    public static SessionFactory openConnectionHibernate() {

        //настраиваем Hibernate для подключения к базе данных
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url",url);
        properties.setProperty("hibernate.connection.username", username);
        properties.setProperty("hibernate.connection.password", password);

        properties.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");

        properties.setProperty("hibernate.show_sql","true");
        properties.setProperty("hibernate.hbm2ddl.auto","update");


        sessionFactory = configuration.addProperties(properties).addAnnotatedClass(User.class).buildSessionFactory();

        return sessionFactory;
    }


}
