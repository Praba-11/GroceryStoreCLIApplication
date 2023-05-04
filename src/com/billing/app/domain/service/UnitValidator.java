package com.billing.app.domain.service;

import com.billing.app.domain.database.UnitDAO;
import com.billing.app.domain.database.UnitJdbcDAO;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CodeNotFoundException;

import java.sql.SQLException;

public class UnitValidator {
    UnitDAO unitDAO;
    public Unit editAttributes(Unit unitToBeEdited, Unit modfiedUnit) throws NullPointerException {
        unitToBeEdited.setName(modfiedUnit.getName());
        unitToBeEdited.setCode(modfiedUnit.getCode());
        unitToBeEdited.setDescription(modfiedUnit.getDescription());
        unitToBeEdited.setDividable(modfiedUnit.isDividable());
        return unitToBeEdited;
    }

    public boolean isDeletable(String key, String value) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        unitDAO = new UnitJdbcDAO();
        if (key.equals("code")) {
            return unitDAO.isCodePresent(value);
        } else if (key.equals("id")) {
            return unitDAO.isIdPresent(value);
        } else {
            throw new CodeNotFoundException("Provided attribute not found in relational table. '" + key + " doesn't contain '" + value + "'");
        }
    }
}

