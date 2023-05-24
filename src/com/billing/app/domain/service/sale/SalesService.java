package com.billing.app.domain.service.sale;

import com.billing.app.domain.entity.Sales;
import com.billing.app.domain.exceptions.NotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.NegativeStockException;

import java.sql.SQLException;
import java.util.List;

public interface SalesService {

    /**
     * Service layer interface for creating a sales record.
     * This interface defines a method to create a sales record based on the provided sales object.
     * Implementations of this interface should handle the necessary business logic for creating sales records.
     * If any SQLException occurs during the process or if the code or ID is not found, the respective exceptions will be thrown.
     * @param sales The Sales object representing the sales record to be created.
     * @return The created Sales object with additional details, such as the generated sales ID.
     * @throws SQLException If an error occurs during the sales record creation process.
     * @throws NotFoundException If the product code or ID specified in the sales record is not found.
     */
    Sales create(Sales sales) throws SQLException, NotFoundException, NegativeStockException;


    /**
     * Service layer interface for deleting a sales record by invoice.
     * This interface defines a method to delete a sales record based on the provided invoice number.
     * Implementations of this interface should handle the necessary business logic for deleting sales records.
     * If the invoice number is not found or if an SQLException occurs during the process, the respective exceptions will be thrown.
     * @param invoice The invoice number of the sales record to be deleted.
     * @return A boolean indicating whether the deletion was successful or not.
     * @throws NotFoundException If the sales record with the specified invoice number is not found.
     * @throws SQLException If an error occurs during the sales record deletion process.
     */
    boolean delete(int invoice) throws NotFoundException, SQLException;


    /**
     * Service layer interface for listing sales records with pagination and search functionality.
     * This interface defines a method to retrieve a list of sales records based on the provided range, page, attribute, and search text.
     * Implementations of this interface should handle the necessary business logic for listing sales records.
     * If any SQLException occurs during the process or if an invalid argument is provided, the respective exceptions will be thrown.
     * @param range The number of sales records to retrieve per page.
     * @param page The page number to retrieve.
     * @param attribute The attribute to sort the sales records (e.g., "date", "amount").
     * @param searchText The search text to filter the sales records.
     * @return A list of Sales objects representing the retrieved sales records.
     * @throws SQLException If an error occurs during the sales record listing process.
     * @throws InvalidArgumentException If an invalid argument is provided, such as an invalid range, page, or attribute.
     */
    List<Sales> list(int range, int page, String attribute, String searchText) throws SQLException, InvalidArgumentException;


    /**
     * Service layer interface for counting sales records within a specified date range.
     * This interface defines a method to count the number of sales records that fall within the specified date range.
     * Implementations of this interface should handle the necessary business logic for counting sales records.
     * If any SQLException occurs during the process, it will be thrown.
     * @param from The starting date of the date range (inclusive) in string format.
     * @param to The ending date of the date range (inclusive) in string format.
     * @return The number of sales records within the specified date range.
     * @throws SQLException If an error occurs during the sales record counting process.
     */
    int count(String from, String to) throws SQLException;

}
