package com.billing.app.domain.service.unit;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.CodeNullException;
import com.billing.app.domain.exceptions.ObjectNullPointerException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UnitServiceInterface {
    Unit create(Unit unit) throws SQLException, ObjectNullPointerException;
    Unit edit(Unit unit) throws SQLException, CodeNotFoundException, ObjectNullPointerException;
    boolean delete(int id) throws SQLException, CodeNotFoundException;
    List<Unit> list() throws SQLException;


}
