package com.tudev.firstapp;

/**
 * Created by Саша on 27.07.2016.
 */
public class Contacts {
    private static Contacts instance = new Contacts();

    public static Contacts getInstance() {
        return instance;
    }

    private Contact[] contacts;

    private Contacts() {
        contacts = new Contact[]{
                new Contact("Alex"),
                new Contact("Bill"),
                new Contact("Tim"),
                new Contact("Bob"),
                new Contact("Alice"),
                new Contact("Kate"),
                new Contact("Tony"),
                new Contact("Marlin"),
                new Contact("Sam"),
                new Contact("Eli"),
                new Contact("Josh"),
                new Contact("Ted"),
                new Contact("Walter"),
                new Contact("Steve"),
                new Contact("Jack"),
                new Contact("Peter"),
                new Contact("Linus")
        };
    }

    public Contact[] getContacts(){
        return contacts;
    }
}
