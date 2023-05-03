package com.billing.app.domain.presentation;

import com.billing.app.domain.database.ProductDAO;
import com.billing.app.domain.database.ProductJdbcDAO;
import com.billing.app.domain.exceptions.ProductException;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws Throwable {

        // Code for separating string input obtained from the command line interface

        ArrayList create = new ArrayList();
        ArrayList edit = new ArrayList();
        ArrayList delete = new ArrayList();
        ArrayList list = new ArrayList();

        create.add("product");
        create.add("create");
        create.add("v4");
        create.add("bottle guard");
        create.add("kg");
        create.add("vegetables");
        create.add("55");


        edit.add("product");
        edit.add("edit");
        edit.add("code");
        edit.add("v4");
        edit.add("n");
        edit.add("snake guard");
//        edit.add("unitCode");
//        edit.add("kg");
//        edit.add("type");
//        edit.add("vegetables");
        edit.add("price");
        edit.add("45");


        list.add("product");
        list.add(("list"));
        list.add("-s");
        list.add("-p");
        list.add("3");
        list.add("3");


        delete.add("product");
        delete.add("delete");
        delete.add("4");


        Router router  = new Router();
        router.module(edit);

    }

}