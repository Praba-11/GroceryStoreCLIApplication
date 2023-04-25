package com.billing.app.domain.presentation;


import com.billing.app.domain.repository.JdbcProductDAO;
import com.billing.app.domain.repository.ProductDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        ArrayList arrayList = new ArrayList();
        arrayList.add("product");
//        arrayList.add("create");
//        arrayList.add("edit");
        arrayList.add(("list"));
        arrayList.add(4);
//        arrayList.add("delete");
//        arrayList.add("code");
//        arrayList.add("101g");
//        arrayList.add("name");
//        arrayList.add("eggplant");
//        arrayList.add("unitCode");
//        arrayList.add("kg");
//        arrayList.add("type");
//        arrayList.add("vegetables");
//        arrayList.add("price");
//        arrayList.add(40);

        Router router  = new Router();
        router.module(arrayList);

        ProductDAO productDAO = new JdbcProductDAO();
    }

}