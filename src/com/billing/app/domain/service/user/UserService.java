package com.billing.app.domain.service.user;


import com.billing.app.domain.database.UnitJdbcDAO;
import com.billing.app.domain.database.UserDAO;
import com.billing.app.domain.database.UserJdbcDAO;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.entity.User;

import java.sql.SQLException;

public class UserService implements UserServiceInterface {
    private UserDAO userDAO;
    UserValidator userValidator;
    public User create(User user) throws SQLException, ClassNotFoundException {
        userDAO = new UserJdbcDAO();
        if (userDAO.create(user)) {
            return userDAO.getUserByUsername(user.getName());
        }
        return null;
    }
}
