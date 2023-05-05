package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.User;
import com.billing.app.domain.service.user.UserService;
import com.billing.app.domain.service.user.UserServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserParser {
    private User user;
    private UserServiceInterface userServiceInterface;
    public User create(ArrayList<String> arrayList) throws SQLException, ClassNotFoundException {
        ArrayList<String> values = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        user = new User();
        user.setId(values.get(0));
        user.setType(values.get(1));
        user.setName(values.get(2));
        user.setPassword(values.get(3));
        user.setFirstName(values.get(4));
        user.setLastName(values.get(5));
        user.setPhoneNumber(Long.parseLong(values.get(6)));
        userServiceInterface = new UserService();
        return userServiceInterface.create(user);
    }
}
