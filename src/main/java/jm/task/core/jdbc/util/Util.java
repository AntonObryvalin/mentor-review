
package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/firstlib";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "83632618";

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

    public static void main(String[] args) {

        Connection connection = createConnection();
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    System.out.println("Соединение с БД активное");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                    System.out.println("Соединение закрыто");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}