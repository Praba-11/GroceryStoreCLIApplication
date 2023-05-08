package com.billing.app.domain.presentation.user;

import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.AnonymousException;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.PrimaryKeyException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.Validator;
import com.billing.app.domain.service.user.UserService;
import com.billing.app.domain.service.user.UserServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {
    private User user;
    private UserServiceInterface userServiceInterface;
    private Validator validator;
    public User create(ArrayList<String> values) throws ClassNotFoundException, PrimaryKeyException, AnonymousException {

        user = new User();
        user.setType(values.get(0));
        user.setUsername(values.get(1));
        user.setPassword(values.get(2));
        user.setFirstName(values.get(3));
        user.setLastName(values.get(4));
        user.setPhoneNumber(Long.parseLong(values.get(5)));
        user.setIsAvailable(true);
        userServiceInterface = new UserService();

        return userServiceInterface.create(user);
    }

    public User edit(ArrayList<String> arrayList) throws TemplateMismatchException, SQLException, AnonymousException, ClassNotFoundException, IllegalAccessException {
        validator = new Validator();
        user = new User();
        ArrayList<String> keyValuePair = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        for (int index = 0; index < keyValuePair.size(); index += 2) {
            String key = keyValuePair.get(index);
            String value = keyValuePair.get(index + 1);

            validator.userEditValidate(user, key, value);
        }
        userServiceInterface = new UserService();
        return userServiceInterface.edit(user);
    }

    public boolean delete(ArrayList<String> arrayList) throws TemplateMismatchException, SQLException, ClassNotFoundException, CodeNotFoundException {

        boolean flag = false;
        String key, value;
        if (arrayList.size() == 4) {
            key = arrayList.get(2);
            value = arrayList.get(3);
            validator = new Validator();
            if (validator.userDeleteValidate(key)) {
                userServiceInterface = new UserService();
                flag = userServiceInterface.delete(key, value);
            }
            return flag;
        } else {
            throw new TemplateMismatchException("Attribute not provided as Key-Value pair. Please check the template and try again.");
        }
    }

    public ArrayList<User> list() throws SQLException, ClassNotFoundException {
        userServiceInterface = new UserService();
        ArrayList<User> userArrayList = userServiceInterface.list();
        return userArrayList;
    }
}
