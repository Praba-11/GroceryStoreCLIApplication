package com.billing.app.domain.presentation.store;

import com.billing.app.domain.entity.Store;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.exceptions.TypeMismatchException;
import com.billing.app.domain.service.store.StoreServiceImplementation;
import com.billing.app.domain.service.store.StoreService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoreController {
    private List<String> valueList;
    private Store store;
    StoreService storeService = new StoreServiceImplementation();
    StoreValidator storeValidator = new StoreValidator();
    public Store create(List<String> values) throws SQLException, ClassNotFoundException, TemplateMismatchException, TypeMismatchException, InvalidArgumentException {
        int expectedLength = 4;
        int actualLength = values.size();
        if (actualLength == expectedLength) {
            storeValidator.validateValues(values);
            store = setValues(values);
            return storeService.create(store);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }
    }

    public Store edit(Map<String, String> values) throws SQLException, ClassNotFoundException, TemplateMismatchException, TypeMismatchException, InvalidArgumentException {
        int expectedLength = 5;
        int actualLength = values.size();
        if (actualLength == expectedLength) {
            storeValidator.validateMap(values);
            valueList = new ArrayList<>(values.values());
            store = setValues(valueList);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }

        return storeService.edit(store);
    }


    public boolean delete() throws SQLException, ClassNotFoundException {
        storeService = new StoreServiceImplementation();
        return storeService.delete();
    }

    public Store view() throws SQLException {
        storeService = new StoreServiceImplementation();
        Store store = storeService.view();
        return store;
    }

    private static Store setValues(List<String> values) {
        Store store = new Store();
        store.setId(Integer.parseInt(values.get(0)));
        store.setName(values.get(1));
        store.setPhoneNumber(Long.parseLong(values.get(2)));
        store.setAddress(values.get(3));
        store.setGstNumber(values.get(4));
        return store;
    }
}
