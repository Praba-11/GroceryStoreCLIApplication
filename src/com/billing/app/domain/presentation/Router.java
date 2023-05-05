package com.billing.app.domain.presentation;


import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.ProductException;
import com.billing.app.domain.exceptions.unit.CodeNullException;
import com.billing.app.domain.exceptions.unit.TemplateMismatchException;

import java.util.ArrayList;
import java.util.Scanner;

public class Router {
    Unit unit;
    public void module(ArrayList<String> arrayList) throws Throwable {
        ProductRouter productRouter;
        UnitRouter unitRouter;
        StoreRouter storeRouter;
        UserRouter userRouter;
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

            case "store":
                storeRouter = new StoreRouter();
                storeRouter.execute(arrayList);
                break;

            case "user":
                userRouter = new UserRouter();
//                userRouter.execute(arrayList);
                break;
        }
    }
}
