package com.billing.app.domain.service;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.ProductException;
import com.billing.app.domain.exceptions.unit.CodeNullException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UnitServiceInterface {
    Unit create(Unit unit) throws SQLException, ClassNotFoundException;
    Unit edit(Unit unit) throws SQLException, ClassNotFoundException, IllegalAccessException, CodeNullException;
    boolean delete(String key, String value) throws SQLException, ClassNotFoundException, CodeNotFoundException;
    ArrayList<Unit> list() throws SQLException, ClassNotFoundException;


}
