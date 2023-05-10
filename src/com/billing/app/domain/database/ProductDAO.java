package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.ProductException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO {
    Product create(Product product) throws ClassNotFoundException, SQLException;
    Product edit(Product product) throws ClassNotFoundException, SQLException, CodeNotFoundException;
    boolean delete(String key, String value) throws SQLException, ClassNotFoundException;
}