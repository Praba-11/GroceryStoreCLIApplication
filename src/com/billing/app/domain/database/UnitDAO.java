package com.billing.app.domain.database;

import com.billing.app.domain.entity.Unit;

import java.sql.SQLException;
import java.util.List;

public interface UnitDAO {

    /**
     * Interface for creating a unit entry.
     * This interface defines a method to create a unit entry in the database.
     * Implementations of this interface should handle the necessary operations to insert
     * the provided unit information into the database as a new entry.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param unit The Unit object representing the unit information to create.
     * @return true if the unit entry is successfully created, false otherwise.
     * @throws SQLException If an error occurs during the process of creating the unit entry.
     */
    Unit create(Unit unit) throws SQLException;


    /**
     * Interface for editing a unit entry.
     * This interface defines a method to edit an existing unit entry in the database.
     * Implementations of this interface should handle the necessary operations to update
     * the provided unit information in the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param unit The Unit object representing the updated unit information.
     * @return true if the unit entry is successfully edited, false otherwise.
     * @throws SQLException If an error occurs during the process of editing the unit entry.
     */
    Unit edit(Unit unit) throws SQLException;


    /**
     * Interface for deleting a unit entry.
     * This interface defines a method to delete an existing unit entry from the database.
     * Implementations of this interface should handle the necessary operations to remove
     * the unit entry from the database based on the provided ID.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param id The ID of the unit entry to delete.
     * @return true if the unit entry is successfully deleted, false otherwise.
     * @throws SQLException If an error occurs during the process of deleting the unit entry.
     */
    boolean delete(int id) throws SQLException;


    /**
     * Interface for retrieving a list of unit entries.
     * This interface defines a method to retrieve a list of unit entries from the database.
     * Implementations of this interface should handle the necessary operations to fetch
     * and return the list of unit entries from the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @return A List of Unit objects representing the unit entries.
     * @throws SQLException If an error occurs during the process of retrieving the unit entries.
     */
    List<Unit> list() throws SQLException;

    /**
     * Interface for finding a unit entry by ID.
     * This interface defines a method to find and retrieve a unit entry from the database based on the provided ID.
     * Implementations of this interface should handle the necessary operations to fetch
     * and return the unit entry with the matching ID from the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param id The ID of the unit entry to find.
     * @return The Unit object representing the found unit entry, or null if no matching entry is found.
     * @throws SQLException If an error occurs during the process of finding the unit entry.
     */
    Unit getById(int id) throws SQLException;


    /**
     * Interface for finding a unit entry by code.
     * This interface defines a method to find and retrieve a unit entry from the database based on the provided code.
     * Implementations of this interface should handle the necessary operations to fetch
     * and return the unit entry with the matching code from the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param code The code of the unit entry to find.
     * @return The Unit object representing the found unit entry, or null if no matching entry is found.
     * @throws SQLException If an error occurs during the process of finding the unit entry.
     */
    Unit getByCode(String code) throws SQLException;


    /**
     * Interface for retrieving the count of unit entries.
     * This interface defines a method to retrieve the count of unit entries in the database.
     * Implementations of this interface should handle the necessary operations to fetch
     * and return the count of unit entries from the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @return The count of unit entries in the database.
     * @throws SQLException If an error occurs during the process of retrieving the count of unit entries.
     */
    int count() throws SQLException;
}
