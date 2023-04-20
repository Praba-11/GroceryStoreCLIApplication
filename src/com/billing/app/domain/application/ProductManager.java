package com.billing.app.domain.application;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.repository.CustomException;
import com.billing.app.domain.repository.JdbcProductDAO;
import com.billing.app.domain.repository.ProductDAO;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductManager {
    public void execute(ArrayList arrayList) {
        Product product = new Product(arrayList.get(2).toString(), arrayList.get(3).toString(), arrayList.get(4).toString(), arrayList.get(5).toString(), Float.parseFloat(arrayList.get(6).toString()));
        ProductDAO productDAO = new JdbcProductDAO();


    }
}
