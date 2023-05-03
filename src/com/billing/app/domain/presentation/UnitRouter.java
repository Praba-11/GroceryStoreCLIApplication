package com.billing.app.domain.presentation;

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

        }
    }
}
