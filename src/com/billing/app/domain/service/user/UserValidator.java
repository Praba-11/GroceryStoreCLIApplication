package com.billing.app.domain.service.user;

import com.billing.app.domain.database.UserDAO;
import com.billing.app.domain.database.UserDAOImplementation;
import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.CodeNotFoundException;

import java.sql.SQLException;
public class UserValidator {
    UserDAO userDAO;
    public User editAttributes(User userToBeEdited, User modifiedUser) throws NullPointerException {
        userToBeEdited.setUsername(modifiedUser.getUsername());
        userToBeEdited.setType(modifiedUser.getType());
        userToBeEdited.setPassword(modifiedUser.getPassword());
        userToBeEdited.setFirstName(modifiedUser.getFirstName());
        userToBeEdited.setLastName(modifiedUser.getLastName());
        userToBeEdited.setPhoneNumber(modifiedUser.getPhoneNumber());
        return userToBeEdited;
    }

    public boolean isDeletable(String key, String value) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        userDAO = new UserDAOImplementation();
        if (key.equals("username")) {
            return userDAO.isUsernamePresent(value);
        } else if (key.equals("id")) {
            return userDAO.isIdPresent(value);
        } else {
            throw new CodeNotFoundException("Provided attribute not found in relational table. '" + key + " doesn't contain '" + value + "'");
        }
    }
}
