package com.billing.app.domain.database;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CustomException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UnitDAO {
    boolean create(Unit unit) throws SQLException, ClassNotFoundException, CustomException;
    boolean edit(String code, ArrayList arrayList) throws SQLException, ClassNotFoundException, CustomException;
    boolean delete(String code) throws SQLException, ClassNotFoundException, CustomException;
    ArrayList<Unit> list() throws SQLException, ClassNotFoundException, CustomException;
}
