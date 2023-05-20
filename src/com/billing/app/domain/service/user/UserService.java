package com.billing.app.domain.service.user;


import com.billing.app.domain.database.UserDAO;
import com.billing.app.domain.database.UserDAOImplementation;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService implements UserServiceInterface {
    private UserDAO userDAO = new UserDAOImplementation();
    UserValidator userValidator = new UserValidator();
    public User create(User user) throws SQLException, ObjectNullPointerException {
        try {
            userValidator.validate(user);
            return userDAO.create(user);
        } catch (ObjectNullPointerException exception) {
            throw new ObjectNullPointerException("Error while creating user: " + exception.getMessage());
        }
    }

    public User edit(User user) throws CodeNotFoundException, ObjectNullPointerException, SQLException {

        try {
            userValidator.validate(user);
            if (userDAO.find(user.getId()) != null) {
                return userDAO.edit(user);
            }
            throw new CodeNotFoundException("Provided product id not present in user relation table.");
        } catch (ObjectNullPointerException exception) {
            throw new ObjectNullPointerException("Error while editing product: " + exception.getMessage());
        }
    }

    public boolean delete(String username) throws SQLException, CodeNotFoundException {
        boolean isDeleted;
        isDeleted = userDAO.delete(username);
        if (!isDeleted) {
            throw new CodeNotFoundException("(Username: " + username + ") not present in user relational table.");
        }
        return true;
    }

    public List<User> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException, InvalidArgumentException {
        List<User> list;
        if (attribute == null && searchText != null && range == 0 && page == 0) {
            list = userDAO.list(searchText);
        } else {
            if (attribute == null && searchText == null && range == 0 && page == 0) {
                attribute = "isavailable";
                searchText = "";
                range = userDAO.count();
            } else if (attribute == null && searchText == null && range > 0 && page == 0) {
                attribute = "isavailable";
                searchText = "";
            } else if (attribute == null && searchText == null && range > 0 && page > 0) {
                attribute = "isavailable";
                searchText = "";
                page = (page - 1) * range;
            } else if (attribute != null && searchText != null && range == 0 && page == 0) {
                range = userDAO.count();
            } else if (attribute != null && searchText != null && range > 0 && page > 0) {
                page = (page - 1) * range;
            } else {
                throw new InvalidArgumentException("Invalid argument provided. Please provide valid arguments as per template.");
            }
            System.out.println(range);
            System.out.println(page);
            System.out.println(attribute);
            System.out.println(searchText);
            list = userDAO.list(range, page, attribute, searchText);
        }
        return list;
    }
}
