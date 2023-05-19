package com.billing.app.domain.database;

import com.billing.app.domain.entity.Store;
import java.sql.SQLException;
import java.util.ArrayList;

public interface StoreDAO {
    boolean create(Store store) throws SQLException;
    boolean edit(Store store) throws SQLException;
    boolean delete() throws SQLException;
    Store getStore() throws SQLException;
    int getCount() throws SQLException;
}
