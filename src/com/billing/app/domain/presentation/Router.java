package com.billing.app.domain.presentation;


import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.product.ProductCLI;
import com.billing.app.domain.presentation.product.price.PriceCLI;
import com.billing.app.domain.presentation.purchase.PurchaseCLI;
import com.billing.app.domain.presentation.sale.SalesCLI;
import com.billing.app.domain.presentation.product.stock.StockCLI;
import com.billing.app.domain.presentation.store.StoreCLI;
import com.billing.app.domain.presentation.unit.UnitCLI;
import com.billing.app.domain.presentation.user.UserCLI;

import java.text.ParseException;
import java.util.List;

public class Router {
    List<String> attributes;
    Unit unit;
    public void module(String command) throws ParseException {
        ProductCLI productCLI;
        UnitCLI unitCLI;
        StoreCLI storeCLI;
        UserCLI userCLI;
        PurchaseCLI purchaseCLI;
        SalesCLI salesCLI;
        StockCLI stockCLI;
        PriceCLI priceCLI;
        Main main = new Main();
        List<String> splitBySpaces;

        String[] splitCommand = command.trim().split("\\s+");
        String module = splitCommand[0];


        switch (module) {
            case "product":
                productCLI = new ProductCLI();
                splitBySpaces = main.splitBySpaces(command);
                if (splitBySpaces.size() == 1) {
                    System.out.println("Action not provided. Please provide a valid command.\n" +
                            "For queries, please use command 'help'");
                } else {
                    String productCommand = command.substring(command.indexOf(splitBySpaces.get(1)));
                    productCLI.execute(productCommand);
                }
                break;

            case "unit":
                unitCLI = new UnitCLI();
                splitBySpaces = main.splitBySpaces(command);
                if (splitBySpaces.size() == 1) {
                    System.out.println("Action not provided. Please provide a valid command.\n" +
                            "For queries, please use command 'help'");
                } else {
                    String unitCommand = command.substring(command.indexOf(splitBySpaces.get(1)));
                    unitCLI.execute(unitCommand);
                }
                break;

            case "store":
                storeCLI = new StoreCLI();
                splitBySpaces = main.splitBySpaces(command);
                if (splitBySpaces.size() == 1) {
                    System.out.println("Action not provided. Please provide a valid command.\n" +
                            "For queries, please use command 'help'");
                } else {
                    String storeCommand = command.substring(command.indexOf(splitBySpaces.get(1)));
                    storeCLI.execute(storeCommand);
                }
                break;

            case "user":
                userCLI = new UserCLI();
                splitBySpaces = main.splitBySpaces(command);
                if (splitBySpaces.size() == 1) {
                    System.out.println("Action not provided. Please provide a valid command.\n" +
                            "For queries, please use command 'help'");
                } else {
                    String userCommand = command.substring(command.indexOf(splitBySpaces.get(1)));
                    userCLI.execute(userCommand);
                }
                break;

            case "purchase":
                purchaseCLI = new PurchaseCLI();
//                purchaseCLI.execute(arrayList);
                break;

            case "sales":
                salesCLI = new SalesCLI();
//                salesCLI.execute(arrayList);
                break;

            case "stock":
                splitBySpaces = main.splitBySpaces(command);
                splitBySpaces.remove(0);
                stockCLI = new StockCLI();
                stockCLI.execute(splitBySpaces);
                break;

            case "price":
                splitBySpaces = main.splitBySpaces(command);
                splitBySpaces.remove(0);
                priceCLI = new PriceCLI();
                priceCLI.execute(splitBySpaces);
                break;

            default:
                System.out.println("Invalid module named " + module + ". Please provide a valid command. \nFor queries, please use command 'help'");
                break;
        }
    }



    private String help() {
        System.out.println("--------------------------------------------- HELP ---------------------------------------------");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s%n", "Store", "User", "Product", "Unit", "Purchase", "Sales");
        System.out.println("--------------------------------------------");
        return null;
    }
}
