package com.billing.app.domain.database;

import com.billing.app.domain.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    /**
     * Interface for creating a user entry.
     * This interface defines a method to create a user entry in the database.
     * Implementations of this interface should handle the necessary operations to insert
     * the provided user information into the database as a new entry.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param user The User object representing the user information to create.
     * @return true if the user entry is successfully created, false otherwise.
     * @throws SQLException If an error occurs during the process of creating the user entry.
     */
    User create(User user) throws SQLException;


    /**
     * Interface for editing a user entry.
     * This interface defines a method to edit an existing user entry in the database.
     * Implementations of this interface should handle the necessary operations to update
     * the user information of the provided user entry in the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param user The User object representing the updated user information.
     * @return The edited User object with the updated information.
     * @throws SQLException If an error occurs during the process of editing the user entry.
     */
    User edit(User user) throws SQLException;


    /**
     * Interface for deleting a user entry.
     * This interface defines a method to delete a user entry from the database based on the provided username.
     * Implementations of this interface should handle the necessary operations to remove
     * the user entry with the matching username from the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param username The username of the user entry to delete.
     * @return true if the user entry is successfully deleted, false otherwise.
     * @throws SQLException If an error occurs during the process of deleting the user entry.
     */
    boolean delete(String username) throws SQLException;


    /**
     * Interface for retrieving a list of user entries based on search criteria.
     * This interface defines a method to retrieve a list of user entries from the database,
     * based on the specified search criteria such as range, page, attribute, and search text.
     * Implementations of this interface should handle the necessary operations to fetch
     * and return the matching user entries from the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param range The maximum number of user entries to retrieve.
     * @param page The page number of user entries to retrieve.
     * @param attribute The attribute to search for matching user entries.
     * @param searchText The text to search for in the specified attribute.
     * @return A List of User objects representing the matching user entries.
     * @throws SQLException If an error occurs during the process of retrieving the user entries.
     */
    List<User> list(int range, int page, String attribute, String searchText) throws SQLException;


    /**
     * Interface for retrieving a list of user entries based on a search text.
     * This interface defines a method to retrieve a list of user entries from the database,
     * based on the specified search text. Implementations of this interface should handle
     * the necessary operations to fetch and return the matching user entries from the database.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param searchText The text to search for in the user entries.
     * @return A List of User objects representing the matching user entries.
     * @throws SQLException If an error occurs during the process of retrieving the user entries.
     */
    List<User> list(String searchText) throws SQLException;


    /**
     * Interface for finding a user entry by ID.
     * This interface defines a method to find and retrieve a user entry from the database based on the provided ID.
     * Implementations of this interface should handle the necessary operations to fetch and return the user entry
     * with the matching ID from the database. If any SQLException occurs during the process, the respective exception
     * will be thrown.
     * @param id The ID of the user entry to find.
     * @return The User object representing the found user entry, or null if no user entry is found with the specified ID.
     * @throws SQLException If an error occurs during the process of finding the user entry.
     */
    User find(int id) throws SQLException;


    /**
     * Interface for counting the total number of user entries.
     * This interface defines a method to count the total number of user entries in the database.
     * Implementations of this interface should handle the necessary operations to calculate and return
     * the count of user entries from the database. If any SQLException occurs during the process,
     * the respective exception will be thrown.
     * @return The total number of user entries in the database.
     * @throws SQLException If an error occurs during the process of counting the user entries.
     */
    int count() throws SQLException;


    /**
     * Interface for user authentication and login.
     * This interface defines a method to authenticate a user login based on the provided username and password.
     * Implementations of this interface should handle the necessary operations to verify the user credentials
     * and return the corresponding User object if the login is successful. If any SQLException occurs during
     * the process, the respective exception will be thrown.
     * @param username The username of the user attempting to login.
     * @param password The password of the user attempting to login.
     * @return The User object representing the authenticated user, or null if the login is unsuccessful.
     * @throws SQLException If an error occurs during the process of user authentication and login.
     */
    User login(String username, String password) throws SQLException;


    boolean adminExists() throws SQLException;
}
