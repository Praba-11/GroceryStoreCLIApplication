package com.billing.app.domain.service;

import com.billing.app.domain.database.ProductJdbcDAO;
import com.billing.app.domain.database.UnitDAO;
import com.billing.app.domain.database.UnitJdbcDAO;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class UnitService implements UnitServiceInterface {
    private UnitDAO unitDAO;

    public void create(Unit unit) throws ProductException, ClassNotFoundException, SQLException, CustomException {
        unitDAO = new UnitJdbcDAO();
        if (unitDAO.create(unit)) {
            throw new CustomException("Unit created successfully.");
        } else {
            throw new CustomException("Unit creation failed.");
        }
    }

    public void edit(Unit unit) throws ProductException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, CustomException {
        unitDAO = new UnitJdbcDAO();
        if (unitDAO.edit(unit)) {
            throw new CustomException("Unit created successfully.");
        } else {
            throw new CustomException("Unit creation failed.");
        }
    }


    public void delete(String id) throws ClassNotFoundException, ProductException {
        unitDAO = new UnitJdbcDAO();
        if (unitDAO.isIdPresent(id)) {
            unitDAO.delete(id);
        } else {
            throw new CodeNotFoundException("Code not present in product relation table.");
        }
    }


    public ArrayList<Unit> list() throws ProductException, ClassNotFoundException {
        unitDAO = new UnitJdbcDAO();
        ArrayList<Unit> unitArrayList = unitDAO.list();
        if (unitArrayList.isEmpty()) {
            throw new NullPageException("Pagination (or) search text failed. Cannot return any list of products.");
        }
        else {
            return unitArrayList;
        }
    }


}
