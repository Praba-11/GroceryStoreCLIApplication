package com.billing.app.domain.presentation;

import com.billing.app.domain.exceptions.ProductException;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, ProductException, IllegalAccessException {

        // Code for separating string input obtained from the command line interface

        ArrayList arrayList = new ArrayList();

        arrayList.add("product");
//        arrayList.add("create");
        arrayList.add("edit");
//        arrayList.add(("list"));
//        arrayList.add("-s");
//        arrayList.add("-p");
//        arrayList.add(4);
//        arrayList.add(1);
//        arrayList.add("delete");
        arrayList.add("code");
        arrayList.add("101a1");
        arrayList.add("name");
        arrayList.add("vicks action 500");
//        arrayList.add("unitCode");
//        arrayList.add("pc");
//        arrayList.add("type");
//        arrayList.add("medical");
        arrayList.add("price");
        arrayList.add("10");



        Router router  = new Router();
        router.module(arrayList);

    }

}