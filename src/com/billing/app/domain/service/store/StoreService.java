package com.billing.app.domain.service.store;

import com.billing.app.domain.entity.Store;

import java.sql.SQLException;

public interface StoreService {

    /**
     * Service layer interface for creating a store.
     * This interface defines a method to create a store based on the provided store object.
     * Implementations of this interface should handle the necessary business logic for creating stores.
     * If any SQLException occurs during the process, it will be thrown.
     * @param store The Store object representing the store to be created.
     * @return The created Store object with additional details, such as the generated store ID.
     * @throws SQLException If an error occurs during the store creation process.
     */
    Store create(Store store) throws SQLException;


    /**
     * Service layer interface for editing a store.
     * This interface defines a method to edit a store based on the provided store object.
     * Implementations of this interface should handle the necessary business logic for editing stores.
     * If any SQLException occurs during the process, it will be thrown.
     * @param store The Store object representing the store to be edited.
     * @return The updated Store object with the modified details.
     * @throws SQLException If an error occurs during the store editing process.
     */
    Store edit(Store store) throws SQLException;


    /**
     * Service layer interface for deleting a store.
     * This interface defines a method to delete a store.
     * Implementations of this interface should handle the necessary business logic for deleting stores.
     * If any SQLException occurs during the process, it will be thrown.
     * @return A boolean indicating whether the deletion was successful or not.
     * @throws SQLException If an error occurs during the store deletion process.
     */
    boolean delete() throws SQLException;


    /**
     * Service layer interface for viewing a store.
     * This interface defines a method to view a store.
     * Implementations of this interface should handle the necessary business logic for retrieving store details.
     * If any SQLException occurs during the process, it will be thrown.
     * @return The Store object representing the viewed store.
     * @throws SQLException If an error occurs during the store retrieval process.
     */
    Store view() throws SQLException;

}
