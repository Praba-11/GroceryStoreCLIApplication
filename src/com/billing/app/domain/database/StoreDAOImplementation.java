package com.billing.app.domain.database;

import com.billing.app.domain.entity.Store;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StoreDAOImplementation implements StoreDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    Store store;
    @Override
    public Store create(Store store) throws SQLException {

        String query = "INSERT INTO store (gstnumber, name, phonenumber, address) VALUES (?, ?, ?, ?)" ;
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setString(2, store.getName());
        preparedStatement.setLong(3, store.getPhoneNumber());
        preparedStatement.setString(4, store.getAddress());
        preparedStatement.setString(1, store.getGstNumber());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return store;
    }

    @Override
    public Store edit(Store store) throws SQLException {

        String query = "UPDATE store SET gstnumber = ?, name = ?, phonenumber = ?, address = ? WHERE id = 1" ;
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setString(2, store.getName());
        preparedStatement.setLong(3, store.getPhoneNumber());
        preparedStatement.setString(4, store.getAddress());
        preparedStatement.setString(1, store.getGstNumber());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return store;
    }

    @Override
    public boolean delete() throws SQLException {

        ConnectionDB connectionDB = new ConnectionDB();
        Statement statement = connectionDB.getConnection().createStatement();
        Statement statement1 = connectionDB.getConnection().createStatement();
        String query = "DELETE FROM store";
        String sql = "ALTER SEQUENCE store_id_seq RESTART WITH 1";
        String userQuery = "UPDATE users SET isavailable = false WHERE usertype != 'Administrator'";
        statement.execute(sql);
        int rowsAffected = statement1.executeUpdate(query);
        statement.execute(userQuery);
        return rowsAffected > 0;
    }



    public Store getStore() throws SQLException {
        String query = "SELECT * FROM store";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String gstNumber = resultSet.getString(3);
            long phoneNumber = resultSet.getLong(4);
            String address = resultSet.getString(5);
            store = new Store(id, name, gstNumber, phoneNumber, address);
        }
        return store;
    }

    public int getCount() throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(id) FROM store";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count;
    }
}
