package com.billing.app.domain.presentation.product.stock;

import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.service.product.ProductService;
import com.billing.app.domain.service.product.ProductServiceInterface;

import java.sql.SQLException;
import java.util.List;

public class StockController {
    ProductServiceInterface productServiceInterface = new ProductService();
    public void update(List<String> stockUpdate) throws TemplateMismatchException, SQLException, IllegalArgumentException {
        try {
            int expectedLength = 2;
            int actualLength = stockUpdate.size();
            if (actualLength == expectedLength) {
                String code = stockUpdate.get(0);
                float quantity = Float.parseFloat(stockUpdate.get(1));
                productServiceInterface.stockUpdate(code, quantity);
            } else {
                throw new TemplateMismatchException("Invalid attributes length (Code, Price). " +
                        "Expected: " + expectedLength + ". Provided: " + actualLength);
            }
        }
        catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Price cannot be a string.");
        }
    }
}
