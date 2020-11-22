package com.example.jraw_test_2;

import java.util.ArrayList;

/*

    Purpose: store data for users which is then used to write to database

 */
public class User {

    public String email;
    public ArrayList<String> imageList;
//    public String username;
//    public String phone_number;

    public User() {

    }

    public User(String email) {

        this.email = email;
//        this.username = username;
//        this.username = phone_number;
    }
}
