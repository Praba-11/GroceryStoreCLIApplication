package com.billing.app.domain.presentation;

import java.util.ArrayList;
import java.util.Scanner;

public class ProductRouter {

    public void parse(ArrayList<String> arrayList) throws Throwable {
        String action = arrayList.get(1);
        Scanner scanner = new Scanner(System.in);
        ProductActionBuilder productAction = new ProductActionBuilder();

        switch (action) {

            case "create":
                try {
                    if (productAction.create(arrayList) != null)
                        System.out.println("Product created successfully!");
                } catch (Throwable exception) {
                    System.out.println("Error creating record into database. " + exception.getMessage());
                }
                break;


            case "edit":
                try {
                    if (productAction.edit(arrayList) != null)
                        System.out.println("Product edited successfully!");
                } catch (Throwable exception) {
                    System.out.println("Error editing record into database. " + exception.getMessage());
                }
                break;


            case "delete":
                try {
                    System.out.println("Are you sure you want to delete the product? y/n");
                    String choice = scanner.next();
                    if (choice.equals("y")) {
                        if (productAction.delete(arrayList))
                            System.out.println("Product deleted successfully!");
                    } else if (choice.equals("n"))
                        System.out.println("Product not deleted.");
                    else
                        System.out.println("Invalid choice, please try again.");
                } catch (Throwable exception) {
                    System.out.println("No records deleted in database. " + exception.getMessage());
                }
                break;

        }
    }
}
