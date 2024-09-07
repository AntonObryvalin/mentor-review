package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Класс UserServiceImpl реализует интерфейс UserService и служит для управления бизнес-логикой
 * работы с пользователями. Служит промежуточным слоем между пользовательским интерфейсом и уровнем данных.
 * Отвечает за создание и удаление таблиц, добавление, удаление и получение пользователей,
 * а также очистку таблицы. Взаимодействует с DAO (Data Access Object) через интерфейс UserDao.
 */

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoJDBCImpl(); // Инициализируем объект класса UserDaoJDBCImpl

    // Метод для создания таблицы пользователей
    public void createUsersTable() {
        userDao.createUsersTable(); // Вызываем метод из UserDaoJDBCImpl
    }

    // Метод для удаления таблицы пользователей
    public void dropUsersTable() {
        userDao.dropUsersTable(); // Вызываем метод из UserDaoJDBCImpl
    }

    // Метод для сохранения нового пользователя
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age); // Вызываем метод из UserDaoJDBCImpl
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    // Метод для удаления пользователя по ID
    public void removeUserById(long id) {
        userDao.removeUserById(id); // Вызываем метод из UserDaoJDBCImpl
    }

    // Метод для получения всех пользователей
    public List<User> getAllUsers() {
        return userDao.getAllUsers(); // Вызываем метод из UserDaoJDBCImpl
    }

    // Метод для очистки таблицы пользователей
    public void cleanUsersTable() {
        userDao.cleanUsersTable(); // Вызываем метод из UserDaoJDBCImpl
    }

}
