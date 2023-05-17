package com.billing.app.domain.presentation.product.stock;

import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;

import java.sql.SQLException;
import java.util.List;

public class StockCLI {
    StockController stockController = new StockController();
    public void execute(List<String> attributes) {
        try {
            String action = attributes.remove(0);
            if (action.equals("update")) {
                stockController.update(attributes);
            } else {
                throw new IllegalArgumentException("Invalid attribute '" + action + "' in provided command. \n" +
                        "Please provide a valid command.");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Attribute error in command: " + exception.getMessage());
        } catch (TemplateMismatchException exception) {
            System.out.println("Incompatible length. " + exception.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}