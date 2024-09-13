package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50), " +
                "lastname VARCHAR(50), " +
                "age TINYINT" + ")";
        try (Connection connection = Util.createConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false); // Начало транзакции

            statement.execute(createTableSQL);
            connection.commit(); // Подтверждение транзакции
            System.out.println("Таблица пользователей создана или уже существует.");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы пользователей.");
            e.printStackTrace();
            try (Connection connection = Util.createConnection()) {
                connection.rollback(); // Откат транзакции при ошибке
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.createConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false); // Начало транзакции

            statement.execute(dropTableSQL);
            connection.commit(); // Подтверждение транзакции
            System.out.println("Таблица пользователей удалена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы.");
            e.printStackTrace();
            try (Connection connection = Util.createConnection()) {
                connection.rollback(); // Откат транзакции при ошибке
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertSQL = "INSERT INTO firstlib.users (name, lastname, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            connection.setAutoCommit(false); // Начало транзакции

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            connection.commit(); // Подтверждение транзакции
            System.out.println("Пользователь успешно добавлен в базу данных.");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении пользователя в базу.");
            e.printStackTrace();
            try (Connection connection = Util.createConnection()) {
                connection.rollback(); // Откат транзакции при ошибке.
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        String deleteSQL = "DELETE FROM firstlib.users WHERE id = ?";
        try (Connection connection = Util.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            connection.setAutoCommit(false); // Начало транзакции

            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                connection.commit(); // Подтверждение транзакции
                System.out.println("Пользователь с id = " + id + " успешно удалён.");
            } else {
                System.out.println("Пользователь с id = " + id + " не найден.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя с id = " + id);
            e.printStackTrace();
            try (Connection connection = Util.createConnection()) {
                connection.rollback(); // Откат транзакции при ошибке
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        String selectSQL = "SELECT * FROM firstlib.users";
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

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
        String cleanSQL = "TRUNCATE TABLE firstlib.users";
        try (Connection connection = Util.createConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false); // Начало транзакции

            statement.executeUpdate(cleanSQL);
            connection.commit(); // Подтверждение транзакции
            System.out.println("Все данные из таблицы пользователей были удалены.");
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы пользователей.");
            e.printStackTrace();
            try (Connection connection = Util.createConnection()) {
                connection.rollback(); // Откат транзакции при ошибке
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}