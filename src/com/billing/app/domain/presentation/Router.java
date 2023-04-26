package com.billing.app.domain.presentation;


import java.util.ArrayList;
import java.util.Scanner;

public class Router {
    public void module(ArrayList<String> arrayList) throws Throwable {
        ProductRouter productRouter = new ProductRouter();
        String module = arrayList.get(0).toString();
        
        switch (module) {
            case "product":
                productRouter.parse(arrayList);
                
        }
    }
}
