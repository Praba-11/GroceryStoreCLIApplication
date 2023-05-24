package com.billing.app.domain.database;

import com.billing.app.domain.entity.Unit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UnitDAOImplementation implements UnitDAO {
    Unit unit;
    private List<Unit> unitList;
    ConnectionDB connectionDB = new ConnectionDB();
    ArrayList<Unit> unitArrayList = new ArrayList<>();

    @Override
    public Unit create(Unit unit) throws SQLException {

        String query = "INSERT INTO unit (name, code, description, isdividable) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        PreparedStatement statement = setQuery(preparedStatement, unit);
        statement.executeUpdate();
        return unit;

    }



    @Override
    public Unit edit(Unit unit) throws SQLException {
        String query = "UPDATE unit SET name = ?, code = ?, description = ?, isdividable = ? WHERE id = ?";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        PreparedStatement statement = setQuery(preparedStatement, unit);
        statement.setInt(5, unit.getId());
        statement.executeUpdate();
        return unit;

    }




    @Override
    public boolean delete(int id) throws SQLException {

        String query = "DELETE FROM unit WHERE id = " + id ;
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        return rowsAffected > 0;
    }




    @Override
    public List<Unit> list() throws SQLException {

        ConnectionDB connectionDB = new ConnectionDB();
        String query = "SELECT * FROM unit";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Unit> units = listUnit(resultSet);
        return units;

    }



    public Unit getById(int id) throws SQLException {
        Unit unitFound = null;
        String query = "SELECT * FROM unit WHERE id = '" + id + "'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        unit = new Unit();
        while (resultSet.next()) {
            unitFound = setUnit(unit, resultSet);
        }
        return unitFound;
    }

    public Unit getByCode(String code) throws SQLException {
        Unit unitFound = null;
        String query = "SELECT * FROM unit WHERE code = '" + code + "'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        unit = new Unit();
        while (resultSet.next()) {
            unitFound = setUnit(unit, resultSet);
        }
        return unitFound;
    }

    public int count() throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(*) FROM unit";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count;
    }

    private List<Unit> listUnit(ResultSet resultSet) throws SQLException {
        unitList = new ArrayList<>();
        while (resultSet.next()) {
            unit = new Unit();
            Unit setUnit = setUnit(unit, resultSet);
            unitList.add(setUnit);
        }
        return unitList;
    }


    private Unit setUnit(Unit unit, ResultSet resultSet) throws SQLException {
        unit.setId(resultSet.getInt(1));
        unit.setName(resultSet.getString(2));
        unit.setCode(resultSet.getString(3));
        unit.setDescription(resultSet.getString(4));
        unit.setDividable(resultSet.getBoolean(5));
        return unit;
    }

    private PreparedStatement setQuery(PreparedStatement preparedStatement, Unit unit) throws SQLException {
        preparedStatement.setString(1, unit.getName());
        preparedStatement.setString(2, unit.getCode());
        preparedStatement.setString(3, unit.getDescription());
        preparedStatement.setBoolean(4, unit.isDividable());
        return preparedStatement;
    }
}
