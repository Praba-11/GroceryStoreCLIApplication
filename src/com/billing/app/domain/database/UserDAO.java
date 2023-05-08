package com.billing.app.domain.database;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.AnonymousException;
import com.billing.app.domain.exceptions.PrimaryKeyException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO {
    boolean create(User user) throws ClassNotFoundException, PrimaryKeyException, AnonymousException;
    boolean edit(User user) throws ClassNotFoundException, IllegalAccessException, SQLException;
    boolean delete(String key, String value) throws SQLException, ClassNotFoundException;
    ArrayList<User> list() throws SQLException, ClassNotFoundException;
    User getUser(String username) throws ClassNotFoundException, AnonymousException;
    boolean isUsernamePresent(String username) throws SQLException, ClassNotFoundException;
    boolean isIdPresent(String id) throws SQLException, ClassNotFoundException;
}
