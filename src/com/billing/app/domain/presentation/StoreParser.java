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

    public Store edit(ArrayList<String> arrayList) throws SQLException, ClassNotFoundException {
        ArrayList<String> values = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        store = new Store();
        store.setName(values.get(1));
        store.setGstNumber(Long.parseLong(values.get(0)));
        store.setAddress(values.get(3));
        store.setPhoneNumber(Long.parseLong(values.get(2)));
        storeServiceInterface = new StoreService();
        return storeServiceInterface.edit(store);
    }


    public boolean delete() throws SQLException, ClassNotFoundException {
        storeServiceInterface = new StoreService();
        return storeServiceInterface.delete();
    }
//
//    public ArrayList<Store> list() throws SQLException, ClassNotFoundException {
//        storeServiceInterface = new UnitService();
//        ArrayList<Store> storeArrayList = storeServiceInterface.list();
//        return storeArrayList;
//    }
}
