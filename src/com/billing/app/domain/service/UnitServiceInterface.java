package com.billing.app.domain.service;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.ProductException;

import java.sql.SQLException;

public interface UnitServiceInterface {
    void create(Unit unit) throws ProductException, ClassNotFoundException, SQLException, CustomException;
}
