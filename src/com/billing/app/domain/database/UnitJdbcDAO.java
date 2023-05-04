package com.billing.app.domain.database;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.ProductException;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UnitJdbcDAO implements UnitDAO {
    Unit unit;
    ConnectionDB connectionDB = new ConnectionDB();
    ArrayList<Unit> unitArrayList = new ArrayList<>();

    @Override
    public boolean create(Unit unit) throws SQLException, ClassNotFoundException {

        // Storing Unit in Database table
        String query = "INSERT INTO unit (name, code, description, isdividable) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setString(1, unit.getName());
        preparedStatement.setString(2, unit.getCode());
        preparedStatement.setString(3, unit.getDescription());
        preparedStatement.setBoolean(4, unit.isDividable());
        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();

        return rowsAffected > 0;

    }





    @Override
    public boolean edit(Unit unit) throws SQLException, ClassNotFoundException, IllegalAccessException {

        // Edit Unit in Database table
        Statement statement = connectionDB.getConnection().createStatement();
        int rowsAffected = 0;
        Field[] fields = unit.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(unit);
            String query = "UPDATE unit SET " + field.getName() + " = '" + value + "' WHERE code = '" + unit.getCode() + "'";
            rowsAffected = statement.executeUpdate(query);
        }
        return rowsAffected > 0;
    }




    @Override
    public boolean delete(String key, String value) throws SQLException, ClassNotFoundException {

        // Delete Unit in Database table
        ConnectionDB connectionDB = new ConnectionDB();
        String query = "DELETE FROM unit WHERE " + key + " = '" + value + "'";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        return rowsAffected > 0;
    }




    @Override
    public ArrayList<Unit> list() throws SQLException, ClassNotFoundException {

        // Returns arraylist of Units from Database table
        ConnectionDB connectionDB = new ConnectionDB();
        String query = "SELECT * FROM unit";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String code = resultSet.getString(3);
            String description = resultSet.getString(4);
            boolean isDividable = resultSet.getBoolean(5);
            unit = new Unit(id, name, code, description, isDividable);
            unitArrayList.add(unit);
        }
        statement.close();
        resultSet.close();
        return unitArrayList;

    }



    public boolean isCodePresent(String code) throws SQLException, ClassNotFoundException {

        boolean flag = false;
        String query = "SELECT EXISTS(SELECT 1 FROM unit WHERE code = '" + code + "')";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
            flag = resultSet.getBoolean(1);
        return flag;

    }

    public Unit getUnitByCode(String code) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM unit WHERE code = '" + code + "'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(4);
            boolean isDividable = resultSet.getBoolean(5);
            unit = new Unit(id, name, code, description, isDividable);
        }
        return unit;
    }

    public boolean isIdPresent(String id) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        String query = "SELECT EXISTS(SELECT 1 FROM unit WHERE id = '" + id + "')";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
            flag = resultSet.getBoolean(1);
        return flag;
    }
}
