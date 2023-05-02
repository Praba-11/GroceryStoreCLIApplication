package com.billing.app.domain.presentation;


import com.billing.app.domain.exceptions.ProductException;

import java.util.ArrayList;
import java.util.Scanner;

public class Router {
    public void module(ArrayList<String> arrayList) throws NoSuchFieldException, ClassNotFoundException, ProductException, IllegalAccessException {
        ProductRouter productRouter;
        UnitRouter unitRouter;
        String module = arrayList.get(0);
        
        switch (module) {
            case "product":
                productRouter = new ProductRouter();
                productRouter.execute(arrayList);
                break;

            case "unit":
                unitRouter = new UnitRouter();
                unitRouter.execute(arrayList);
                break;
                
        }
    }
}
