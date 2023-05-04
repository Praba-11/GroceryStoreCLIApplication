package com.billing.app.domain.database;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CustomException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UnitDAO {
    boolean create(Unit unit) throws SQLException, ClassNotFoundException;
    boolean edit(Unit unit) throws SQLException, ClassNotFoundException, IllegalAccessException;
    boolean delete(String key, String value) throws SQLException, ClassNotFoundException;
    ArrayList<Unit> list() throws SQLException, ClassNotFoundException;
    Unit getUnitByCode(String code) throws SQLException, ClassNotFoundException;
    boolean isCodePresent(String code) throws SQLException, ClassNotFoundException;
    boolean isIdPresent(String id) throws SQLException, ClassNotFoundException;

}
