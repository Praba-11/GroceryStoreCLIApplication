package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CustomException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UnitDAO {
    Unit create(Unit unit) throws SQLException;
    Unit edit(Unit unit) throws SQLException;
    boolean delete(int id) throws SQLException;
    List<Unit> list() throws SQLException;
    Unit find(int id) throws SQLException;
    int count() throws SQLException;
}
