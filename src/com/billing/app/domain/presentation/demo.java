package com.billing.app.domain.presentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class demo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        ArrayList<String> separatedStrings = separateString(input);

        System.out.println("Separated strings: " + separatedStrings);
    }

    private static ArrayList<String> separateString(String input) {
        // Replace multiple spaces, commas, and special characters with a single space
        String cleanedInput = input.replaceAll("[\\s,\\p{P}]+", " ");

        // Split the cleaned input into an array of strings
        String[] stringArray = cleanedInput.split(" ");

        // Convert the array to an ArrayList
        ArrayList<String> separatedStrings = new ArrayList<>(Arrays.asList(stringArray));

        return separatedStrings;
    }
}

