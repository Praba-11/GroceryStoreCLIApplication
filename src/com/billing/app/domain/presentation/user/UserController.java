package com.billing.app.domain.presentation.user;

import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.service.user.UserServiceImplementation;
import com.billing.app.domain.service.user.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserController {
    private List<String> valueList;
    private User user;
    private UserService userService = new UserServiceImplementation();
    private UserValidator userValidator = new UserValidator();
    public User create(List<String> values) throws SQLException, ObjectNullPointerException, TypeMismatchException, InvalidArgumentException, TemplateMismatchException {

        int expectedLength = 6;
        int actualLength = values.size();
        if (actualLength == expectedLength) {
            userValidator.validateValues(values);
            user = setValues(values, false);
            return userService.create(user);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }
    }

    public User edit(Map<String, String> values) throws TypeMismatchException, InvalidArgumentException,
            TemplateMismatchException, ObjectNullPointerException, SQLException, NotFoundException, IllegalAccessException {
        int expectedLength = 7;
        int actualLength = values.size();
        if (actualLength == expectedLength) {
            userValidator.validateMap(values);
            valueList = new ArrayList<>(values.values());
            user = setValues(valueList, true);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }
        return userService.edit(user);
    }

    public boolean delete(String username) throws SQLException, ClassNotFoundException, NotFoundException, InvalidArgumentException {
        boolean flag = false;
        flag = userService.delete(username);
        return flag;
    }

    public List<User> list(List<String> values) throws InvalidArgumentException, TemplateMismatchException, SQLException, ClassNotFoundException {

        int range, page;
        String attribute, searchText;
        Map<String, Object> parameters = userValidator.validateUserList(values);

        range = Integer.parseInt(parameters.get("range").toString());
        page = Integer.parseInt(parameters.get("page").toString());
        attribute = (String) parameters.get("attribute");
        searchText = (String) parameters.get("searchtext");

        return userService.list(range, page, attribute, searchText);

    }

    private User setValues(List<String> values, boolean setId) {
        User user = new User();
        int startIndex = setId ? 0 : -1;

        if (setId) {
            user.setId(Integer.parseInt(values.get(startIndex)));
        }

        user.setUserType(User.UserType.valueOf(values.get(startIndex + 1).toUpperCase()));
        user.setUsername(values.get(startIndex + 2));
        user.setPassword(values.get(startIndex + 3));
        user.setFirstName(values.get(startIndex + 4));
        user.setLastName(values.get(startIndex + 5));
        user.setPhoneNumber(Long.parseLong(values.get(startIndex + 6)));
        user.setIsAvailable(true);
        return user;
    }

    public User find(String username, String password) throws InvalidArgumentException, SQLException {
        User loginUser;
        userValidator.validateLogin(username, password);
        loginUser = userService.find(username, password);
        return loginUser;
    }

    public boolean adminExists() throws InvalidArgumentException {
        try {
            boolean flag = false;
            flag = userService.adminExists();
            return flag;
        } catch (SQLException exception) {
            throw new InvalidArgumentException("Anonymous error experienced.");
        }
    }
}
