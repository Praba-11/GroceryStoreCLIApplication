package com.billing.app.domain.service.unit;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.CodeNullException;
import com.billing.app.domain.exceptions.ObjectNullPointerException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UnitServiceInterface {
    Unit create(Unit unit) throws SQLException, ClassNotFoundException, ObjectNullPointerException;
    Unit edit(Unit unit) throws SQLException, ClassNotFoundException, IllegalAccessException, CodeNullException;
    boolean delete(String key, String value) throws SQLException, ClassNotFoundException, CodeNotFoundException;
    ArrayList<Unit> list() throws SQLException, ClassNotFoundException;


}
