package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserRouter {
    public void execute(ArrayList<String> arrayList) {
        String action = arrayList.get(1);
        Scanner scanner = new Scanner(System.in);
        UserParser userParser = new UserParser();

        switch (action) {

            case "create":
                try {
                    User userCreated = null;
                    userCreated = userParser.create(arrayList);
                    System.out.println(userCreated);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }
}
