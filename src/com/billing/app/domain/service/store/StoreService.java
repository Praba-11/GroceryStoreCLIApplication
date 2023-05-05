package com.billing.app.domain.service.store;

import com.billing.app.domain.database.StoreDAO;
import com.billing.app.domain.database.StoreJdbcDAO;
import com.billing.app.domain.entity.Store;

import java.sql.SQLException;

public class StoreService implements StoreServiceInterface {
    StoreDAO storeDAO;
    public Store create(Store store) throws SQLException, ClassNotFoundException {
        storeDAO = new StoreJdbcDAO();
        if (storeDAO.getCount() > 0) {
            return null;
        }
        else {
            if (storeDAO.create(store)) {
                return storeDAO.getStore();
            }
        }
        return null;
    }

    public Store edit(Store store) throws SQLException, ClassNotFoundException {
        storeDAO = new StoreJdbcDAO();
        if (storeDAO.edit(store)) {
            return storeDAO.getStore();
        }
        return null;
    }

    public boolean delete() throws SQLException, ClassNotFoundException {
        storeDAO = new StoreJdbcDAO();
        if (storeDAO.getStore() != null) {
            storeDAO.delete();
            return true;
        }
        return false;
    }
}