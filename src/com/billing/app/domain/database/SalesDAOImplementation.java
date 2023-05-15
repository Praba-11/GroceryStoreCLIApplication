package com.billing.app.domain.database;

import com.billing.app.domain.entity.Sales;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalesDAOImplementation {
    Sales sales;
    List<Sales> salesList;
    ConnectionDB connectionDB = new ConnectionDB();
    public Sales create(Sales sales) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO sales (invoice, date, grand_total) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        PreparedStatement statement = setQuery(preparedStatement, sales);
        statement.executeUpdate();
        return sales;
    }

    public boolean delete(int invoice) throws SQLException, ClassNotFoundException {
        String query = "DELETE * FROM sales WHERE invoice = " + invoice;
        Statement statement = connectionDB.getConnection().createStatement();
        int rowsAffected = statement.executeUpdate(query);
        return rowsAffected > 0;
    }

    public List<Sales> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM sales WHERE CAST(" + attribute + " AS TEXT) ILIKE '%" + searchText + "%' LIMIT " + range + " OFFSET " + page;
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Sales> sales = listSales(resultSet);
        return sales;
    }

    public List<Sales> list(String searchText) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM sales WHERE CAST(id AS TEXT) ILIKE '%" + searchText + "%' OR code ILIKE '%" + searchText + "%' OR name ILIKE '%" + searchText + "%' OR unitcode ILIKE '%" + searchText + "%' OR type ILIKE '%" + searchText + "%' OR CAST(price AS TEXT) ILIKE '%" + searchText + "%' OR CAST(stock AS TEXT) ILIKE '%" + searchText + "%'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Sales> sales = listSales(resultSet);
        return sales;
    }

    private List<Sales> listSales(ResultSet resultSet) throws SQLException {
        salesList = new ArrayList<>();
        while (resultSet.next()) {
            sales = new Sales();
            Sales setSales = setSales(sales, resultSet);
            salesList.add(setSales);
        }
        return salesList;
    }

    private Sales setSales(Sales sales, ResultSet resultSet) throws SQLException {
        sales.setInvoice(resultSet.getInt(1));
        sales.setDate(resultSet.getDate(2));
        sales.setGrandTotal(resultSet.getFloat(3));
        return sales;
    }


    private PreparedStatement setQuery(PreparedStatement preparedStatement, Sales sales) throws SQLException {
        preparedStatement.setInt(1, sales.getInvoice());
        preparedStatement.setDate(2, sales.getDate());
        preparedStatement.setFloat(3, sales.getGrandTotal());
        return preparedStatement;
    }
}