package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

/**
 В методе main класса Main должны происходить следующие операции:
 Создание таблицы User(ов)
 Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
 Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
 Очистка таблицы User(ов)
 Удаление таблицы
 */
public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {

       userService.createUsersTable();

       userService.saveUser("Alex", "Ivanov", (byte) 42);
       userService.saveUser("Oleg","Petrov", (byte) 33);
       userService.saveUser("Elena", "Turova", (byte) 27);
       userService.saveUser("Olga", "Pavlova", (byte) 21);

       userService.removeUserById(2);

       List<User> allUsers = userService.getAllUsers();
       for (User user : allUsers) {
           System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();



        }
    }

