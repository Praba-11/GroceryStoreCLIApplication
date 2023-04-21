package com.billing.app.domain.application;

import com.billing.app.domain.repository.CustomException;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductRouter {
    public static void main(String[] args) throws Throwable {


        ArrayList arrayList = new ArrayList();
        arrayList.add("product");
//        arrayList.add("create");
        arrayList.add("edit");
        arrayList.add("code");
        arrayList.add("101d");
        arrayList.add("name");
        arrayList.add("brinjal");
//        arrayList.add("unitCode");
//        arrayList.add("kg");
//        arrayList.add("type");
//        arrayList.add("vegetables");
        arrayList.add("price");
        arrayList.add(40);

        ProductManager productManager = new ProductManager();
        if (arrayList.get(1).equals("create")) {
            try {
                if(productManager.create(arrayList) != null)
                    System.out.println("Product created successfully!");
            }
            catch (CustomException exception) {
                System.out.println(exception.getMessage());
            }
        }
        else if (arrayList.get(1).equals("edit")) {
            try {
                if (productManager.edit(arrayList) != null)
                    System.out.println("Product edited successfully!");
            }
            catch (CustomException exception) {
                System.out.println(exception.getMessage());
            }
        }

    }
}
