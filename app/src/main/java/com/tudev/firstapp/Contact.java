package com.tudev.firstapp;

import java.io.Serializable;

/**
 * Created by Саша on 26.07.2016.
 */

public class Contact implements Serializable{
    private String name;
    private String email;

    public Contact(String n, String e){
        name = n;
        email = e;
    }

    public Contact(String n){
        name = n;
        email = n + "@gmail.com";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
