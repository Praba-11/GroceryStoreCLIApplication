package com.billing.app.domain.database;

import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.AnonymousException;
import com.billing.app.domain.exceptions.PrimaryKeyException;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserJdbcDAO implements UserDAO {
    User user;
    ConnectionDB connectionDB = new ConnectionDB();
    ArrayList<User> userArrayList;
     public boolean create(User user) throws ClassNotFoundException, PrimaryKeyException, AnonymousException {
         try {
             String query = "INSERT INTO users (username, type, password, firstname, lastname, phonenumber, isavailable) VALUES (?, ?, ?, ?, ?, ?, ?)";
             PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
             preparedStatement.setString(1, user.getUsername());
             preparedStatement.setString(2, user.getType());
             preparedStatement.setString(3, user.getPassword());
             preparedStatement.setString(4, user.getFirstName());
             preparedStatement.setString(5, user.getLastName());
             preparedStatement.setLong(6, user.getPhoneNumber());
             preparedStatement.setBoolean(7, user.isAvailable());
             int rowsAffected = preparedStatement.executeUpdate();
             preparedStatement.close();
             return rowsAffected > 0;
         } catch (SQLException exception) {
             if (exception.getSQLState().equals("23505")) {
                 throw new PrimaryKeyException(user.getUsername() + " already exists. " + exception.getMessage());
             } else {
                 throw new AnonymousException(exception.getMessage());
             }
         }
     }


    public boolean edit(User user) throws ClassNotFoundException, IllegalAccessException, SQLException {

        // Edit User in Database table
        Statement statement = connectionDB.getConnection().createStatement();
        int rowsAffected = 0;
        Field[] fields = user.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(user);
            String query = "UPDATE users SET " + field.getName() + " = '" + value + "' WHERE username = '" + user.getUsername() + "'";
            rowsAffected = statement.executeUpdate(query);
        }
        return rowsAffected > 0;
    }


    public boolean delete(String key, String value) throws SQLException, ClassNotFoundException {

        // Delete User in Database table
        ConnectionDB connectionDB = new ConnectionDB();
        String query = "UPDATE users SET isavailable = " + false + " WHERE " + key + " = '" + value + "'";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        return rowsAffected > 0;
    }


    public ArrayList<User> list() throws SQLException, ClassNotFoundException {

        // Returns arraylist of Users from Database table
        userArrayList = new ArrayList<>();
        ConnectionDB connectionDB = new ConnectionDB();
        String query = "SELECT * FROM users";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt(1));
            user.setType(resultSet.getString(2));
            user.setUsername(resultSet.getString(3));
            user.setPassword(resultSet.getString(4));
            user.setFirstName(resultSet.getString(5));
            user.setLastName(resultSet.getString(6));
            user.setPhoneNumber(resultSet.getLong(7));
            user.setIsAvailable(resultSet.getBoolean(8));
            userArrayList.add(user);
        }
        statement.close();
        resultSet.close();
        return userArrayList;

    }


     public User getUser(String username) throws ClassNotFoundException, AnonymousException {
         try {
             String query = "SELECT * FROM users WHERE username = '" + username + "'";
             Statement statement = connectionDB.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query);
             while (resultSet.next()) {
                 user = new User();
                 user.setId(resultSet.getInt(1));
                 user.setType(resultSet.getString(2));
                 user.setUsername(resultSet.getString(3));
                 user.setPassword(resultSet.getString(4));
                 user.setFirstName(resultSet.getString(5));
                 user.setLastName(resultSet.getString(6));
                 user.setPhoneNumber(resultSet.getLong(7));
             }
             return user;
         } catch (SQLException exception) {
             throw new AnonymousException(exception.getMessage());
         }
     }


    public boolean isUsernamePresent(String username) throws SQLException, ClassNotFoundException {

        boolean flag = false;
        String query = "SELECT EXISTS(SELECT 1 FROM users WHERE username = '" + username + "')";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
            flag = resultSet.getBoolean(1);
        return flag;

    }


    public boolean isIdPresent(String id) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        String query = "SELECT EXISTS(SELECT 1 FROM users WHERE id = '" + id + "')";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
            flag = resultSet.getBoolean(1);
        return flag;
    }
}
