package com.billing.app.domain.database;

import com.billing.app.domain.entity.Store;
import java.sql.SQLException;
import java.util.ArrayList;

public interface StoreDAO {

    /**
     * Interface for creating a store entry.
     * This interface defines a method to create a store entry in the database.
     * Implementations of this interface should handle the necessary operations to insert
     * the provided store information into the database as a new entry.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param store The Store object representing the store information to create.
     * @return true if the store entry is successfully created, false otherwise.
     * @throws SQLException If an error occurs during the process of creating the store entry.
     */
    boolean create(Store store) throws SQLException;


    /**
     *
     Interface for editing a store entry.
     This interface defines a method to edit an existing store entry in the database.
     Implementations of this interface should handle the necessary operations to update
     the provided store information in the database.
     If any SQLException occurs during the process, the respective exception will be thrown.
     @param store The Store object representing the updated store information.
     @return true if the store entry is successfully edited, false otherwise.
     @throws SQLException If an error occurs during the process of editing the store entry.
     */
    boolean edit(Store store) throws SQLException;


    /**
     * Interface for deleting a store entry.
     * This interface defines a method to delete an existing store entry from the database.
     * Implementations of this interface should handle the necessary operations to remove
     * the store entry from the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @return true if the store entry is successfully deleted, false otherwise.
     * @throws SQLException If an error occurs during the process of deleting the store entry.
     */
    boolean delete() throws SQLException;


    /**
     * Interface for retrieving store information.
     * This interface defines a method to retrieve the store information from the database.
     * Implementations of this interface should handle the necessary operations to fetch
     * and return the store information from the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @return The Store object representing the store information.
     * @throws SQLException If an error occurs during the process of retrieving the store information.
     */
    Store getStore() throws SQLException;


    /**
     * Interface for retrieving the count of store entries.
     * This interface defines a method to retrieve the count of store entries in the database.
     * Implementations of this interface should handle the necessary operations to fetch
     * and return the count of store entries from the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @return The count of store entries in the database.
     * @throws SQLException If an error occurs during the process of retrieving the count of store entries.
     */
    int getCount() throws SQLException;
}
