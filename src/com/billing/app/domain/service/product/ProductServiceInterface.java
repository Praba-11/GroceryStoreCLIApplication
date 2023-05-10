package com.billing.app.domain.service.product;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.ObjectNullPointerException;
import com.billing.app.domain.exceptions.ProductException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface ProductServiceInterface {
    Product create(Product product) throws ClassNotFoundException, SQLException, ObjectNullPointerException;

    Product edit(Product modifiedProduct) throws ClassNotFoundException, SQLException, ObjectNullPointerException, CodeNotFoundException;

    boolean delete(String key, String value) throws SQLException, ClassNotFoundException, CodeNotFoundException;
}
