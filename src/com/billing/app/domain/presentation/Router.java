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
import java.util.List;

public class Router {
    ProductCLI productCLI = new ProductCLI();
    UnitCLI unitCLI = new UnitCLI();
    StoreCLI storeCLI = new StoreCLI();
    UserCLI userCLI = new UserCLI();
    PurchaseCLI purchaseCLI = new PurchaseCLI();
    SalesCLI salesCLI = new SalesCLI();
    StockCLI stockCLI = new StockCLI();
    PriceCLI priceCLI = new PriceCLI();
    Main main = new Main();
    List<String> splitBySpaces;
    StoreController storeController = new StoreController();
    Unit unit;
    public void admin(String command) throws SQLException {
        String[] splitCommand = command.trim().split("\\s+");
        String module = splitCommand[0];
        Store store = storeController.view();
        if (store != null) {
            switch (module) {
                case "product" -> {
                    String productCommand = split(command);
                    productCLI.execute(productCommand);
                }
                case "unit" -> {
                    String unitCommand = split(command);
                    unitCLI.execute(unitCommand);
                }
                case "store" -> {
                    String storeCommand = split(command);
                    storeCLI.execute(storeCommand);
                }
                case "user" -> {
                    String userCommand = split(command);
                    userCLI.execute(userCommand);
                }
                case "purchase" -> {
                    String purchaseCommand = split(command);
                    purchaseCLI.execute(purchaseCommand);
                }
                case "sales" -> {
                    String salesCommand = split(command);
                    salesCLI.execute(salesCommand);
                }
                case "stock" -> {
                    splitBySpaces = listSplit(command);
                    stockCLI.execute(splitBySpaces);
                }
                case "price" -> {
                    splitBySpaces = listSplit(command);
                    priceCLI.execute(splitBySpaces);
                }
                case "help" -> System.out.println(help());
                default -> System.out.println("Invalid module named " + module + ". Please provide a valid command. " +
                        "\nFor queries, please use command 'help'");
            }
        } else {
            if (module.equals("store")) {
                String storeCommand = split(command);
                storeCLI.execute(storeCommand);
            } else {
                System.out.println("Store not created. Please create store.");
            }
        }
    }

    public void purchaseUser(String command) {
        String[] splitCommand = command.trim().split("\\s+");
        String module = splitCommand[0];
        switch (module) {
            case "product":
                String productCommand = split(command);
                splitBySpaces = main.splitBySpaces(productCommand);
                String action = splitBySpaces.get(0);
                if (action.equals("list")) {
                    productCLI.list(command);
                } else {
                    System.out.println("Invalid command. Please provide a valid command.");
                }
                break;
            case "purchase":
                String purchaseCommand = split(command);
                purchaseCLI.execute(purchaseCommand);
                break;
            default:
                System.out.println("Invalid module named " + module + ". Please provide a valid command. \n" +
                        "For queries, please use command 'help'");
                break;
        }
    }

    public void salesUser(String command) {
        String[] splitCommand = command.trim().split("\\s+");
        String module = splitCommand[0];
        switch (module) {
            case "product":
                String productCommand = split(command);
                splitBySpaces = main.splitBySpaces(productCommand);
                String action = splitBySpaces.get(0);
                if (action.equals("list")) {
                    productCLI.list(command);
                } else {
                    System.out.println("Invalid command. Please provide a valid command.");
                }
                break;
            case "sales":
                String salesCommand = split(command);
                salesCLI.execute(salesCommand);
                break;
            default:
                System.out.println("Invalid module named " + module + ". Please provide a valid command. \n" +
                        "For queries, please use command 'help'");
                break;
        }
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


    private List<String> listSplit(String command) {
        splitBySpaces = main.splitBySpaces(command);
        splitBySpaces.remove(0);
        return splitBySpaces;
    }

    private String split(String command) {
        String moduleCommand = null;
        splitBySpaces = main.splitBySpaces(command);
        if (splitBySpaces.size() == 1) {
            System.out.println("Action not provided. Please provide a valid command.\n" +
                    "For queries, please use command 'help'");
        } else {
            moduleCommand = command.substring(command.indexOf(splitBySpaces.get(1)));
        }
        return moduleCommand;
    }
}
