package com.billing.app.domain.presentation.product.price;

import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.service.product.ProductServiceImplementation;
import com.billing.app.domain.service.product.ProductService;

import java.sql.SQLException;
import java.util.List;

public class PriceController {
    ProductService productService = new ProductServiceImplementation();
    public void update(List<String> priceUpdate) throws TemplateMismatchException, SQLException, InvalidArgumentException {
        try {
            int expectedLength = 2;
            int actualLength = priceUpdate.size();
            if (actualLength == expectedLength) {
                String code = priceUpdate.get(0);
                float price = Float.parseFloat(priceUpdate.get(1));
                productService.priceUpdate(code, price);
            } else {
                throw new TemplateMismatchException("Invalid attributes length (Code, Quantity). " +
                        "Expected: " + expectedLength + ". Provided: " + actualLength);
            }
        }
        catch (NumberFormatException exception) {
            throw new InvalidArgumentException("Stock quantity cannot be a string.");
        }
    }
}
