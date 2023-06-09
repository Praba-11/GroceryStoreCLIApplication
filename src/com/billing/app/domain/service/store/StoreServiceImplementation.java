package com.billing.app.domain.service.store;

import com.billing.app.domain.database.StoreDAO;
import com.billing.app.domain.database.StoreDAOImplementation;
import com.billing.app.domain.entity.Store;

import java.sql.SQLException;

public class StoreServiceImplementation implements StoreService {
    StoreDAO storeDAO = new StoreDAOImplementation();
    public Store create(Store store) throws SQLException {
        if (storeDAO.getCount() > 0) {
            return null;
        }
        else {
            storeDAO.create(store);
            return storeDAO.getStore();
        }
    }

    public Store edit(Store store) throws SQLException {
        storeDAO.edit(store);
        return storeDAO.getStore();
    }

    public boolean delete() throws SQLException {
        if (storeDAO.getStore() != null) {
            storeDAO.delete();
            return true;
        }
        return false;
    }

    public Store view() throws SQLException {
        return storeDAO.getStore();
    }
}
