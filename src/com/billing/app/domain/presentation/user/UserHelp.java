package com.billing.app.domain.presentation.user;

public class UserHelp {
    void createUser() {
        System.out.println("Create user using following template.");
        System.out.println("usertype, username,  password, first name, last name, phone number\n" +
                "type - text, purchase/sales, mandatory\n" +
                "username - text, min 3 - 30 char, mandatory\n" +
                "password - text, alphanumeric, special char, min 8 char, mandatory\n" +
                "firstname - text, mandatory with 3 to 30 chars\n" +
                "lastname  - text, optional\n" +
                "phone - number, mandatory, ten digits, digit start with 9/8/7/6\n");
    }
    void editUser() {
        System.out.println("Edit user using following template. Copy the user data from the list, edit the attribute values. ");
        System.out.println("id: <id - 6>, usertype: <usertype-edited>, username: <username>,  password: <password>, " +
                "first name: <first name>, last name: <last name>, phone number: <phone number>");
        System.out.println("You can not give empty or null values to the mandatory attributes.\n" +
                "id - number, mandatory\n" +
                "usertype - text, purchase/sales, mandatory\n" +
                "username - text, min 3 - 30 char, mandatory\n" +
                "password - text, alphanumeric, special char, min 8 char, mandatory\n" +
                "firstname - text, mandatory with 3 to 30 chars\n" +
                "lastname  - text, optional\n" +
                "phone - number, mandatory, ten digits, digit start with 9/8/7/6\n");
        System.out.println("user edit id: <id - 6>, usertype: <usertype-edited>, username: <username>,  " +
                "password: <password>, first name: <first name>, last name: <last name>, phone number: <phone number>");
    }
    void deleteUser() {
        System.out.println("delete user using the following template\n" +
                "\n" +
                "username - text, min 3 - 30 char, mandatory, existing\n");
    }
    void listUser() {
        System.out.println("List user with the following options.");
        System.out.println("user list - will list all the users default to maximum up to 20 users");
        System.out.println("user list -p 10 - pageable list shows 10 users as default");
        System.out.println("user list -p 10 3 - pagable list shows 10 users in 3rd page, ie., user from 21 to 30");
        System.out.println("user list -s searchtext - search the user with the given search text in all the " +
                "searchable attributes");
        System.out.println("user list -s <attr>: searchtext - search the user with the given search text in all the " +
                "given attribute");
        System.out.println("user list -s <attr>: searchtext -p 10 6 - pagable list shows 10 users in 6th page with the " +
                "given search text in the given attribute");
    }
}
