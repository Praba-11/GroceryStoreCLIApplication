package com.billing.app.domain.database;

import com.billing.app.domain.entity.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserJdbcDAO implements UserDAO {
    User user;
    ConnectionDB connectionDB = new ConnectionDB();
     public boolean create(User user) throws SQLException, ClassNotFoundException {
         String query = "INSERT INTO users (username, type, password, firstname, lastname, phonenumber) VALUES (?, ?, ?, ?, ?, ?)";
         PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
         preparedStatement.setString(1, user.getName());
         preparedStatement.setString(2, user.getType());
         preparedStatement.setString(3, user.getPassword());
         preparedStatement.setString(4, user.getFirstName());
         preparedStatement.setString(5, user.getLastName());
         preparedStatement.setLong(6, user.getPhoneNumber());
         int rowsAffected = preparedStatement.executeUpdate();
         preparedStatement.close();
         return rowsAffected > 0;
     }

}
