package com.billing.app.domain.service.unit;

import com.billing.app.domain.database.UnitDAO;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.ObjectNullPointerException;

public class UnitValidator {
    UnitDAO unitDAO;
    public boolean validate(Unit unit) throws ObjectNullPointerException {
        if(unit == null) {
            throw new ObjectNullPointerException("Unit cannot be null or empty.");
        }
        if (unit.getCode().isEmpty() || unit.getCode() == null) {
            throw new ObjectNullPointerException("Unit code cannot be null (or) empty.");
        }
        if (unit.getName().isEmpty() || unit.getName() == null) {
            throw new ObjectNullPointerException("Unit name cannot be null (or) empty.");
        }
        if (unit.getDescription().isEmpty() || unit.getDescription() == null) {
            throw new ObjectNullPointerException("Unit description cannot be null (or) empty.");
        }
        return true;
    }

}

