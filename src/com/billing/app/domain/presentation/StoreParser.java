package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Store;
import com.billing.app.domain.entity.Store;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.service.StoreService;
import com.billing.app.domain.service.StoreServiceInterface;
import com.billing.app.domain.service.UnitService;

import java.sql.SQLException;
import java.util.ArrayList;

public class StoreParser {
    private Store store;
    StoreServiceInterface storeServiceInterface;
    Validator validator;
    public Store create(ArrayList<String> arrayList) {
        ArrayList<String> values = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        store = new Store();
        store.setName(values.get(0));
        store.setGstNumber(Long.parseLong(values.get(1)));
        store.setAddress(values.get(2));
        store.setPhoneNumber(Long.parseLong(values.get(3)));
        storeServiceInterface = new StoreService();
        storeServiceInterface.create(store);
    }

    public Store edit(ArrayList<String> arrayList) {
        validator = new Validator();
        store = new Store();
        ArrayList<String> keyValuePair = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        for (int index = 0; index < keyValuePair.size(); index += 2) {
            String key = keyValuePair.get(index);
            String value = keyValuePair.get(index + 1);
            validator.storeValidate(store, key, value);
        }
        storeServiceInterface = new StoreService();
        return storeServiceInterface.edit(store);
    }


    public boolean delete(ArrayList<String> arrayList) {
        boolean flag = false;
        int id = Integer.parseInt(arrayList.get(2));
        validator = new Validator();
        if (validator.storeValidate(id)) {
            storeServiceInterface = new StoreService();
            flag = storeServiceInterface.delete(id);
        }
        return flag;
    }

    public ArrayList<Store> list() throws SQLException, ClassNotFoundException {
        storeServiceInterface = new UnitService();
        ArrayList<Store> storeArrayList = storeServiceInterface.list();
        return storeArrayList;
    }
}
