package com.billing.app.domain.presentation.product.price;

import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;

import java.sql.SQLException;
import java.util.List;

public class PriceCLI {
    PriceController priceController = new PriceController();
    public void execute(List<String> attributes) {
        try {
            String action = attributes.remove(0);
            if (action.equals("update")) {
                priceController.update(attributes);
            } else {
                throw new InvalidArgumentException("Invalid attribute '" + action + "' in provided command. \n" +
                        "Please provide a valid command.");
            }
        } catch (InvalidArgumentException exception) {
            System.out.println("Attribute error in command: " + exception.getMessage());
        } catch (TemplateMismatchException exception) {
            System.out.println("Incompatible length. " + exception.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
