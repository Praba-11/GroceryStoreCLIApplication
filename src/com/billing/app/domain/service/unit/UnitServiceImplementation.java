package com.billing.app.domain.service.unit;

import com.billing.app.domain.database.UnitDAO;
import com.billing.app.domain.database.UnitDAOImplementation;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.*;

import java.sql.SQLException;
import java.util.List;

public class UnitServiceImplementation implements UnitService {
    private UnitDAO unitDAO = new UnitDAOImplementation();
    UnitValidator unitValidator = new UnitValidator();
    public Unit create(Unit unit) throws SQLException, ObjectNullPointerException {
        unitValidator.validate(unit);
        unitDAO.create(unit);
        return unitDAO.getByCode(unit.getCode());
    }

    @Override
    public Unit edit(Unit unit) throws SQLException, NotFoundException, ObjectNullPointerException {
        unitValidator.validate(unit);
        if (unitDAO.getById(unit.getId()) != null) {
            unitDAO.edit(unit);
            return unitDAO.getByCode(unit.getCode());
        }
        throw new NotFoundException("Provided unit id not present in unit relation table.");
    }


    public boolean delete(int id) throws SQLException, NotFoundException {
        boolean isDeleted;
        isDeleted = unitDAO.delete(id);
        if (!isDeleted) {
            throw new NotFoundException("(Id: " + id + ") doesn't exist.\n" +
                    "Please provide a valid id for deleting the unit.");
        }
        return true;
    }

    public List<Unit> list() throws SQLException {
        unitDAO = new UnitDAOImplementation();
        List<Unit> units = unitDAO.list();
        return units;
    }
}
