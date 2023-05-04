package com.billing.app.domain.service;

import com.billing.app.domain.database.ProductJdbcDAO;
import com.billing.app.domain.database.UnitDAO;
import com.billing.app.domain.database.UnitJdbcDAO;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.unit.CodeNullException;
import com.billing.app.domain.presentation.Validator;

import java.sql.SQLException;
import java.util.ArrayList;

public class UnitService implements UnitServiceInterface {
    private UnitDAO unitDAO;
    UnitValidator unitValidator;
    public Unit create(Unit unit) throws SQLException, ClassNotFoundException {
        unitDAO = new UnitJdbcDAO();
        if (unitDAO.create(unit)) {
            return unitDAO.getUnitByCode(unit.getCode());
        }
        return null;
    }

    @Override
    public Unit edit(Unit modifiedUnit) throws SQLException, ClassNotFoundException, IllegalAccessException, CodeNullException {
        try {
            unitDAO = new UnitJdbcDAO();
            Unit unitToBeEdited = unitDAO.getUnitByCode(modifiedUnit.getCode());
            unitValidator = new UnitValidator();
            unitValidator.editAttributes(unitToBeEdited, modifiedUnit);
            if (unitDAO.edit(unitToBeEdited)) {
                return unitDAO.getUnitByCode(modifiedUnit.getCode());
            }
            return null;
        }
        catch (NullPointerException exception) {
            throw new CodeNullException("'" + modifiedUnit.getCode() + "' cannot be assigned as Unit Code.");
        }
    }

    public boolean delete(String key, String value) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        boolean flag = false;
        unitDAO = new UnitJdbcDAO();
        unitValidator = new UnitValidator();
        if (unitValidator.isDeletable(key, value)) {
            flag = unitDAO.delete(key, value);
        }
        return flag;
    }

    public ArrayList<Unit> list() throws SQLException, ClassNotFoundException {
        unitDAO = new UnitJdbcDAO();
        ArrayList<Unit> unitArrayList = unitDAO.list();
        return unitArrayList;
    }
}
