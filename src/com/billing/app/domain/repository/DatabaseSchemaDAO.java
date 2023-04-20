package com.billing.app.domain.repository;

import java.sql.SQLException;

public interface DatabaseSchemaDAO {
    public void create() throws SQLException, ClassNotFoundException;
    public void delete() throws SQLException, ClassNotFoundException;
}
