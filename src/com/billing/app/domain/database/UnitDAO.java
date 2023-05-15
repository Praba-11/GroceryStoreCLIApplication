package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CustomException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UnitDAO {
    Unit create(Unit unit) throws SQLException, ClassNotFoundException;
    Unit edit(Unit unit) throws SQLException, ClassNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    List<Unit> list() throws SQLException, ClassNotFoundException;
    Unit find(int id) throws SQLException, ClassNotFoundException;
    int count() throws SQLException, ClassNotFoundException;
}
