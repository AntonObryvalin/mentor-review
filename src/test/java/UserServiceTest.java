import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
/**
 * Класс UserServiceTest содержит тесты для проверки корректности работы методов класса UserServiceImpl.
 * Тесты включают создание и удаление таблицы пользователей, сохранение, удаление и получение пользователей,
 * а также очистку таблицы пользователей. Используется библиотека JUnit для выполнения тестов и проверки
 * корректности методов.
 */
public class UserServiceTest {
    // Создаем экземпляр UserService для тестирования
    private final UserService userService = new UserServiceImpl();

    // Тестовые данные для пользователей
    private final String testName = "Ivan";
    private final String testLastName = "Ivanov";
    private final byte testAge = 5;

    /**
     * Тестирование метода dropUsersTable.
     * Проверяет, что метод корректно удаляет таблицу пользователей.
     */
    @Test
    public void dropUsersTable() {
        try {
            // Удаляем таблицу пользователей
            userService.dropUsersTable();
            // Пытаемся удалить таблицу еще раз (чтобы проверить устойчивость к повторному удалению)
            userService.dropUsersTable();
        } catch (Exception e) {
            // Если возникает исключение, тест завершается неудачей
            Assert.fail("При тестировании удаления таблицы произошло исключение\n" + e);
        }
    }

    /**
     * Тестирование метода createUsersTable.
     * Проверяет, что метод корректно создает таблицу пользователей.
     */
    @Test
    public void createUsersTable() {
        try {
            // Удаляем таблицу, если она существует, и создаем новую таблицу
            userService.dropUsersTable();
            userService.createUsersTable();
        } catch (Exception e) {
            // Если возникает исключение, тест завершается неудачей
            Assert.fail("При тестировании создания таблицы пользователей произошло исключение\n" + e.getMessage());
        }
    }

    /**
     * Тестирование метода saveUser.
     * Проверяет, что пользователь корректно добавляется в базу данных.
     */
    @Test
    public void saveUser() {
        try {
            // Удаляем таблицу, создаем новую и сохраняем пользователя
            userService.dropUsersTable();
            userService.createUsersTable();
            userService.saveUser(testName, testLastName, testAge);

            // Проверяем, что пользователь был корректно добавлен в базу данных
            User user = userService.getAllUsers().get(0);

            if (!testName.equals(user.getName())
                    || !testLastName.equals(user.getLastName())
                    || testAge != user.getAge()) {
                Assert.fail("User был некорректно добавлен в базу данных");
            }

        } catch (Exception e) {
            // Если возникает исключение, тест завершается неудачей
            Assert.fail("Во время тестирования сохранения пользователя произошло исключение\n" + e);
        }
    }

    /**
     * Тестирование метода removeUserById.
     * Проверяет, что пользователь корректно удаляется по ID.
     */
    @Test
    public void removeUserById() {
        try {
            // Удаляем таблицу, создаем новую и сохраняем пользователя
            userService.dropUsersTable();
            userService.createUsersTable();
            userService.saveUser(testName, testLastName, testAge);

            // Удаляем пользователя с ID = 1
            userService.removeUserById(1L);
        } catch (Exception e) {
            // Если возникает исключение, тест завершается неудачей
            Assert.fail("При тестировании удаления пользователя по id произошло исключение\n" + e);
        }
    }

    /**
     * Тестирование метода getAllUsers.
     * Проверяет, что метод корректно возвращает список пользователей.
     */
    @Test
    public void getAllUsers() {
        try {
            // Удаляем таблицу, создаем новую, сохраняем пользователя и проверяем количество пользователей
            userService.dropUsersTable();
            userService.createUsersTable();
            userService.saveUser(testName, testLastName, testAge);
            List<User> userList = userService.getAllUsers();

            if (userList.size() != 1) {
                Assert.fail("Проверьте корректность работы метода сохранения пользователя/удаления или создания таблицы");
            }
        } catch (Exception e) {
            // Если возникает исключение, тест завершается неудачей
            Assert.fail("При попытке достать всех пользователей из базы данных произошло исключение\n" + e);
        }
    }

    @Test
    public void cleanUsersTable() {
        try {
            // Удаляем таблицу, создаем новую, сохраняем пользователя и очищаем таблицу
            userService.dropUsersTable();
            userService.createUsersTable();
            userService.saveUser(testName, testLastName, testAge);
            userService.cleanUsersTable();

            // Проверяем, что таблица очищена
            if (userService.getAllUsers().size() != 0) {
                Assert.fail("Метод очищения таблицы пользователей реализован не корректно");
            }
        } catch (Exception e) {
            // Если возникает исключение, тест завершается неудачей
            Assert.fail("При тестировании очистки таблицы пользователей произошло исключение\n" + e);
        }
    }
}
