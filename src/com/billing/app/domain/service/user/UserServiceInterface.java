package com.billing.app.domain.service.user;

import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.AnonymousException;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.PrimaryKeyException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserServiceInterface {
    User create(User user) throws ClassNotFoundException, PrimaryKeyException, AnonymousException;
    User edit(User modifiedUser) throws AnonymousException, ClassNotFoundException, SQLException, IllegalAccessException;
    boolean delete(String key, String value) throws SQLException, ClassNotFoundException, CodeNotFoundException;
    ArrayList<User> list() throws SQLException, ClassNotFoundException;
}
