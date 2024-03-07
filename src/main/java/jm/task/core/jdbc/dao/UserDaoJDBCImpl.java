package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection conn = Util.open();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "name VARCHAR (255)," +
                    "last_name VARCHAR (255)," +
                    "age INTEGER )");
        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate( "DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO users " +
                "(name, last_name, age) VALUES (?,?,?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных JDBC");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM users WHERE id = ?")){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();

        try(ResultSet resultSet= conn.createStatement().executeQuery("SELECT id," +
                " name, last_name, age FROM users")) {
            while (resultSet.next()) {
                User userOne = new User(
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                userOne.setId(resultSet.getLong("id"));
                usersList.add(userOne);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try(Statement statement = conn.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
