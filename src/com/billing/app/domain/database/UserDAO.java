package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.AnonymousException;
import com.billing.app.domain.exceptions.PrimaryKeyException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserDAO {
    User create(User user) throws SQLException;
    User edit(User user) throws SQLException;
    boolean delete(String username) throws SQLException;
    List<User> list(int range, int page, String attribute, String searchText) throws SQLException;
    List<User> list(String searchText) throws SQLException;
    User find(int id) throws SQLException;
    int count() throws SQLException;
}
