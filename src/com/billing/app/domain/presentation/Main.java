package com.billing.app.domain.presentation;

import com.billing.app.domain.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InvalidArgumentException {
        CLI cli = new CLI();
        cli.execute();
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

