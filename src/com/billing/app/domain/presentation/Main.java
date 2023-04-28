package com.billing.app.domain.presentation;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws Throwable {

        ArrayList arrayList = new ArrayList();

        arrayList.add("product");
//        arrayList.add("create");
//        arrayList.add("edit");
        arrayList.add(("list"));
//        arrayList.add("-s");
        arrayList.add("-p");
        arrayList.add(4);
        arrayList.add(5);
//        arrayList.add("delete");
//        arrayList.add("code");
//        arrayList.add("101m");
//        arrayList.add("name");
//        arrayList.add("tablet");
//        arrayList.add("unitCode");
//        arrayList.add("pc");
//        arrayList.add("type");
//        arrayList.add("Medical");
//        arrayList.add("price");
//        arrayList.add(9);

        Router router  = new Router();
        router.module(arrayList);

    }

}