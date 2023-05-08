package com.billing.app.domain.presentation.store;

import com.billing.app.domain.entity.Store;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.Validator;
import com.billing.app.domain.service.store.StoreService;
import com.billing.app.domain.service.store.StoreServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;

public class StoreController {
    private Store store;
    StoreServiceInterface storeServiceInterface;
    Validator validator;
    public Store create(ArrayList<String> arrayList) throws SQLException, ClassNotFoundException {
        ArrayList<String> values = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        store = new Store();
        store.setName(values.get(1));
        store.setGstNumber(Long.parseLong(values.get(0)));
        store.setAddress(values.get(3));
        store.setPhoneNumber(Long.parseLong(values.get(2)));
        storeServiceInterface = new StoreService();
        return storeServiceInterface.create(store);
    }

    public Store edit(ArrayList<String> arrayList) throws SQLException, ClassNotFoundException, TemplateMismatchException {
        validator = new Validator();
        store = new Store();
        ArrayList<String> keyValuePair = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        for (int index = 0; index < keyValuePair.size(); index += 2) {
            String key = keyValuePair.get(index);
            String value = keyValuePair.get(index + 1);
            validator.storeEditValidate(store, key, value);
        }
        storeServiceInterface = new StoreService();
        return storeServiceInterface.edit(store);
    }


    public boolean delete() throws SQLException, ClassNotFoundException {
        storeServiceInterface = new StoreService();
        return storeServiceInterface.delete();
    }

    public Store view() throws SQLException, ClassNotFoundException {
        storeServiceInterface = new StoreService();
        Store store = storeServiceInterface.view();
        return store;
    }
}
