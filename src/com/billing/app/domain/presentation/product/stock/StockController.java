package com.billing.app.domain.presentation.product.stock;

import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.service.product.ProductServiceImplementation;
import com.billing.app.domain.service.product.ProductService;

import java.sql.SQLException;
import java.util.List;

public class StockController {
    ProductService productService = new ProductServiceImplementation();
    public void update(List<String> stockUpdate) throws TemplateMismatchException, SQLException, InvalidArgumentException {
        try {
            int expectedLength = 2;
            int actualLength = stockUpdate.size();
            if (actualLength == expectedLength) {
                String code = stockUpdate.get(0);
                float quantity = Float.parseFloat(stockUpdate.get(1));
                productService.stockUpdate(code, quantity);
            } else {
                throw new TemplateMismatchException("Invalid attributes length (Code, Price). " +
                        "Expected: " + expectedLength + ". Provided: " + actualLength);
            }
        }
        catch (NumberFormatException exception) {
            throw new InvalidArgumentException("Price cannot be a string.");
        }
    }
}
