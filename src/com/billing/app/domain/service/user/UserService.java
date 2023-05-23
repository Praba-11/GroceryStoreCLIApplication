package com.billing.app.domain.service.user;

import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.*;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    /**
     * Service layer interface for creating a user.
     * This interface defines a method to create a user based on the provided user object.
     * Implementations of this interface should handle the necessary business logic for creating users.
     * If any SQLException occurs during the process or if a required object is null, the respective exceptions will be thrown.
     * @param user The User object representing the user to be created.
     * @return The created User object with additional details, such as the generated user ID.
     * @throws SQLException If an error occurs during the user creation process.
     * @throws ObjectNullPointerException If any required object, such as the user object itself, is null.
     */
    User create(User user) throws SQLException, ObjectNullPointerException;


    /**
     * Service layer interface for editing a user.
     * This interface defines a method to edit a user based on the provided user object.
     * Implementations of this interface should handle the necessary business logic for editing users.
     * If any SQLException occurs during the process, if a required object is null, or if the user's code or ID is not found, the respective exceptions will be thrown.
     * @param user The User object representing the user to be edited.
     * @return The updated User object with the modified details.
     * @throws SQLException If an error occurs during the user editing process.
     * @throws CodeOrIDNotFoundException If the user's code or ID is not found.
     * @throws ObjectNullPointerException If any required object, such as the user object itself, is null.
     */
    User edit(User user) throws SQLException, CodeOrIDNotFoundException, ObjectNullPointerException;

    /**
     * Service layer interface for deleting a user.
     * This interface defines a method to delete a user based on the provided username.
     * Implementations of this interface should handle the necessary business logic for deleting users.
     * If any SQLException occurs during the process or if the user's code or ID is not found, the respective exceptions will be thrown.
     * @param username The username of the user to be deleted.
     * @return A boolean indicating whether the deletion was successful or not.
     * @throws SQLException If an error occurs during the user deletion process.
     * @throws CodeOrIDNotFoundException If the user's code or ID is not found.
     */
    boolean delete(String username) throws SQLException, CodeOrIDNotFoundException;


    /**
     * Service layer interface for listing users.
     * This interface defines a method to retrieve a list of users based on the specified range, page, attribute, and search text.
     * Implementations of this interface should handle the necessary business logic for listing users.
     * If any SQLException occurs during the process or if an invalid argument is provided, the respective exceptions will be thrown.
     * @param range The number of users to retrieve in a single fetch.
     * @param page The page number of results to retrieve.
     * @param attribute The attribute name to filter the search on (e.g., "username", "email").
     * @param searchText The text to search for in the specified attribute.
     * @return A list of User objects matching the specified criteria.
     * @throws SQLException If an error occurs during the user listing process.
     * @throws InvalidArgumentException If an invalid argument is provided, such as a negative range or page number.
     */
    List<User> list(int range, int page, String attribute, String searchText) throws SQLException, InvalidArgumentException;


    /**
     * Service layer interface for finding a user.
     * This interface defines a method to find a user based on the provided username and password.
     * Implementations of this interface should handle the necessary business logic for finding users.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param username The username of the user to find.
     * @param password The password of the user to find.
     * @return The User object representing the found user, or null if not found.
     * @throws SQLException If an error occurs during the user finding process.
     */
    User find(String username, String password) throws SQLException;
}
