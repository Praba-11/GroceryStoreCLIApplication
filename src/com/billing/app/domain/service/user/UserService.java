package com.billing.app.domain.service.user;


import com.billing.app.domain.database.UserDAO;
import com.billing.app.domain.database.UserDAOImplementation;
import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.AnonymousException;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.PrimaryKeyException;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserService implements UserServiceInterface {
    private UserDAO userDAO;
    UserValidator userValidator;
    public User create(User user) throws ClassNotFoundException, PrimaryKeyException, AnonymousException {
        userDAO = new UserDAOImplementation();
        if (userDAO.create(user)) {
            return userDAO.getUser(user.getUsername());
        }
        return null;
    }

    public User edit(User modifiedUser) throws AnonymousException, ClassNotFoundException, SQLException, IllegalAccessException {
        userDAO = new UserDAOImplementation();
        User userToBeEdited = userDAO.getUser(modifiedUser.getUsername());
        userValidator = new UserValidator();
        if (userToBeEdited != null) {
            userValidator.editAttributes(userToBeEdited, modifiedUser);
            if (userDAO.edit(userToBeEdited)) {
                return userDAO.getUser(modifiedUser.getUsername());
            }
        } else {
            return null;
        }
        return null;
    }

    public boolean delete(String key, String value) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        boolean flag = false;
        userDAO = new UserDAOImplementation();
        userValidator = new UserValidator();
        if (userValidator.isDeletable(key, value)) {
            flag = userDAO.delete(key, value);
        }
        return flag;
    }

    public ArrayList<User> list() throws SQLException, ClassNotFoundException {
        userDAO = new UserDAOImplementation();
        ArrayList<User> userArrayList = userDAO.list();
        return userArrayList;
    }
}
