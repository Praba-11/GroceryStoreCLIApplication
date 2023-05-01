package com.billing.app.domain.database;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CustomException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UnitJdbcDAO implements UnitDAO {
    Unit unit;
    ArrayList<Unit> unitArrayList = new ArrayList<>();
    @Override
    public boolean create(Unit unit) throws SQLException, ClassNotFoundException, CustomException {

        // Storing Unit in Database table
        try {
            ConnectionDB connectionDB = new ConnectionDB();
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
        catch (ClassNotFoundException | SQLException exception) {
            throw new CustomException(exception.getMessage());
        }
    }




    @Override
    public boolean edit(String code, ArrayList arrayList) throws SQLException, ClassNotFoundException, CustomException {

        // Edit Unit in Database table
        try {
            String id = code;
            int rowsAffected = 0;
            ConnectionDB connectionDB = new ConnectionDB();
            Statement statement = connectionDB.getConnection().createStatement();
            for (int index = 0; index < arrayList.size()-1; index++) {
                String query = "UPDATE unit SET " + arrayList.get(index) + " = '" + arrayList.get(index + 1) + "' WHERE code = '" + id + "'";
                rowsAffected = statement.executeUpdate(query);
                index = index + 1;
            }
            return rowsAffected > 0;
        }
        catch (ClassNotFoundException | SQLException exception) {
            throw new CustomException(exception.getMessage());
        }
    }




    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException, CustomException {

        // Delete Unit in Database table
        try {
            ConnectionDB connectionDB = new ConnectionDB();
            String query = "DELETE FROM unit WHERE code = '" + code + "'";
            PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsAffected > 0;
        }
        catch (ClassNotFoundException | SQLException exception) {
            throw new CustomException(exception.getMessage());
        }

    }




    @Override
    public ArrayList<Unit> list() throws SQLException, ClassNotFoundException, CustomException {

        // Returns arraylist of Units from Database table
        try {
            ConnectionDB connectionDB = new ConnectionDB();
            String query = "SELECT * FROM unit";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                String code = resultSet.getString(2);
                String description = resultSet.getString(3);
                boolean isDividable = resultSet.getBoolean(4);
                unit = new Unit(name, code, description, isDividable);
                unitArrayList.add(unit);
            }
            statement.close();
            resultSet.close();
            return unitArrayList;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new CustomException(exception.getMessage());
        }
    }
}
