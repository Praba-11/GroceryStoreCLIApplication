package com.billing.app.domain.database;

import com.billing.app.domain.entity.Store;
import java.sql.SQLException;
import java.util.ArrayList;

public interface StoreDAO {
    boolean create(Store store) throws SQLException, ClassNotFoundException;

    boolean edit(Store store) throws SQLException, ClassNotFoundException;

    boolean delete() throws SQLException, ClassNotFoundException;
    Store getStore() throws SQLException, ClassNotFoundException;
    int getCount() throws SQLException, ClassNotFoundException;
}
