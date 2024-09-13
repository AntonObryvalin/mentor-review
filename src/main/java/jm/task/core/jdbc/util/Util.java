package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

// Класс для управления соединениями с базой данных и Hibernate
public class Util {

    // JDBC конфигурация
    private static final String URL = "jdbc:mysql://localhost:3306/firstlib";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "83632618";

    // Создание соединения с базой данных
    public static Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Соединение с БД установлено");
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к БД");
            e.printStackTrace();
        }
        return connection;
    }

    // ***************** Конфигурация для Hibernate *****************
    private static SessionFactory sessionFactory;

    // Инициализация SessionFactory для Hibernate
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure(); // Здесь можно указать путь к файлу конфигурации, если используется XML
                configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/firstlib");
                configuration.setProperty("hibernate.connection.username", "root");
                configuration.setProperty("hibernate.connection.password", "83632618");
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");
                configuration.setProperty("hibernate.show_sql", "true");

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("SessionFactory создан");
            } catch (Exception e) {
                System.out.println("Ошибка при создании SessionFactory");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    // Закрытие SessionFactory
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("SessionFactory закрыт");
        }
    }
}