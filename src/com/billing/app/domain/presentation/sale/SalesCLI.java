package com.billing.app.domain.presentation.sale;

import com.billing.app.domain.entity.Sales;
import com.billing.app.domain.entity.SalesItem;
import com.billing.app.domain.exceptions.CodeOrIDNotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.NegativeStockException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.Main;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SalesCLI {
    SalesController salesController = new SalesController();
    ArrayList<String> values;
    Scanner scanner = new Scanner(System.in);
    Main main = new Main();
    List<String> splitBySpaces;
    SalesValidator salesValidator = new SalesValidator();
    public void execute(String salesCommand) {
        splitBySpaces = main.splitBySpaces(salesCommand);
        String action = splitBySpaces.get(0);
        switch (action) {
            case "products":
                splitBySpaces = main.splitBySpaces(salesCommand);
                String create;
                if (splitBySpaces.size() == 1) {
                    create = scanner.nextLine();
                } else {
                    create = salesCommand.substring(salesCommand.indexOf(splitBySpaces.get(1)));
                }
                seller(create);
                break;



            case "delete":
                splitBySpaces = main.splitBySpaces(salesCommand);
                String delete;
                if (splitBySpaces.size() == 1) {
                    System.out.println("Template mismatch. Please provide a valid command.");
                } else {
                    delete = salesCommand.substring(salesCommand.indexOf(splitBySpaces.get(1)));
                    deleter(delete);
                }
                break;


            case "list":
                splitBySpaces = main.splitBySpaces(salesCommand);
                String list;
                if (splitBySpaces.size() == 1) {
                    list = "";
                    lister(list);
                } else {
                    list = salesCommand.substring(salesCommand.indexOf(splitBySpaces.get(1)));
                    lister(list);
                }
                break;

            case "count":
                splitBySpaces = main.splitBySpaces(salesCommand);
                String count = null;
                if (splitBySpaces.size() == 1) {
                    counter(null);
                } else {
                    count = salesCommand.substring(salesCommand.indexOf(splitBySpaces.get(1)));
                    counter(count);
                }
                break;
        }
    }


    private void sales(List<String> purchase) {
        try {
            Sales sales = salesController.create(purchase);
            bill(sales);
        } catch (SQLException exception) {
            System.out.println("Cannot sell products." + exception.getMessage());
        } catch (CodeOrIDNotFoundException exception) {
            System.out.println("Invalid product code. " + exception.getMessage());
        } catch (TemplateMismatchException exception) {
            System.out.println("Template mismatch. " + exception.getMessage());
        } catch (ParseException exception) {
            System.out.println("Invalid date. Provided date cannot be parsed. " + exception.getMessage());
        } catch (NegativeStockException e) {
            System.out.println("Incompatible Stock. " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> separator(String purchaseCommand) {
        List<String> purchase = null;
        String modifiedInput = purchaseCommand.replaceAll("\\s", "");
        Pattern pattern = Pattern.compile("^(\\d+,\\d{2}-\\d{2}-\\d{4}),(\\[.+\\])$");
        Matcher matcher = pattern.matcher(modifiedInput);
        if (matcher.find()) {
            String part1 = matcher.group(1);
            String part2 = matcher.group(2);
            part2 = part2.substring(1, part2.length() - 1);
            purchase = new ArrayList<>(List.of(part1.split(",")));
            List<String> elementsList = new ArrayList<>(List.of(part2.split("\\],\\[")));
            for (String element : elementsList) {
                purchase.addAll(List.of(element.split(",")));
            }
        }
        return purchase;
    }


    private void seller(String create) {
        List<String> salesDetails;
        salesDetails = separator(create);
        if (salesDetails != null) {
            sales(salesDetails);
        } else {
            System.out.println("Template mismatch. Please provide a valid command.");
        }
    }

    private void deleter(String delete) {
        try {
            if (delete.equals("help")) {
//                        purchaseHelp.deletePurchase();
            } else {
                boolean isDeleted = false;
                isDeleted = salesController.delete(delete);
                System.out.println(isDeleted);
                System.out.println("Sales '" + delete + "' deleted successfully.");
            }
        } catch (SQLException exception) {
            System.out.print("Unable to delete sales. ");
            String sqlMessage = salesValidator.validateSQLState(exception);
            System.out.println(sqlMessage);
        } catch (CodeOrIDNotFoundException exception) {
            System.out.println("Provided invoice not found. " + exception.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvalidArgumentException exception) {
            System.out.println("Invalid argument. " + exception.getMessage());
        } catch (TemplateMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    private void lister(String list) {
        String regex = "\\s*-\\w+|\\b\\w+\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(list);
        List<String> listCommand = new ArrayList<>();
        while (matcher.find()) {
            String part = matcher.group().trim();
            listCommand.add(part);
        }

        if (listCommand.size() == 1 && listCommand.get(0).equals("help")) {
//                    purchaseHelp.listPurchase();
        } else {
            try {
                List<Sales> salesArray = salesController.list(listCommand);
                System.out.println("List returned successfully.");
                for (Sales sales : salesArray) {
                    String output = String.format("invoice: %-10d, date: %-15s, grandtotal: %-15.2f",
                            sales.getInvoice(), sales.getDate().toString(), sales.getGrandTotal());
                    System.out.println(output);
                }
            } catch (InvalidArgumentException exception) {
                System.out.println("Incompatible argument. " + exception.getMessage());
            } catch (TemplateMismatchException exception) {
                System.out.println("Template mismatch. " + exception.getMessage());
            } catch (SQLException exception) {
                System.out.print("Unable to list purchase. ");
                String sqlMessage = salesValidator.validateSQLState(exception);
                System.out.println(sqlMessage);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void counter(String date) {
        List<String> list;
        if (date == null) {
            list = new ArrayList<>();
        } else {
            list = new ArrayList<>(List.of(date.split(",")));
        }
        try {
            int count = salesController.count(list);
            System.out.println(count);
        } catch (SQLException exception) {
            System.out.print("Unable to count purchase. ");
            String sqlMessage = salesValidator.validateSQLState(exception);
            System.out.println(sqlMessage);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TemplateMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    private void bill(Sales sales) {
        System.out.println("Bill printed successfully!\n");
        String template = "Invoice ID: %s\nDate: %s\n\nSold Items:\n----------------------\n%s\n----------------------\n\nTotal: %s\n";
        StringBuilder itemLines = new StringBuilder();
        for (SalesItem item : sales.getListOfSalesItem()) {
            itemLines.append(String.format("%s (Quantity: %.2f, Price: ₹%.2f)\n", item.getName(), item.getQuantity(), item.getCostPrice()));
        }
        String invoice = String.format(template, sales.getInvoice(), sales.getDate(), itemLines.toString(), sales.getGrandTotal());
        System.out.println(invoice);
    }
}
