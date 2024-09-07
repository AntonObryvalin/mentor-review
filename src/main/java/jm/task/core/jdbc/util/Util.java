/**
 * Создает соединение с базой данных MySQL с использованием JDBC.
 * Проверяет состояние соединения и выводит соответствующие сообщения.
 * Закрывает соединение после использования, что важно для освобождения ресурсов.
 */
package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Класс Util предоставляет утилитарные методы для работы с базой данных.
 * Включает метод для создания соединения с базой данных.
 * Этот класс инкапсулирует детали подключения к базе данных, упрощая взаимодействие
 * с базой в других классах (например, в DAO или сервисах).
 */

public class Util {
    // URL подключения к базе данных. Здесь указываем протокол JDBC, адрес сервера, порт и имя базы данных.
    private static final String URL = "jdbc:mysql://localhost:3306/firstlib";
    // jdbc(интерфейс):mysql(тип базы данных):// — указывает на использование JDBC для MySQL.
    // localhost — указывает на локальный сервер базы данных.
    // :3306 — указывает порт MySQL, на котором сервер слушает подключения.
    // /firstlib — указывает на имя базы данных, к которой вы хотите подключиться.

    private static final String USERNAME = "root";
    private static final String PASSWORD = "83632618";


    // Метод создает соединение с базой данных и возвращает его
    public static Connection createConnection() {
        // Переменная для хранения соединения с базой данных.
        Connection connection = null;
        try {
            // Регистрация драйвера MySQL. Это действие может быть не обязательно
            // для новых версий JDBC, так как драйверы автоматически регистрируются

            // DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            // DriverManager — это класс из пакета java.sql, который управляет списком драйверов базы данных.
            // Метод registerDriver класса DriverManager используется для регистрации драйвера базы данных.
            // com.mysql.cj.jdbc.Driver представляет собой реализацию JDBC-драйвера для MySQL

            // Class.forName("com.mysql.cj.jdbc.Driver");
            // Метод Class.forName в Java используется для динамической загрузки классов в рантайме
            // Class Это класс из пакета java.lang
            // forName - статический метод который загружает класс с указанным именем
            // com.mysql.cj.jdbc.Driver - полное имя класса драйвера JDBC


            // Установка соединения с базой данных с использованием URL, имени пользователя и пароля.
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // ищет среди зарегистрированных драйверов тот, который поддерживает указанный URL

            System.out.println("Соединение с БД установлено");
        } catch (SQLException e) {
            // Обработка исключения в случае ошибок при подключении.
            System.out.println("Ошибка при подключении к БД");
            e.printStackTrace();
        }
        // Возвращение объекта соединения (или null в случае ошибки).
        return connection;
    }

    // Метод main для тестирования подключения к базе данных.
    public static void main(String[] args) {
        // Получение соединения с базой данных.
        Connection connection = createConnection();
        if (connection != null) {
            try {
                // Проверка, закрыто ли соединение. Если нет, выводим сообщение.
                if (!connection.isClosed()) {
                    System.out.println("Соединение с БД активное");
                }
            } catch (SQLException e) {
                // Обработка исключения в случае ошибок при проверке состояния соединения.
                e.printStackTrace();
            } finally {
                try {
                    // Закрытие соединения в блоке finally для обеспечения закрытия независимо от результатов проверки
                    connection.close();
                    System.out.println("Соединение закрыто");
                } catch (SQLException e) {
                    // Обработка исключения в случае ошибок при закрытии соединения.
                    e.printStackTrace();
                }
            }
        }
    }
}