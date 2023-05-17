package com.billing.app.domain.presentation;

import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException, TemplateMismatchException {


        Scanner scanner = new Scanner(System.in);
        System.out.print(">> ");
        String input = scanner.nextLine();
        if (input.equals("exit")) {
            System.out.println("exit");
        } else {
//            ArrayList<String> command = separateString(input);
            Router router = new Router();
            router.module(input);
        }
    }


//    private static ArrayList<String> separateString(String input) {
//
//        ArrayList<String> splitByCommas = new ArrayList<>();
//        ArrayList<String> splitByCommasAndSpaces = new ArrayList<>();
//
//        String[] parts = input.trim().split("\\s*[,]\\s*");
//        for (String part : parts) {
//            splitByCommas.add(part);
//        }
//        String firstStringSet = splitByCommas.get(0);
//        String[] sample = firstStringSet.split("\\s+");
//
//        for (String element : sample) {
//            splitByCommasAndSpaces.add(element);
//        }
//        for (int index = 1; index<splitByCommas.size(); index++) {
//            splitByCommasAndSpaces.add(splitByCommas.get(index).trim());
//        }
//
//        return splitByCommasAndSpaces;
//
//    }



    public List<String> splitBySpaces(String command) throws TemplateMismatchException {
        List<String> splitBySpaces = new ArrayList<>();
        String specialCharsPattern = ".*[,:!@#$%^&*()].*";
        if (command.matches(specialCharsPattern)) {
            String[] parts = command.trim().split("\\s+");
            for (String part : parts) {
                splitBySpaces.add(part.trim());
            }
        } else {
            throw new TemplateMismatchException("Provided command is of invalid template.");
        }
        return splitBySpaces;
    }

    public List<String> splitByCommas(String command) {
        List<String> splitByCommas = new ArrayList<>();
        String[] parts = command.trim().split("\\s*,\\s*");
        for (String part : parts) {
            splitByCommas.add(part.trim());
        }
        return splitByCommas;
    }
}

