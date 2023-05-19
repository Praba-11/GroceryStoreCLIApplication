package com.billing.app.domain.presentation.sale;

import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesCLIValidator {
    public Map<String, Object> validateList(List<String> values) throws InvalidArgumentException, TemplateMismatchException {
        Map<String, Object> parseMap = new HashMap<>();
        int range = 0, page = 0;
        String attribute = null, searchText = null;
        if (values.size() == 0) {
            range = 0; page = 0;
            attribute = null; searchText = null;
        } else if (values.size() == 2) {
            String notation = values.get(0);
            if (notation.equals("-p")) {
                range = Integer.parseInt(values.get(1));
            }
            else if (notation.equals("-s")) {
                searchText = values.get(1);
            }
            else {
                throw new InvalidArgumentException("Invalid notation provided. Provide a valid list notation.");
            }

        } else if (values.size() == 3) {
            System.out.println(values);
            String notation = values.get(0);
            if (notation.equals("-p")) {
                range = Integer.parseInt(values.get(1));
                page = Integer.parseInt(values.get(2));
            }
            else if (notation.equals("-s")) {
                attribute = values.get(1);
                searchText = values.get(2);
            }
            else {
                throw new InvalidArgumentException("Invalid notation provided. Provide a valid list notation.");
            }

        } else if (values.size() == 6) {
            String firstNotation = values.get(0);
            String secondNotation = values.get(3);
            if (firstNotation.equals("-s") && secondNotation.equals("-p")) {
                range = Integer.parseInt(values.get(4));
                page = Integer.parseInt(values.get(5));
                attribute = values.get(1);
                searchText = values.get(2);
            }
        } else {
            throw new TemplateMismatchException("Invalid number of parameters provided. Please provide with right attributes according to the template.");
        }

        parseMap.put("range", range);
        parseMap.put("page", page);
        parseMap.put("attribute", attribute);
        parseMap.put("searchtext", searchText);


        return parseMap;
    }
}
