package com.billing.app.domain.database;

import com.billing.app.domain.entity.Purchase;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseDAO {

    /**
     * Interface for creating a purchase in the database.
     * This interface defines a method to create a purchase entry in the database based on the provided Purchase object.
     * Implementations of this interface should handle the necessary database operations to create the purchase.
     * If any SQLException occurs during the database operation, it will be thrown.
     * @param purchase The Purchase object representing the purchase to be created.
     * @return The Purchase object representing the created purchase with additional details, such as the generated purchase ID.
     * @throws SQLException If an error occurs during the database operation.
     */
    Purchase create(Purchase purchase) throws SQLException;


    /**
     * Interface for deleting a purchase from the database based on the invoice number.
     * This interface defines a method to delete a purchase entry from the database using the provided invoice number.
     * Implementations of this interface should handle the necessary database operations to delete the purchase.
     * If any SQLException occurs during the database operation, it will be thrown.
     * @param invoice The invoice number associated with the purchase to be deleted.
     * @return A boolean value indicating whether the deletion was successful (true) or not (false).
     * @throws SQLException If an error occurs during the database operation.
     */
    boolean delete(int invoice) throws SQLException;


    /**
     * Interface for listing purchases from the database based on specified criteria.
     * This interface defines a method to retrieve a list of purchases from the database, filtered by the provided criteria.
     * Implementations of this interface should handle the necessary database operations to fetch the purchases.
     * If any SQLException occurs during the database operation, it will be thrown.
     * @param range The number of purchases to retrieve in each page of results.
     * @param page The page number of results to retrieve.
     * @param attribute The attribute of the purchases to filter by (e.g., "date", "customer", "product").
     * @param searchText The search text used for filtering the purchases based on the specified attribute.
     * @return A list of Purchase objects representing the retrieved purchases.
     * @throws SQLException If an error occurs during the database operation.
     */
    List<Purchase> list(int range, int page, String attribute, String searchText) throws SQLException;


    /**
     * Interface for listing purchases from the database based on a search text.
     * This interface defines a method to retrieve a list of purchases from the database that match the provided search text.
     * Implementations of this interface should handle the necessary database operations to fetch the purchases.
     * If any SQLException occurs during the database operation, it will be thrown.
     * @param searchText The search text used to filter the purchases.
     * @return A list of Purchase objects representing the retrieved purchases.
     * @throws SQLException If an error occurs during the database operation.
     */
    List<Purchase> list(String searchText) throws SQLException;


    /**
     * Interface for counting the number of purchases within a specified date range.
     * This interface defines a method to count the number of purchases that fall within the specified date range.
     * Implementations of this interface should handle the necessary database operations to perform the count.
     * If any SQLException occurs during the database operation, it will be thrown.
     * @param from The starting date of the date range (inclusive).
     * @param to The ending date of the date range (inclusive).
     * @return The number of purchases within the specified date range.
     * @throws SQLException If an error occurs during the database operation.
     */
    int count(String from, String to) throws SQLException;

}
