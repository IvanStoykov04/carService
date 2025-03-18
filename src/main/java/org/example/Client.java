package org.example;

import packageEnum.UserType;

public class Client extends User{
    public Client(String name, String email, String password, String phone, String address) {
        super(name, email, password, phone, address);
    }

    @Override
    public UserType getTypeUser() {
        return UserType.CLIENT;
    }
}
