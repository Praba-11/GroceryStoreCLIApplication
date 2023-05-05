package com.billing.app.domain.service.store;

import com.billing.app.domain.entity.Store;

import java.sql.SQLException;

public interface StoreServiceInterface {
    Store create(Store store) throws SQLException, ClassNotFoundException;
    Store edit(Store store) throws SQLException, ClassNotFoundException;
    boolean delete() throws SQLException, ClassNotFoundException;

}
