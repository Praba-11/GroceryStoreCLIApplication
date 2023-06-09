package com.billing.app.domain.service.user;


import com.billing.app.domain.database.UserDAO;
import com.billing.app.domain.database.UserDAOImplementation;
import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.*;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImplementation implements UserService {
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

    public User edit(User user) throws NotFoundException, ObjectNullPointerException, SQLException {

        try {
            userValidator.validate(user);
            if (userDAO.find(user.getId()) != null) {
                return userDAO.edit(user);
            }
            throw new NotFoundException("Provided user id not created or not exists.");
        } catch (ObjectNullPointerException exception) {
            throw new ObjectNullPointerException("Error while editing product: " + exception.getMessage());
        }
    }

    public boolean delete(String username) throws SQLException, NotFoundException {
        boolean isDeleted;
        isDeleted = userDAO.delete(username);
        if (!isDeleted) {
            throw new NotFoundException("(Username: " + username + ") not not created or existing.");
        }
        return true;
    }

    public List<User> list(int range, int page, String attribute, String searchText) throws SQLException, InvalidArgumentException {
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
                int calculatedLimit = (range * page) - range;
                int calculatedPage = calculatedLimit / range;
                if (calculatedLimit > userDAO.count()) {
                    throw new InvalidArgumentException("Invalid argument provided. " +
                            "(Number of pages for listing : " + calculatedPage + ")");
                } else {
                    attribute = "isdeleted";
                    searchText = "";
                    page = (page - 1) * range;
                }
            } else if (attribute != null && searchText != null && range == 0 && page == 0) {
                range = userDAO.count();
            } else if (attribute != null && searchText != null && range > 0 && page > 0) {
                int calculatedLimit = (range * page) - range;
                int calculatedPage = calculatedLimit / range;
                if (calculatedLimit > userDAO.count()) {
                    throw new InvalidArgumentException("Invalid argument provided. " +
                            "(Number of pages for listing : " + calculatedPage + ")");
                } else {
                    attribute = "isdeleted";
                    searchText = "";
                    page = (page - 1) * range;
                }
            } else {
                throw new InvalidArgumentException("Invalid argument provided. Please provide valid arguments as per template.");
            }
            System.out.println(range+page+attribute+searchText);
            list = userDAO.list(range, page, attribute, searchText);
        }
        return list;
    }

    public User find(String username, String password) throws SQLException {
        User loginUser;
        loginUser = userDAO.login(username, password);
        return loginUser;
    }

    public boolean adminExists() throws SQLException {
        boolean flag;
        flag = userDAO.adminExists();
        return flag;
    }
}
