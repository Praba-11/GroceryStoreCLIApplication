package com.billing.app.domain.repository;

import com.billing.app.domain.entity.Unit;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UnitDAO {
    void create(Unit unit) throws SQLException, ClassNotFoundException;
    void edit(String code, ArrayList arrayList) throws SQLException, ClassNotFoundException;
    void delete(String code) throws SQLException, ClassNotFoundException;
    void list() throws SQLException, ClassNotFoundException;
}
