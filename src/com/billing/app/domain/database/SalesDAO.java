package com.billing.app.domain.database;

import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.Sales;

import java.sql.SQLException;
import java.util.List;

public interface SalesDAO {

    /**
     * Interface for creating a sales entry.
     * This interface defines a method to create a sales entry based on the provided Sales object.
     * Implementations of this interface should handle the necessary operations to persist the sales data.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param sales The Sales object representing the sales entry to be created.
     * @return The Sales object representing the created sales entry with additional details.
     * @throws SQLException If an error occurs during the sales creation process.
     */
    Sales create(Sales sales) throws SQLException;


    /**
     * Interface for finding a sales entry by its code.
     * This interface defines a method to find a sales entry in the database based on the provided code.
     * Implementations of this interface should handle the necessary operations to query the database and retrieve the sales entry.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param code The code of the sales entry to find.
     * @return A boolean indicating whether a sales entry with the given code exists in the database (true) or not (false).
     * @throws SQLException If an error occurs during the process of finding the sales entry.
     */
    boolean find(String code) throws SQLException;


    /**
     * Interface for deleting a sales entry by its invoice number.
     * This interface defines a method to delete a sales entry from the database based on the provided invoice number.
     * Implementations of this interface should handle the necessary operations to remove the sales entry from the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param invoice The invoice number of the sales entry to delete.
     * @return A boolean indicating whether the deletion of the sales entry was successful (true) or not (false).
     * @throws SQLException If an error occurs during the process of deleting the sales entry.
     */
    boolean delete(int invoice) throws SQLException;


    /**
     * Interface for retrieving a list of sales entries based on specified criteria.
     * This interface defines a method to retrieve a list of sales entries from the database
     * based on the specified range, page, attribute, and search text.
     * Implementations of this interface should handle the necessary operations to query the database
     * and retrieve the sales entries that match the given criteria.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param range The number of sales entries to retrieve in a single request.
     * @param page The page number of results to retrieve.
     * @param attribute The attribute to filter the sales entries by (e.g., "name", "date").
     * @param searchText The search text to match against the specified attribute.
     * @return A list of Sales objects representing the retrieved sales entries.
     * @throws SQLException If an error occurs during the process of retrieving the sales entries.
     */
    List<Sales> list(int range, int page, String attribute, String searchText) throws SQLException;


    /**
     * Interface for retrieving a list of sales entries based on a search text.
     * This interface defines a method to retrieve a list of sales entries from the database
     * based on the provided search text.
     * Implementations of this interface should handle the necessary operations to query the database
     * and retrieve the sales entries that match the given search text.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param searchText The search text to match against the sales entries.
     * @return A list of Sales objects representing the retrieved sales entries.
     * @throws SQLException If an error occurs during the process of retrieving the sales entries.
     */
    List<Sales> list(String searchText) throws SQLException;


    /**
     * Interface for counting the number of sales entries within a specified date range.
     * This interface defines a method to count the number of sales entries in the database
     * within the specified date range.
     * Implementations of this interface should handle the necessary operations to query the database
     * and determine the count of sales entries that fall within the given date range.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param from The starting date of the date range.
     * @param to The ending date of the date range.
     * @return The count of sales entries within the specified date range.
     * @throws SQLException If an error occurs during the process of counting the sales entries.
     */
    int count(String from, String to) throws SQLException;


    /**
     * Interface for setting the grand total of a sales entry.
     * This interface defines a method to set the grand total of a sales entry in the database.
     * Implementations of this interface should handle the necessary operations to update the grand total
     * of the specified sales entry.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param sales The Sales object representing the sales entry for which to set the grand total.
     * @throws SQLException If an error occurs during the process of setting the grand total.
     */
    void setGrandTotal(Sales sales) throws SQLException;

}
