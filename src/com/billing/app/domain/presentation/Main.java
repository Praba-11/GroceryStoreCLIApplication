package com.billing.app.domain.presentation;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws Throwable {

        ArrayList arrayList = new ArrayList();
        arrayList.add("product");
//        arrayList.add("create");
        arrayList.add("edit");
//        arrayList.add(("list"));
//        arrayList.add(4);
//        arrayList.add("delete");
        arrayList.add("code");
        arrayList.add("101h");
        arrayList.add("name");
        arrayList.add("broccoli");
//        arrayList.add("unitCode");
//        arrayList.add("kg");
        arrayList.add("type");
        arrayList.add("vegetables");
//        arrayList.add("price");
//        arrayList.add(50);

        Router router  = new Router();
        router.module(arrayList);

    }

}