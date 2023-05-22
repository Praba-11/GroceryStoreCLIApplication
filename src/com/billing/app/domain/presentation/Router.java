package com.billing.app.domain.presentation;


import com.billing.app.domain.entity.Store;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.presentation.product.ProductCLI;
import com.billing.app.domain.presentation.product.price.PriceCLI;
import com.billing.app.domain.presentation.product.stock.StockCLI;
import com.billing.app.domain.presentation.purchase.PurchaseCLI;
import com.billing.app.domain.presentation.sale.SalesCLI;
import com.billing.app.domain.presentation.store.StoreCLI;
import com.billing.app.domain.presentation.store.StoreController;
import com.billing.app.domain.presentation.unit.UnitCLI;
import com.billing.app.domain.presentation.user.UserCLI;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class Router {
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
    StoreController storeController = new StoreController();
    Unit unit;
    public void admin(String command) throws ParseException, SQLException {
        String[] splitCommand = command.trim().split("\\s+");
        String module = splitCommand[0];
        Store store = storeController.view();
        if (store != null) {
            switch (module) {
                case "product" -> product(command);
                case "unit" -> unit(command);
                case "store" -> store(command);
                case "user" -> user(command);
                case "purchase" -> purchase(command);
                case "sales" -> sales(command);
                case "stock" -> stock(command);
                case "price" -> price(command);
                case "help" -> System.out.println(help());
                default -> System.out.println("Invalid module named " + module + ". Please provide a valid command. " +
                        "\nFor queries, please use command 'help'");
            }
        } else {
            if (module.equals("store")) {
                store(command);
            } else {
                System.out.println("Store not created. Please create store.");
            }
        }
    }


    public void purchaseUser(String command) throws ParseException {
        String[] splitCommand = command.trim().split("\\s+");
        String module = splitCommand[0];
        switch (module) {
            case "purchase" -> purchase(command);
            case "stock" -> stock(command);
            case "price" -> price(command);
            default -> System.out.println("Invalid module named " + module + ". Please provide a valid command. \n" +
                    "For queries, please use command 'help'");
        }
    }

    public void salesUser(String command) throws ParseException {
        String[] splitCommand = command.trim().split("\\s+");
        String module = splitCommand[0];
        switch (module) {
            case "sales" -> sales(command);
            case "stock" -> stock(command);
            case "price" -> price(command);
            default -> System.out.println("Invalid module named " + module + ". Please provide a valid command. \n" +
                            "For queries, please use command 'help'");
        }
    }

    private void store(String command) {
        storeCLI = new StoreCLI();
        splitBySpaces = main.splitBySpaces(command);
        if (splitBySpaces.size() == 1) {
            System.out.println("Action not provided. Please provide a valid command.\n" +
                    "For queries, please use command 'help'");
        } else {
            String storeCommand = command.substring(command.indexOf(splitBySpaces.get(1)));
            storeCLI.execute(storeCommand);
        }
    }

    private void product(String command) {
        productCLI = new ProductCLI();
        splitBySpaces = main.splitBySpaces(command);
        String productCommand = null;
        if (splitBySpaces.size() == 1) {
            System.out.println("Action not provided. Please provide a valid command.\n" +
                    "For queries, please use command 'help'");
        } else {
            productCommand = command.substring(command.indexOf(splitBySpaces.get(1)));
        }
        productCLI.execute(productCommand);
    }

    private void unit(String command) {
        String unitCommand = null;
        unitCLI = new UnitCLI();
        splitBySpaces = main.splitBySpaces(command);
        if (splitBySpaces.size() == 1) {
            System.out.println("Action not provided. Please provide a valid command.\n" +
                    "For queries, please use command 'help'");
        } else {
            unitCommand = command.substring(command.indexOf(splitBySpaces.get(1)));
        }
        unitCLI.execute(unitCommand);
    }

    private void user(String command) {
        userCLI = new UserCLI();
        splitBySpaces = main.splitBySpaces(command);
        if (splitBySpaces.size() == 1) {
            System.out.println("Action not provided. Please provide a valid command.\n" +
                    "For queries, please use command 'help'");
        } else {
            String userCommand = command.substring(command.indexOf(splitBySpaces.get(1)));
            userCLI.execute(userCommand);
        }
    }

    private void purchase(String command) throws ParseException {
        purchaseCLI = new PurchaseCLI();
        splitBySpaces = main.splitBySpaces(command);
        if (splitBySpaces.size() == 1) {
            System.out.println("Action not provided. Please provide a valid command.\n" +
                    "For queries, please use command 'help'");
        } else {
            String purchaseCommand = command.substring(command.indexOf(splitBySpaces.get(1)));
            purchaseCLI.execute(purchaseCommand);
        }
    }

    private void sales(String command) throws ParseException {
        salesCLI = new SalesCLI();
        splitBySpaces = main.splitBySpaces(command);
        if (splitBySpaces.size() == 1) {
            System.out.println("Action not provided. Please provide a valid command.\n" +
                    "For queries, please use command 'help'");
        } else {
            String salesCommand = command.substring(command.indexOf(splitBySpaces.get(1)));
            salesCLI.execute(salesCommand);
        }
    }

    private void stock(String command) {
        splitBySpaces = main.splitBySpaces(command);
        splitBySpaces.remove(0);
        stockCLI = new StockCLI();
        stockCLI.execute(splitBySpaces);
    }

    private void price(String command) {
        splitBySpaces = main.splitBySpaces(command);
        splitBySpaces.remove(0);
        priceCLI = new PriceCLI();
        priceCLI.execute(splitBySpaces);
    }


    private String help() {
        String help = """
        
        ----------------------------------------------------------------------------------------------------------------
         STORE                        USER                        PRODUCT                        UNIT       
        ----------------------------------------------------------------------------------------------------------------        
         store create help            user create help            product create help            unit create help    
         store create <arguments>     user create <arguments>     product create <arguments>     unit create <arguments>
         store edit help              user edit help              product edit help              unit edit help      
         store edit <arguments>       user edit <arguments>       product edit <arguments>       unit edit <arguments>
         store delete help            user delete help            product delete help            unit delete help
         store delete                 user delete <argument>      product delete <argument>      unit delete <argument> 
         store view help              user list help              product list help              unit list help
         store view                   user list <arguments>       product list <arguments>       unit list <arguments>  
         
        ----------------------------------------------------------------------------------------------------------------
         PURCHASE                     SALES                       PRICE                          STOCK
        ----------------------------------------------------------------------------------------------------------------
         purchase create help         sell create help            price update help              stock update <arguments>
         purchase create <arguments>  sell create <arguments>     price update <arguments>       stock update help
         purchase delete help         sell delete help            
         purchase delete <argument>   sell delete <argument>      
         purchase count help          sell count help
         purchase count               sell count
         purchase list help           sell list help
         purchase list <arguments>    sell list <arguments>
        ----------------------------------------------------------------------------------------------------------------
        """;
        return help;
    }
}
