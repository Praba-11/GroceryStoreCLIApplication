package com.billing.app.domain.service.user;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserServiceInterface {
    User create(User user) throws SQLException, ObjectNullPointerException;
    User edit(User user) throws SQLException, IllegalAccessException, CodeNotFoundException, ObjectNullPointerException;
    boolean delete(String username) throws SQLException, ClassNotFoundException, CodeNotFoundException;
    List<User> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException, InvalidArgumentException;
    User find(String username, String password) throws SQLException;
}
