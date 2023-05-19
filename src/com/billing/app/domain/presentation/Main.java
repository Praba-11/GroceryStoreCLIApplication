package com.billing.app.domain.presentation;

import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.sale.SalesController;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException, SQLException, CodeNotFoundException, TemplateMismatchException, ClassNotFoundException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print(">> ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            } else {
                Router router = new Router();
                SalesController salesController = new SalesController();
                List<String> sale = new ArrayList<>();
                sale.add("11-07-2023");
                sale.add("21734");
                sale.add("101e");
                sale.add("3");
                sale.add("103a");
                sale.add("4");
                salesController.create(sale);


//                router.module(input);
            }
        }
    }




    public List<String> splitBySpaces(String command) {
        List<String> splitBySpaces = new ArrayList<>();
        String[] parts = command.trim().split("\\s+");
        for (String part : parts) {
            splitBySpaces.add(part.trim());
        }
        return splitBySpaces;
    }
}

