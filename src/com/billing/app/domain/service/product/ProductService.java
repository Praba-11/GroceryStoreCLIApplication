package com.billing.app.domain.service.product;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.CodeOrIDNotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.ObjectNullPointerException;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    /**
     * Ensures the product is valid by validating the business logic and forwarding it to the data access layer.
     *
     * @param product The product object to be created
     * @return The created product with updated information, including the generated identifier
     * @throws SQLException If a database error occurs while accessing the data access layer
     * @throws ObjectNullPointerException If the product object or its field is null
     */
    Product create(Product product) throws SQLException, ObjectNullPointerException;

    /**
     * Edits an existing product by validating the business logic, checking for the existence of the product code, and forwarding the updated product to the data access layer.
     * @param product The product object to be edited
     * @return The edited product with updated information
     * @throws SQLException If a database error occurs while accessing the data access layer
     * @throws ObjectNullPointerException If the product object or its field is null
     * @throws CodeOrIDNotFoundException If the product id is not found in the system
     */
    Product edit(Product product) throws SQLException, ObjectNullPointerException, CodeOrIDNotFoundException;

    /**
     * Deletes a product with the specified ID by forwarding the request to the data access layer.
     * @param id The identifier of the product to be deleted
     * @return {@code true} if the product is successfully deleted, {@code false} otherwise
     * @throws SQLException If a database error occurs while accessing the data access layer
     * @throws CodeOrIDNotFoundException If the product with the specified ID is not found in the system
     */
    boolean delete(int id) throws SQLException, CodeOrIDNotFoundException;

    /**
     * Retrieves a list of products based on the specified range, page, attribute, and search text.
     * @param range The maximum number of products to retrieve in a single query
     * @param page The page number of the results to retrieve
     * @param attribute The attribute to search for (e.g., "name", "type")
     * @param searchText The text to search for within the specified attribute
     * @return A list of products matching the search criteria
     * @throws SQLException If a database error occurs while accessing the data access layer
     * @throws InvalidArgumentException If the provided arguments are invalid or out of range
     */
    List<Product> list(int range, int page, String attribute, String searchText) throws SQLException, InvalidArgumentException;

    /**
     * Updates the stock quantity of a product with the specified code.
     * @param code The code of the product to update
     * @param stock The new stock quantity value
     * @return The edited product with updated stock
     * @throws SQLException If a database error occurs while accessing the data access layer
     */
    Product stockUpdate(String code, float stock) throws SQLException;

    /**
     * Updates the price of a product with the specified code.
     * @param code The code of the product to update
     * @param price The new price value
     * @return The edited product with updated price
     * @throws SQLException If a database error occurs while accessing the data access layer
     */
    Product priceUpdate(String code, float price) throws SQLException;
}
