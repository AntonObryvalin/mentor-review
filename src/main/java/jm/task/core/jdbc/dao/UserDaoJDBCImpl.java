package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс UserDaoJDBCImpl реализует CRUD-операции для сущности User
 * и отвечает за взаимодействие с базой данных через SQL-запросы.
 * Использует Util для получения соединений с БД. Сервисный слой
 * (UserServiceImpl) вызывает методы этого класса для работы с данными.
 */

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        // Определяем SQL-запрос для создания таблицы пользователей, если она еще не существует.
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" + "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50), " + "lastname VARCHAR(50), " + "age TINYINT" + ")";

        // Получаем соединение с базой данных
        try (Connection connection = Util.createConnection(); // Используем метод createConnection из класса Util
             Statement statement = connection.createStatement()) {
            // Statement — это объект, который позволяет отправлять SQL-запросы к базе данных

            // Выполняем SQL-запрос для создания таблицы
            statement.execute(createTableSQL);
            System.out.println("Таблица пользователей создана или уже существует.");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы пользователей.");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        // SQL-запрос для удаления таблицы, если она существует.
        String dropTableSQL = "DROP TABLE IF EXISTS users";

        // Получаем соединение с базой данных
        try (Connection connection = Util.createConnection(); // Используем метод createConnection из класса Util
             // Statement — это объект, который позволяет отправлять SQL-запросы к базе данных
             Statement statement = connection.createStatement()) {

            // Выполняем SQL-запрос для удаления таблицы
            statement.execute(dropTableSQL);
            System.out.println("Таблица пользователей удалена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы.");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        // Определяем SQL-запрос для вставки данных пользователя в таблицу.
        // Используем параметры (?, ?, ?) вместо реальных значений для безопасной вставки через PreparedStatement.
        String insertSQL = "INSERT INTO firstlib.users (name, lastname, age) VALUES (?, ?, ?)";

        // Получаем соединение с базой данных с помощью метода createConnection из класса Util.
        try (Connection connection = Util.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) { // Создаем PreparedStatement

            // Устанавливаем значения для параметров запроса
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            // Выполняем SQL-запрос для вставки данных
            preparedStatement.executeUpdate();
            System.out.println("Пользователь успешно добавлен в базу данных.");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении пользователя в базу.");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        // SQL-запрос для удаления пользователя по его ID.
        String deleteSQL = "DELETE FROM firstlib.users WHERE id = ?";

        // Используем конструкцию try-with-resources для автоматического закрытия соединения и PreparedStatement
        try (Connection connection = Util.createConnection(); // Получаем соединение с базой данных
             // Создаем PreparedStatement и передаем SQL-запрос
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            // Устанавливаем значение параметра запроса (ID пользователя)
            preparedStatement.setLong(1, id);

            // Выполняем запрос и получаем количество затронутых строк
            int rowsAffected = preparedStatement.executeUpdate();

            // Проверяем, было ли удаление успешным
            if (rowsAffected > 0) {
                System.out.println("Пользователь с id = " + id + " успешно удалён.");
            } else {
                System.out.println("Пользователь с id = " + id + " не найден.");
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя с id = " + id);
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        // SQL-запрос для получения всех пользователей из таблицы
        String selectSQL = "SELECT * FROM firstlib.users";

        // Инициализируем пустой список, куда будут добавляться объекты User
        List<User> users = new ArrayList<>();

        // Получаем соединение с базой данных и выполняем запрос
        try (Connection connection = Util.createConnection(); // Получаем соединение
             Statement statement = connection.createStatement(); // Создаем Statement
             ResultSet resultSet = statement.executeQuery(selectSQL)) { // Выполняем запрос и получаем результат

            // Проходим по результату (ResultSet) и создаем объекты User
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");

                users.add(new User(name, lastname, age));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка пользователей.");
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        // SQL-запрос для очистки таблицы пользователей
        String cleanSQL = "TRUNCATE TABLE firstlib.users";

        // Получаем соединение с базой данных и выполняем запрос
        try (Connection connection = Util.createConnection();  // Получаем соединение
             Statement statement = connection.createStatement()) {  // Создаем Statement

            // Выполняем запрос для очистки таблицы
            statement.executeUpdate(cleanSQL);
            System.out.println("Все данные из таблицы пользователей были удалены.");

        } catch (SQLException e) {
            // Обработка ошибок при выполнении SQL-запроса
            System.out.println("Ошибка при очистке таблицы пользователей.");
            e.printStackTrace();
        }
    }
}
