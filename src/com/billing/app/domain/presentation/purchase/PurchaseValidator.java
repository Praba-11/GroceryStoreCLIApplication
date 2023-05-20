package com.billing.app.domain.presentation.purchase;

import java.sql.SQLException;

public class PurchaseValidator {
    public String validateSQLState(SQLException exception) {
        String sqlState = exception.getSQLState();
        if (sqlState.equals("23505"))
            return "Provided product code already exists. \n" + exception.getMessage();
        if (sqlState.equals("23503")) {
            return "Invalid unit provided, please ensure whether the unit is already created (or) present. \n" + exception.getMessage();
        }
        if (sqlState.equals("42703")) {
            return "Provided 'attribute' column does not exist.";
        }
        return "Unrecognised SQL state error.";
    }
}
