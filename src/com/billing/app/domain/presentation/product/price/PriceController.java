package com.billing.app.domain.presentation.product.price;

import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.service.product.ProductService;
import com.billing.app.domain.service.product.ProductServiceInterface;

import java.sql.SQLException;
import java.util.List;

public class PriceController {
    ProductServiceInterface productServiceInterface = new ProductService();
    public void update(List<String> priceUpdate) throws TemplateMismatchException, SQLException, IllegalArgumentException {
        try {
            int expectedLength = 2;
            int actualLength = priceUpdate.size();
            if (actualLength == expectedLength) {
                String code = priceUpdate.get(0);
                float price = Float.parseFloat(priceUpdate.get(1));
                productServiceInterface.priceUpdate(code, price);
            } else {
                throw new TemplateMismatchException("Invalid attributes length (Code, Quantity). " +
                        "Expected: " + expectedLength + ". Provided: " + actualLength);
            }
        }
        catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Stock quantity cannot be a string.");
        }
    }
}
