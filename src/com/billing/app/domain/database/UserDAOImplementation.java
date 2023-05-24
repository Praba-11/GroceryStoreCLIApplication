package com.billing.app.domain.database;

import com.billing.app.domain.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImplementation implements UserDAO {
    User user;
    ConnectionDB connectionDB = new ConnectionDB();
    private List<User> userList;
    @Override
    public User create(User user) throws SQLException {
        System.out.println(user);
        String query = "INSERT INTO users (usertype, username, password, firstname, lastname, phonenumber, isavailable) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        PreparedStatement statement = setQuery(preparedStatement, user);
        statement.executeUpdate();
        return user;
    }

    @Override
    public User edit(User user) throws SQLException {
        String query = "UPDATE users SET usertype = ?, username = ?, password = ?, firstname = ?, lastname = ?, phonenumber = ?, " +
                "isavailable = ? WHERE id = ?";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        PreparedStatement statement = setQuery(preparedStatement, user);
        statement.setInt(8, user.getId());
        statement.executeUpdate();
        return user;
    }


    @Override
    public boolean delete(String username) throws SQLException {
        String query = "UPDATE users SET isavailable = " + false + " WHERE username = '" + username + "'";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        return rowsAffected > 0;
    }


    public List<User> list(int range, int page, String attribute, String searchText) throws SQLException {

        String query = "SELECT * FROM users WHERE CAST(" + attribute + " AS TEXT) ILIKE '%" + searchText + "%' " +
                "LIMIT " + range + " OFFSET " + page;
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<User> users = listUsers(resultSet);
        return users;
    }

    public List<User> list(String searchText) throws SQLException {

        String query = "SELECT * FROM users WHERE CAST(id AS TEXT) ILIKE '%" + searchText + "%' OR usertype ILIKE '%" +
                searchText + "%' OR username ILIKE '%" + searchText + "%' OR password ILIKE '%" + searchText + "%' " +
                "OR firstname ILIKE '%" + searchText + "%' OR lastname ILIKE '%" + searchText + "%' OR " +
                "CAST(phonenumber AS TEXT) ILIKE '%" + searchText + "%' OR CAST(isavailable AS TEXT) ILIKE '%" + searchText + "%'";

        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<User> users = listUsers(resultSet);
        return users;
    }


    public User find(int id) throws SQLException {
        User userFound = null;
        String query = "SELECT * FROM users WHERE id = '" + id + "'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        user = new User();
        while (resultSet.next()) {
            userFound = setUser(user, resultSet);
        }
        return userFound;
    }

    public int count() throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(*) FROM users";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count;
    }

    public User login(String username, String password) throws SQLException {
        User userfound = null;
        String query = "SELECT * FROM users WHERE username = '" + username+ "' AND password = '" + password + "'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        User user = new User();
        while (resultSet.next()) {
            userfound = setUser(user, resultSet);
        }
        return userfound;
    }

    public boolean adminExists() throws SQLException {
        int flag = 0;
        String query = "SELECT COUNT(*) FROM users WHERE usertype = 'Administrator'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            flag = resultSet.getInt(1);
        }
        return flag != 0;
    }

    private PreparedStatement setQuery(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getUserType().getValue());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getFirstName());
        preparedStatement.setString(5, user.getLastName());
        preparedStatement.setLong(6, user.getPhoneNumber());
        preparedStatement.setBoolean(7, user.isAvailable());
        return preparedStatement;
    }

    private User setUser(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getInt(1));
        user.setUserType(User.UserType.fromValue(resultSet.getString(2)));
        user.setUsername(resultSet.getString(3));
        user.setPassword(resultSet.getString(4));
        user.setFirstName(resultSet.getString(5));
        user.setLastName(resultSet.getString(6));
        user.setPhoneNumber(resultSet.getLong(7));
        user.setIsAvailable(resultSet.getBoolean(8));
        return user;
    }

    private List<User> listUsers(ResultSet resultSet) throws SQLException {
        userList = new ArrayList<>();
        while (resultSet.next()) {
            user = new User();
            User setUser = setUser(user, resultSet);
            userList.add(setUser);
        }
        return userList;
    }


}
