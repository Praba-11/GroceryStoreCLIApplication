package com.billing.app.domain.presentation;

import com.billing.app.domain.database.ProductDAO;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.ProductException;
import com.billing.app.domain.service.ProductService;
import com.billing.app.domain.service.ProductServiceInterface;
import com.billing.app.domain.service.UnitService;
import com.billing.app.domain.service.UnitServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UnitParser {
    private Unit unit;
    UnitServiceInterface unitServiceInterface;
    public void create(ArrayList<String> arrayList) throws SQLException, CustomException, ClassNotFoundException, ProductException {
        ArrayList<String> values = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        Unit unit = new Unit();
        unit.setName(values.get(0));
        unit.setCode(values.get(1));
        unit.setDescription(values.get(2));
        unit.setDividable(Boolean.parseBoolean(values.get(3)));
        unitServiceInterface = new UnitService();
        unitServiceInterface.create(unit);
    }


    public void edit(ArrayList<String> arrayList) throws ProductException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException, CustomException {
        ArrayList<String> keyValues = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < keyValues.size(); i += 2) {
            String key = keyValues.get(i);
            String value = keyValues.get(i + 1);
            map.put(key, value);
        }
        unit = new Unit();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals("name")) {
                unit.setName(value);
            } else if (key.equals("unitCode")) {
                unit.setCode(value);
            } else if (key.equals("description")) {
                unit.setDescription(value);
            } else if (key.equals("isDividable")) {
                unit.setDividable(Boolean.parseBoolean(value));
            } else {
                throw new ProductException("Invalid attribute provided. Please provide necessary attribute. " + key);
            }
        }
        unitServiceInterface = new ProductService();
        return unitServiceInterface.edit(unit);
    }

    public boolean delete(ArrayList<String> arrayList) throws ProductException, ClassNotFoundException, CustomException {
        try {
            String code = arrayList.get(2);
            unitServiceInterface = new ProductService();
            return unitServiceInterface.delete(code);
        }
        catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
    }

    public ArrayList<Product> list(ArrayList<String> arrayList) throws Throwable {
        unitServiceInterface = new ProductService();
        if (arrayList.size() == 2) {
            return unitServiceInterface.list();
        }
    }
}
