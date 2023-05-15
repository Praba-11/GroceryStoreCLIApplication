package com.billing.app.domain.service.unit;

import com.billing.app.domain.database.UnitDAO;
import com.billing.app.domain.database.UnitDAOImplementation;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.*;

import java.sql.SQLException;
import java.util.List;

public class UnitService implements UnitServiceInterface {
    private UnitDAO unitDAO = new UnitDAOImplementation();
    UnitValidator unitValidator = new UnitValidator();
    public Unit create(Unit unit) throws SQLException, ClassNotFoundException, ObjectNullPointerException {
        try {
            unitValidator.validate(unit);
            return unitDAO.create(unit);
        } catch (ObjectNullPointerException exception) {
            throw new ObjectNullPointerException("Error while creating unit: " + exception.getMessage());
        }
    }

    @Override
    public Unit edit(Unit unit) throws ClassNotFoundException, SQLException, CodeNotFoundException, ObjectNullPointerException {

        try {
            unitValidator.validate(unit);
            if (unitDAO.find(unit.getId()) != null) {
                return unitDAO.edit(unit);
            }
            throw new CodeNotFoundException("Provided unit id not present in unit relation table.");
        } catch (ObjectNullPointerException exception) {
            throw new ObjectNullPointerException("Error while editing unit: " + exception.getMessage());
        }
    }

    public boolean delete(int id) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        boolean isDeleted;
        isDeleted = unitDAO.delete(id);
        if (!isDeleted) {
            throw new CodeNotFoundException("(Id: " + id + ") not present in unit relational table.");
        }
        return true;
    }

    public List<Unit> list() throws SQLException, ClassNotFoundException {
        unitDAO = new UnitDAOImplementation();
        List<Unit> units = unitDAO.list();
        return units;
    }
}
