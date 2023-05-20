package com.billing.app.domain.presentation.purchase;

import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PurchaseCLI {
    PurchaseController purchaseController = new PurchaseController();
    ArrayList<String> values;

    public void execute(ArrayList<String> stringArrayList) throws ParseException {
        String action = stringArrayList.get(1);
        switch (action) {
            case "products":
                String modifiedInput = input.replaceAll("\\s", "");

                // Splitting the modified input into two parts
                Pattern pattern = Pattern.compile("^(\\d+,\\d{2}-\\d{2}-\\d{4}),(\\[.+\\])$");
                Matcher matcher = pattern.matcher(modifiedInput);

                if (matcher.find()) {
                    String part1 = matcher.group(1);
                    String part2 = matcher.group(2);
                    System.out.println(part2);
                    // Split part2 into individual elements
                    part2 = part2.substring(1, part2.length() - 1);
                    List<String> list = new ArrayList<>(List.of(part1.split(",")));
                    // Split part2 into individual elements
                    List<String> elementsList = new ArrayList<>(List.of(part2.split("\\],\\[")));
                    // Print the individual elements
                    for (String element : elementsList) {
                        list.addAll(List.of(element.split(",")));
                    }
                    System.out.println(list);
                } else {
                    System.out.println("No match found");
                }

                try {
                    purchaseController.create(stringArrayList);
                } catch (SQLException exception) {
                    System.out.println("Cannot purchase products." + exception.getMessage());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (CodeNotFoundException exception) {
                    System.out.println("Invalid product code. " + exception.getMessage());
                } catch (TemplateMismatchException exception) {
                    System.out.println("Template mismatch. " + exception.getMessage());
                }
                break;

            case "delete":
                try {
                    if (stringArrayList.size() == 3) {
                        List<String> values = new ArrayList<>(stringArrayList.subList(2, stringArrayList.size()));
                        System.out.println(values);
                        purchaseController.delete(values);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (CodeNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (TemplateMismatchException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "list":
                List<String> command = stringArrayList.subList(0, stringArrayList.size() - 1);
                String[] lastSplit = stringArrayList.get(stringArrayList.size() - 1).split("\\s+");
                command.addAll(Arrays.asList(lastSplit));
                if (command.size() == 3 && command.get(2).equals("help")) {
//                    productHelp = new ProductHelp();
//                    productHelp.listProduct();
                    System.out.println("purchase help");
                } else {
                    values = new ArrayList<>(command.subList(2, command.size()));
                    try {
                        List<Purchase> purchaseArray = purchaseController.list(values);
                        System.out.println("List returned successfully.");
                        for (Purchase purchases : purchaseArray) {
                            System.out.println(purchases);
                        }
                    } catch (InvalidArgumentException exception) {
                        System.out.println("Incompatible argument. " + exception.getMessage());
                    } catch (TemplateMismatchException exception) {
                        System.out.println("Template mismatch. " + exception.getMessage());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;

            case "count":
                try {
                    List<String> values = new ArrayList<>(stringArrayList.subList(2, stringArrayList.size()));
                    purchaseController.count(values);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (TemplateMismatchException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
