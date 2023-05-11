package com.billing.app.domain.presentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Throwable {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">> ");
        String input = scanner.nextLine();
        if (input.equals("exit")) {
            System.out.println("exit");
        } else {
            ArrayList<String> command = separateString(input);
            Router router = new Router();
            System.out.println(command);
            router.module(command);
        }
    }

    private static ArrayList<String> separateString(String input) {

        ArrayList<String> splitByCommas = new ArrayList<>();
        ArrayList<String> splitByCommasAndSpaces = new ArrayList<>();

        String[] parts = input.trim().split("\\s*[:,]\\s*");
        for (String part : parts) {
            splitByCommas.add(part);
        }
        String firstStringSet = splitByCommas.get(0);
        String[] sample = firstStringSet.split("\\s+");
        for (String element : sample) {
            splitByCommasAndSpaces.add(element);
        }
        for (int index = 1; index<splitByCommas.size(); index++) {
            splitByCommasAndSpaces.add(splitByCommas.get(index).trim());
        }

        return splitByCommasAndSpaces;

    }
}

