package com.billing.app.domain.database;

import com.billing.app.domain.entity.Unit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UnitJdbcDAO implements UnitDAO {
    @Override
    public void create(Unit unit) throws SQLException, ClassNotFoundException {

        // Storing Unit in Database table
        ConnectionDB connectionDB = new ConnectionDB();
        String query = "INSERT INTO unit (name, code, description, isdividable) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setString(1, unit.getName());
        preparedStatement.setString(2, unit.getCode());
        preparedStatement.setString(3, unit.getDescription());
        preparedStatement.setBoolean(4, unit.isDividable());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        System.out.println("Unit entry added.");
    }

    @Override
    public void edit(String code, ArrayList arrayList) throws SQLException, ClassNotFoundException {

        // Edit Unit in Database table
        String id = code;
        ConnectionDB connectionDB = new ConnectionDB();
        Statement statement = connectionDB.getConnection().createStatement();
        for (int index = 0; index < arrayList.size()-1; index++) {
            String query = "UPDATE unit SET " + arrayList.get(index) + " = '" + arrayList.get(index + 1) + "' WHERE code = '" + id + "'";
            statement.executeUpdate(query);
            index = index + 1;
        }

        System.out.println("Unit edited.");
    }

    @Override
    public void delete(String code) throws SQLException, ClassNotFoundException {

        // Delete Unit in Database table
        ConnectionDB connectionDB = new ConnectionDB();
        String query = "DELETE FROM unit WHERE code = '" + code + "'";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.executeUpdate();
        preparedStatement.close();

        System.out.println("Unit deleted.");

    }
    @Override
    public void list() throws SQLException, ClassNotFoundException {

        // List Unit from Database table
        ConnectionDB connectionDB = new ConnectionDB();
        String query = "SELECT * FROM unit";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String name = resultSet.getString(1);
            String code = resultSet.getString(2);
            String description = resultSet.getString(3);
            boolean isDividable = resultSet.getBoolean(4);
            System.out.println(name + ": " + code + ": " + description + ": " + isDividable);
        }
        statement.close();
        resultSet.close();

    }
}
