package com.billing.app.domain.presentation.unit;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.CodeNullException;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.exceptions.TypeMismatchException;
import com.billing.app.domain.presentation.Validator;
import com.billing.app.domain.service.unit.UnitService;
import com.billing.app.domain.service.unit.UnitServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UnitController {
    private List<String> valueList;
    private List<String> keyList;
    private Unit unit;
    UnitServiceInterface unitServiceInterface = new UnitService();
    UnitValidator unitValidator = new UnitValidator();

    public Unit create(ArrayList<String> values) throws SQLException, ClassNotFoundException, TypeMismatchException, IllegalArgumentException, TemplateMismatchException {

        int expectedLength = 4;
        int actualLength = values.size();
        unitValidator.validateDetails(values);
        if (actualLength == expectedLength) {
            unit = setValues(values, false);
            return unitServiceInterface.create(unit);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }
    }


    public Unit edit(ArrayList<String> values) throws SQLException, ClassNotFoundException, IllegalAccessException, NullPointerException, CodeNullException, TemplateMismatchException, TypeMismatchException, IllegalArgumentException {

        int expectedLength = 10;
        int actualLength = values.size();

        valueList = new ArrayList<>();
        keyList = new ArrayList<>();

        if (actualLength == expectedLength) {
            for (int index = 0; index < values.size(); index += 2) {
                String key = values.get(index);
                String value = values.get(index + 1);
                keyList.add(key);
                valueList.add(value);
            }

            List<String> keys = new ArrayList<>(keyList.subList(1, keyList.size()));
            List<String> details = new ArrayList<>(valueList.subList(1, valueList.size()));

            String key = keyList.get(0);
            String identifier = valueList.get(0);

            unitValidator.validateId(key, identifier);
            unitValidator.validateKeys(keys);
            unitValidator.validateDetails(details);

            unit = setValues(valueList, true);

        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }

        return unitServiceInterface.edit(unit);
    }

        public boolean delete (ArrayList <String> values) throws TemplateMismatchException, SQLException, CodeNotFoundException, ClassNotFoundException {
            boolean flag = false;

            int expectedLength = 1;
            int actualLength = values.size();
            if (actualLength == expectedLength) {
                int id = Integer.parseInt(values.get(0));
                flag = unitServiceInterface.delete(id);
                return flag;
            } else {
                throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Provided: " + actualLength);
            }
        }

        public ArrayList<Unit> list () throws SQLException, ClassNotFoundException {
            unitServiceInterface = new UnitService();
            ArrayList<Unit> unitArrayList = unitServiceInterface.list();
            return unitArrayList;
        }

    private static Unit setValues(List<String> values, boolean setId) {
        Unit unit = new Unit();
        int startIndex = setId ? 0 : -1;

        if (setId) {
            unit.setId(Integer.parseInt(values.get(startIndex)));
        }

        unit.setName(values.get(startIndex + 1));
        unit.setCode(values.get(startIndex + 2));
        unit.setDescription(values.get(startIndex + 3));
        unit.setDividable(Boolean.parseBoolean(values.get(startIndex + 4)));

        return unit;
    }

}
