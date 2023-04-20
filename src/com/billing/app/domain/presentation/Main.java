package com.billing.app.domain.presentation;

import com.billing.app.domain.application.ProductManager;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Store;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.repository.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Throwable {

        ArrayList arrayList = new ArrayList();
        arrayList.add("product");
        arrayList.add("create");
        arrayList.add("101avb");
        arrayList.add("orange");
        arrayList.add("kg");
        arrayList.add("fruits");
        arrayList.add(25);

        ProductManager productManager = new ProductManager();
        productManager.execute(arrayList);
    }

}