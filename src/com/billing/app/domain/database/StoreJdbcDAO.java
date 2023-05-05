package com.billing.app.domain.database;

import com.billing.app.domain.entity.Store;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StoreJdbcDAO implements StoreDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    Store store;
    @Override
    public boolean create(Store store) throws SQLException, ClassNotFoundException {

        // Storing Store details in Database table
        String query = "INSERT INTO store (gstnumber, name, phonenumber, address) VALUES (?, ?, ?, ?)" ;
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setString(2, store.getName());
        preparedStatement.setLong(3, store.getPhoneNumber());
        preparedStatement.setString(4, store.getAddress());
        preparedStatement.setLong(1, store.getGstNumber());
        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();

        return rowsAffected > 0;
    }

    @Override
    public boolean edit(Store store) throws SQLException, ClassNotFoundException {

        // Editing Store details in Database table
        String query = "UPDATE store SET gstnumber = ?, name = ?, phonenumber = ?, address = ? WHERE id = 1" ;
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setString(2, store.getName());
        preparedStatement.setLong(3, store.getPhoneNumber());
        preparedStatement.setString(4, store.getAddress());
        preparedStatement.setLong(1, store.getGstNumber());
        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();

        return rowsAffected > 0;
    }

    @Override
    public boolean delete() throws SQLException, ClassNotFoundException {

        // Delete Store operation
        ConnectionDB connectionDB = new ConnectionDB();
        Statement statement = connectionDB.getConnection().createStatement();
        Statement statement1 = connectionDB.getConnection().createStatement();
        String query = "DELETE FROM store";
        String sql = "ALTER SEQUENCE store_id_seq RESTART WITH 1";
        statement.execute(sql);
        int rowsAffected = statement1.executeUpdate(query);
        return rowsAffected > 0;
    }


    public Store getStore() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM store";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            long gstNumber = resultSet.getLong(3);
            long phoneNumber = resultSet.getLong(4);
            String address = resultSet.getString(5);
            store = new Store(id, name, gstNumber, phoneNumber, address);
        }
        return store;
    }
}
