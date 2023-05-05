package com.billing.app.domain.service;

import com.billing.app.domain.database.StoreDAO;
import com.billing.app.domain.database.StoreJdbcDAO;
import com.billing.app.domain.database.UnitJdbcDAO;
import com.billing.app.domain.entity.Store;
import com.billing.app.domain.exceptions.CodeNotFoundException;

import java.sql.SQLException;

public class StoreService implements StoreServiceInterface {
    StoreDAO storeDAO;
    public Store create(Store store) {
        storeDAO = new StoreJdbcDAO();
        if (storeDAO.create(store)) {
            return storeDAO.getStoreByGSTNumber(store.getCode());
        }
        return null;
    }

    public Store edit(Store store) {
        storeDAO = new StoreJdbcDAO();
        if (storeDAO.edit(store)) {
            return storeDAO.getStoreByGSTNumber(store.getCode());
        }
        return null;
    }

    public boolean delete(int id) {
        boolean flag = false;
        storeDAO = new StoreJdbcDAO();
        if (storeServiceValidator.isDeletable(key, value)) {
            flag = storeDAO.delete(key, value);
        }
        return flag;
    }
}
