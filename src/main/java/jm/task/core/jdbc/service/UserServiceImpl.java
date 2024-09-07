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
    private final UserDao userDao = new UserDaoJDBCImpl();


    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }

}
