package com.billing.app.domain.service;

import com.billing.app.domain.database.ProductJdbcDAO;
import com.billing.app.domain.database.UnitDAO;
import com.billing.app.domain.database.UnitJdbcDAO;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.InvalidConstraintLengthException;
import com.billing.app.domain.exceptions.ProductException;

import java.sql.SQLException;

public class UnitService {
    private UnitDAO unitDAO;
    public void create(Unit unit) throws ProductException, ClassNotFoundException, SQLException, CustomException {
        unitDAO = new UnitJdbcDAO();
        unitDAO.create(unit);
    }


}
