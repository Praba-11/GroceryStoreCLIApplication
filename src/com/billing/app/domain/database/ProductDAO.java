package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {

    /**
     * Interface for creating a product in database table.
     * This interface defines a method to create a product entry in the database table based on the data entities from the provided product object.
     * Implementations of this interface should handle the necessary database operations to store the product information.
     * If any SQLException occurs during the database operation, it will be thrown.
     * @param product The product object containing the data entities to be stored in the database table.
     * @return The created product object with updated information, including any generated unique identifiers.
     * @throws SQLException If an error occurs during the database operation.
     */
    Product create(Product product) throws SQLException;

    /**
     * Interface for editing a product in the database table.
     * This interface defines a method to edit a product in the database table based on the provided product object.
     * Implementations of this interface should handle the necessary database operations to update the product information.
     * If any SQLException occurs during the database operation or if the product code is not found, it will be thrown.
     * @param product The product object containing the updated information for the product to be edited.
     * @return The edited product object with the updated information.
     * @throws SQLException If an error occurs during the database operation.
     */
    Product edit(Product product) throws SQLException;

    /**
     * Interface for deleting a product from the database table.
     * This interface defines a method to delete a product from the database table based on the provided product ID.
     * Implementations of this interface should handle the necessary database operations to set the product as deleted.
     * If any SQLException occurs during the database operation, it will be thrown.
     * @param id The ID of the product to be deleted from the database table.
     * @return  A boolean value indicating whether the deletion was successful (true) or not (false).
     * @throws SQLException If an error occurs during the database operation.
     */
    boolean delete(int id) throws SQLException;

    /**
     * Interface for listing products from the database table.
     * This interface defines a method to retrieve a list of products from the database table based on the specified range, page, attribute, and search text.
     * Implementations of this interface should handle the necessary database operations to fetch the products.
     * If any SQLException occurs during the database operation, it will be thrown.
     * @param range The number of products to be retrieved per page.
     * @param page The page number of the products to be retrieved.
     * @param attribute The attribute of the products to filter the search by (e.g., name, type).
     * @param searchText The text to search for in the specified attribute.
     * @return A list of Product objects matching the search criteria.
     * @throws SQLException If an error occurs during the database operation.
     */
    List<Product> list(int range, int page, String attribute, String searchText) throws SQLException;

    /**
     * Interface for listing products from the database table.
     * This interface defines a method to retrieve a list of products from the database table based on the provided search text.
     * Implementations of this interface should handle the necessary database operations to fetch the products.
     * If any SQLException occurs during the database operation, it will be thrown.
     * @param searchText The text to search for in the product's attributes.
     * @return A list of Product objects matching the search criteria.
     * @throws SQLException If an error occurs during the database operation.
     */
    List<Product> list(String searchText) throws SQLException;

    /**
     * Interface for finding a product in the database table.
     * This interface defines a method to find a product in the database table based on the provided product ID.
     * Implementations of this interface should handle the necessary database operations to retrieve the product.
     * If any SQLException occurs during the database operation, it will be thrown.
     * @param id The ID of the product to be found in the database table.
     * @return The Product object representing the found product.
     * @throws SQLException If an error occurs during the database operation.
     */
    Product findById(int id) throws SQLException;


    /**
     * Interface for finding a product by its code.
     * This interface defines a method to find and retrieve a product from the database based on the provided product code.
     * Implementations of this interface should handle the necessary operations to fetch and return the product
     * with the matching code from the database. If any SQLException occurs during the process, the respective exception
     * will be thrown.
     * @param code The code of the product to find.
     * @return The Product object representing the found product, or null if no product is found with the specified code.
     * @throws SQLException If an error occurs during the process of finding the product.
     */
    Product findByCode(String code) throws SQLException;


    /**
     * Interface for counting the number of products in the database table.
     * This interface defines a method to count the total number of products in the database table.
     * Implementations of this interface should handle the necessary database operations to retrieve the count.
     * If any SQLException occurs during the database operation, it will be thrown.
     * @return The total number of products in the database table.
     * @throws SQLException If an error occurs during the database operation.
     */
    int count() throws SQLException;

}