package com.billing.app.domain.presentation.unit;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.service.unit.UnitService;
import com.billing.app.domain.service.unit.UnitServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UnitController {
    private List<String> valueList;
    private List<String> keyList;
    private Unit unit;
    UnitServiceInterface unitServiceInterface = new UnitService();
    UnitValidator unitValidator = new UnitValidator();

    public Unit create(List<String> values) throws SQLException, TypeMismatchException, InvalidArgumentException, TemplateMismatchException, ObjectNullPointerException {

        int expectedLength = 4;
        int actualLength = values.size();

        if (actualLength == expectedLength) {
            unitValidator.validateDetails(values);
            unit = setValues(values, false);
            return unitServiceInterface.create(unit);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }
    }


    public Unit edit(Map<String, String> values) throws SQLException, NullPointerException, CodeNullException, TemplateMismatchException, TypeMismatchException, InvalidArgumentException, ObjectNullPointerException, CodeNotFoundException {

        int expectedLength = 5;
        int actualLength = values.size();
        if (actualLength == expectedLength) {
            unitValidator.validateMap(values);
            valueList = new ArrayList<>(values.values());
            unit = setValues(valueList, true);
            System.out.println(unit);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }
        return unitServiceInterface.edit(unit);
    }

        public boolean delete (String values) throws SQLException, CodeNotFoundException, InvalidArgumentException {
            boolean flag = false;
            int id;
            try {
                id = Integer.parseInt(values);
            } catch (NumberFormatException exception) {
                throw new InvalidArgumentException("Unparseable id provided for deletion. Please try again.");
            }
            flag = unitServiceInterface.delete(id);
            return flag;
        }

        public List<Unit> list () throws SQLException {
            unitServiceInterface = new UnitService();
            List<Unit> unitArrayList = unitServiceInterface.list();
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
