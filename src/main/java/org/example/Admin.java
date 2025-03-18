package org.example;

import packageEnum.UserType;

public class Admin extends User{

    public Admin(String name, String email, String password, String phone, String address) {
        super(name, email, password, phone, address);
    }

    @Override
    public UserType getTypeUser() {
        return UserType.ADMIN;
    }


}
