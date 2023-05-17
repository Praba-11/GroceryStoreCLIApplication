package com.billing.app.domain.presentation;


import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.presentation.product.ProductCLI;
import com.billing.app.domain.presentation.product.price.PriceCLI;
import com.billing.app.domain.presentation.purchase.PurchaseCLI;
import com.billing.app.domain.presentation.sale.SalesCLI;
import com.billing.app.domain.presentation.product.stock.StockCLI;
import com.billing.app.domain.presentation.store.StoreCLI;
import com.billing.app.domain.presentation.unit.UnitCLI;
import com.billing.app.domain.presentation.user.UserCLI;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Router {
    List<String> attributes;
    Unit unit;
    public void module(ArrayList<String> arrayList) throws ParseException {
        ProductCLI productCLI;
        UnitCLI unitCLI;
        StoreCLI storeCLI;
        UserCLI userCLI;
        PurchaseCLI purchaseCLI;
        SalesCLI salesCLI;
        StockCLI stockCLI;
        PriceCLI priceCLI;
        String module = arrayList.get(0);

        switch (module) {
            case "product":
                productCLI = new ProductCLI();
                productCLI.execute(arrayList);
                break;

            case "unit":
                unitCLI = new UnitCLI();
                unitCLI.execute(arrayList);
                break;

            case "store":
                storeCLI = new StoreCLI();
                storeCLI.execute(arrayList);
                break;

            case "user":
                userCLI = new UserCLI();
                userCLI.execute(arrayList);
                break;

            case "purchase":
                purchaseCLI = new PurchaseCLI();
                purchaseCLI.execute(arrayList);
                break;

            case "sales":
                salesCLI = new SalesCLI();
                salesCLI.execute(arrayList);
                break;

            case "stock":
                arrayList.remove(0);
                stockCLI = new StockCLI();
                stockCLI.execute(arrayList);
                break;

            case "price":
                arrayList.remove(0);
                priceCLI = new PriceCLI();
                priceCLI.execute(arrayList);
                break;
        }
    }
}
