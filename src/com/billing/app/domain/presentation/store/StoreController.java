package com.billing.app.domain.presentation.store;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Store;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.exceptions.TypeMismatchException;
import com.billing.app.domain.presentation.Validator;
import com.billing.app.domain.service.store.StoreService;
import com.billing.app.domain.service.store.StoreServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoreController {
    private List<String> valueList;
    private Store store;
    StoreServiceInterface storeServiceInterface = new StoreService();
    StoreValidator storeValidator = new StoreValidator();
    public Store create(List<String> values) throws SQLException, ClassNotFoundException, TemplateMismatchException, TypeMismatchException, IllegalArgumentException {
        int expectedLength = 4;
        int actualLength = values.size();
        if (actualLength == expectedLength) {
            storeValidator.validateValues(values);
            store = setValues(values, false);
            return storeServiceInterface.create(store);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }
    }

    public Store edit(Map<String, String> values) throws SQLException, ClassNotFoundException, TemplateMismatchException, TypeMismatchException, IllegalArgumentException {
        int expectedLength = 4;
        int actualLength = values.size();
        if (actualLength == expectedLength) {
            storeValidator.validateMap(values);
            valueList = new ArrayList<>(values.values());
            store = setValues(valueList, true);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }

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

    private static Store setValues(List<String> values, boolean setId) {
        Store store = new Store();
        store.setName(values.get(0));
        store.setPhoneNumber(Long.parseLong(values.get(1)));
        store.setAddress(values.get(2));
        store.setGstNumber(values.get(3));
        return store;
    }
}
