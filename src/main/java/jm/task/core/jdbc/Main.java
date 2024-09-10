package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Ivan", "Ivanov", (byte) 25);
        System.out.println("User с именем Ivan добавлен в базу данных");

        userDao.saveUser("Petr", "Petrov", (byte) 30);
        System.out.println("User с именем Petr добавлен в базу данных");

        userDao.saveUser("Sidor", "Sidorov", (byte) 35);
        System.out.println("User с именем Sidor добавлен в базу данных");

        userDao.saveUser("Anna", "Ivanova", (byte) 28);
        System.out.println("User с именем Anna добавлен в базу данных");

        for (User user : userDao.getAllUsers()) {
            System.out.println(user);
        }

        userDao.cleanUsersTable();

        userDao.dropUsersTable();
    }
}