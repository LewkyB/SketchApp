package com.example.jraw_test_2;

/*

    Purpose: store data for users which is then used to write to database

    update 11/22/20: is it even necessary to have a class for users when
    TODO: remove need to populate database with this object
 */
public class User {

    public String email;
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
