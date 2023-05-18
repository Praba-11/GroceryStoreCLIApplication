package com.billing.app.domain.presentation;

import com.billing.app.domain.exceptions.TemplateMismatchException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print(">> ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            } else {
                Router router = new Router();
                router.module(input);
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

