package com.billing.app.domain.repository;

import com.billing.app.domain.entity.Store;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StoreJdbcDAO implements StoreDAO {
    DatabaseSchemaDAO databaseSchemaDAO = new DatabaseSchemaJdbcDAO();

    @Override
    public void create(Store store) throws SQLException, ClassNotFoundException {

        // Storing Store details in Database table
        ConnectionDB connectionDB = new ConnectionDB();
        String query = "INSERT INTO store (name, phone_number, address, gst_number) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setString(1, store.getName());
        preparedStatement.setLong(2, store.getPhoneNumber());
        preparedStatement.setString(3, store.getAddress());
        preparedStatement.setLong(4, store.getGstNumber());
        preparedStatement.executeUpdate();
        databaseSchemaDAO.create();
        preparedStatement.close();

        System.out.println("Store created.");
    }

    @Override
    public void edit(ArrayList arrayList) throws SQLException, ClassNotFoundException {

        // Editing Store details in Database table
        ConnectionDB connectionDB = new ConnectionDB();
        Statement statement = connectionDB.getConnection().createStatement();
        for (int index = 0; index < arrayList.size() - 2; index++) {
            String query = "UPDATE products SET " + arrayList.get(index) + " = '" + arrayList.get(index + 1) + "'";
            statement.executeQuery(query);
            index = index + 1;
        }
        statement.close();

        System.out.println("Store edited.");
    }

    @Override
    public void delete() throws SQLException, ClassNotFoundException {

        // Delete Store operation
        ConnectionDB connectionDB = new ConnectionDB();
        Statement statement = connectionDB.getConnection().createStatement();
        String query = "Drop table Store";
        statement.executeQuery(query);
        databaseSchemaDAO.delete();
        statement.close();

        System.out.println("Store Deleted.");
    }
}
