package com.billing.app.domain.entity;

public class User {
    public enum UserType {
        ADMIN("Administrator"),
        PURCHASE("Purchase User"),
        SALES("Sales User");

        private String value;

        UserType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static UserType fromValue(String value) {
            for (UserType userType : UserType.values()) {
                if (userType.getValue().equals(value)) {
                    return userType;
                }
            }
            throw new IllegalArgumentException("Invalid value for UserType: " + value);
        }
    }

    private UserType userType;

    @Override
    public String toString() {
        return "User{" +
                "userType=" + userType +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", isAvailable=" + isAvailable +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    private int id;
    private String username;
    private boolean isAvailable;

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String firstName;
    private String lastName;
    private long phoneNumber;

}