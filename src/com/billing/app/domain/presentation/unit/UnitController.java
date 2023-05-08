package com.billing.app.domain.presentation.unit;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.CodeNullException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.Validator;
import com.billing.app.domain.service.unit.UnitService;
import com.billing.app.domain.service.unit.UnitServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;


public class UnitController {
    private Unit unit;
    UnitServiceInterface unitServiceInterface;
    Validator validator;

    public Unit create(ArrayList<String> arrayList) throws SQLException, ClassNotFoundException {
        ArrayList<String> values = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        unit = new Unit();
        unit.setName(values.get(0));
        unit.setCode(values.get(1));
        unit.setDescription(values.get(2));
        unit.setDividable(Boolean.parseBoolean(values.get(3)));
        unitServiceInterface = new UnitService();
        return unitServiceInterface.create(unit);
    }


    public Unit edit(ArrayList<String> arrayList) throws SQLException, ClassNotFoundException, IllegalAccessException, NullPointerException, CodeNullException, TemplateMismatchException {
        validator = new Validator();
        unit = new Unit();
        ArrayList<String> keyValuePair = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        for (int index = 0; index < keyValuePair.size(); index += 2) {
            String key = keyValuePair.get(index);
            String value = keyValuePair.get(index + 1);
            validator.unitEditValidate(unit, key, value);
        }
        unitServiceInterface = new UnitService();
        return unitServiceInterface.edit(unit);
    }

    public boolean delete(ArrayList<String> arrayList) throws TemplateMismatchException, SQLException, CodeNotFoundException, ClassNotFoundException {
        boolean flag = false;
        String key = arrayList.get(2);
        String value = arrayList.get(3);
        validator = new Validator();
        if (validator.deleteValidate(key)) {
            unitServiceInterface = new UnitService();
            flag = unitServiceInterface.delete(key, value);
        }
        return flag;
    }

    public ArrayList<Unit> list() throws SQLException, ClassNotFoundException {
        unitServiceInterface = new UnitService();
        ArrayList<Unit> unitArrayList = unitServiceInterface.list();
        return unitArrayList;
    }
}
