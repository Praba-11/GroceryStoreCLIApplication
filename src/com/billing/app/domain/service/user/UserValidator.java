package com.billing.app.domain.service.user;

import com.billing.app.domain.database.UserDAO;
import com.billing.app.domain.database.UserDAOImplementation;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.ObjectNullPointerException;

import java.sql.SQLException;
public class UserValidator {
    UserDAO userDAO;


    public boolean validate(User user) throws ObjectNullPointerException {
        if(user == null) {
            throw new ObjectNullPointerException("User cannot be null or empty.");
        }
        if (user.getUserType().getValue().isEmpty() || user.getUserType().getValue() == null) {
            throw new ObjectNullPointerException("User type cannot be null (or) empty.");
        }
        if (user.getUsername().isEmpty() || user.getUsername() == null) {
            throw new ObjectNullPointerException("User name cannot be null (or) empty.");
        }
        if (user.getPassword().isEmpty() || user.getPassword() == null) {
            throw new ObjectNullPointerException("User password code cannot be null (or) empty.");
        }
        if (user.getFirstName().isEmpty() || user.getFirstName() == null) {
            throw new ObjectNullPointerException("User first name cannot be null (or) empty.");
        }
        if (user.getLastName().isEmpty() || user.getLastName() == null) {
            throw new ObjectNullPointerException("User last name cannot be null (or) empty.");
        }
        if (user.getPhoneNumber() == 0L) {
            throw new ObjectNullPointerException("User phone number cannot be null (or) empty.");
        }
        return true;
    }
}
