package com.billing.app.domain.repository.jdbc;

import com.billing.app.domain.entity.Store;
import java.sql.SQLException;
import java.util.ArrayList;

public interface StoreDAO {
    void create(Store store) throws SQLException, ClassNotFoundException;

    void edit(ArrayList arrayList) throws SQLException, ClassNotFoundException;

    void delete() throws SQLException, ClassNotFoundException;

}
