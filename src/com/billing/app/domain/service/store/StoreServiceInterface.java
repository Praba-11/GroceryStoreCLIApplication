package com.billing.app.domain.service.store;

import com.billing.app.domain.entity.Store;

import java.sql.SQLException;

public interface StoreServiceInterface {
    Store create(Store store) throws SQLException;
    Store edit(Store store) throws SQLException;
    boolean delete() throws SQLException;
    Store view() throws SQLException;

}
