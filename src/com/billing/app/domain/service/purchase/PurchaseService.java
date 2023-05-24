package com.billing.app.domain.service.purchase;

import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.exceptions.NotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseService {

    /**
     * Service layer interface for creating a purchase.
     * This interface defines a method to create a purchase based on the provided purchase object.
     * Implementations of this interface should handle the necessary business logic for creating purchases.
     * If any SQLException occurs during the process or if the code or ID is not found, it will be thrown.
     * @param purchase The Purchase object representing the purchase to be created.
     * @return The created Purchase object with additional details, such as the generated purchase ID.
     * @throws SQLException If an error occurs during the purchase creation process.
     * @throws NotFoundException If the product code or ID specified in the purchase is not found.
     */
    Purchase create(Purchase purchase) throws SQLException, NotFoundException;



    /**
     * Service layer interface for deleting a purchase by invoice.
     * This interface defines a method to delete a purchase based on the provided invoice number.
     * Implementations of this interface should handle the necessary business logic for deleting purchases.
     * If the invoice number is not found or if an SQLException occurs during the process, the respective exceptions will be thrown.
     * @param invoice The invoice number of the purchase to be deleted.
     * @return A boolean indicating whether the deletion was successful or not.
     * @throws NotFoundException If the purchase with the specified invoice number is not found.
     * @throws SQLException If an error occurs during the purchase deletion process.
     */
    boolean delete(int invoice) throws NotFoundException, SQLException;



    /**
     * Service layer interface for listing purchases with pagination and search functionality.
     * This interface defines a method to retrieve a list of purchases based on the provided range, page, attribute, and search text.
     * Implementations of this interface should handle the necessary business logic for listing purchases.
     * If any SQLException occurs during the process or if an invalid argument is provided, the respective exceptions will be thrown.
     * @param range The number of purchases to retrieve per page.
     * @param page The page number to retrieve.
     * @param attribute The attribute to sort the purchases (e.g., "date", "price").
     * @param searchText The search text to filter the purchases.
     * @return A list of Purchase objects representing the retrieved purchases.
     * @throws SQLException If an error occurs during the purchase listing process.
     * @throws InvalidArgumentException If an invalid argument is provided, such as an invalid range, page, or attribute.
     */
    List<Purchase> list(int range, int page, String attribute, String searchText) throws SQLException, InvalidArgumentException;


    /**
     * Service layer interface for counting purchases within a specified date range.
     * This interface defines a method to count the number of purchases that fall within the specified date range.
     * Implementations of this interface should handle the necessary business logic for counting purchases.
     * If any SQLException occurs during the process, it will be thrown.
     * @param from The starting date of the date range (inclusive) in string format.
     * @param to The ending date of the date range (inclusive) in string format.
     * @return The number of purchases within the specified date range.
     * @throws SQLException If an error occurs during the purchase counting process.
     */
    int count(String from, String to) throws SQLException;

}
