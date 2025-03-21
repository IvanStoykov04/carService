package org.example;

import packageEnum.UserType;

public class Admin extends User{

    private int id;
    public Admin(int id,String name, String email, String password, String phone, String address) {
        super(name, email, password, phone, address);
        this.id=id;
    }

    public Admin(String name, String email, String password, String phone, String address) {
        super(name, email, password, phone, address);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public UserType getTypeUser() {
        return UserType.ADMIN;
    }


}
