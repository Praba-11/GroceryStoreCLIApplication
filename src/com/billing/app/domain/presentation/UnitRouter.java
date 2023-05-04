package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Product;

import java.util.ArrayList;
import java.util.Scanner;

public class UnitRouter {
    public void execute(ArrayList<String> arrayList) {
        String action = arrayList.get(1);
        Scanner scanner = new Scanner(System.in);
        UnitParser unitParser = new UnitParser();

        switch (action) {

            case "create":
//                try {
//                    if (unitParser.create(arrayList) != null) {
//                        System.out.println("Unit created successfully!");
//                    }
//                } catch (Throwable exception) {
//                    System.out.println("Error creating record into database. \n" + exception.getMessage());
//                }
//                break;

            case "edit":
                try {
                    if (unitParser.edit(arrayList) != null)
                        System.out.println("Product edited successfully!");
//                } catch (Throwable exception) {
//                    System.out.println("Error editing record into database. \n" + exception.getMessage());
//                }

            case "delete":
                try {
                    System.out.println("Are you sure you want to delete the unit? y/n");
                    String choice = scanner.next();
                    if (choice.equals("y")) {
                        if (unitParser.delete(arrayList))
                            System.out.println("Unit deleted successfully!");
                    } else if (choice.equals("n"))
                        System.out.println("Unit not deleted.");
                    else
                        System.out.println("Invalid choice, please try again.");
                } catch (Throwable exception) {
                    System.out.println("No records deleted in database. \n" + exception.getMessage());
                }
                break;


            case "list":
                try {
                    ArrayList<Unit> unitArray = unitParser.list(arrayList);
                    System.out.println("List returned successfully.");
                    formatter.format("%-15s %15s %15s %15s %15s %15s\n", "Code", "Name", "Unit Code", "Type", "Price", "Stock");
                    formatter.format("%-15s %15s %15s %15s %15s %15s\n", "----", "----", "---------", "----", "-----", "-----");
                    for (Unit unit : unitArray) {
                        formatter.format("%-15s %15s %15s %15s %15.2f %15d\n", unit.getName(), products.getName(), products.getUnitCode(), products.getType(), products.getPrice(), products.getStock());
                    }
                    System.out.println(formatter.toString());
                } catch (Throwable exception) {
                    System.out.println("Cannot list the records! \n" + exception.getMessage());
                }
        }
    }
}
