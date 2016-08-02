package com.tudev.firstapp.data;

import com.tudev.firstapp.data.Contact;

import java.util.ArrayList;

/**
 * Created by Саша on 27.07.2016.
 */

public enum Contacts{
    Instance;

    private ArrayList<Contact> contactsList;
    Contacts(){
        contactsList = arrayToArrayList(new Contact[]{
                Contact.createGmailContact("Alex"),
                Contact.createGmailContact("Bill"),
                Contact.createGmailContact("Tim"),
                Contact.createGmailContact("Bob"),
                Contact.createGmailContact("Alice"),
                Contact.createGmailContact("Kate"),
                Contact.createGmailContact("Tony"),
                Contact.createGmailContact("Marlin"),
                Contact.createGmailContact("Sam"),
                Contact.createGmailContact("Eli"),
                Contact.createGmailContact("Josh"),
                Contact.createGmailContact("Ted"),
                Contact.createGmailContact("Walter"),
                Contact.createGmailContact("Steve"),
                Contact.createGmailContact("Jack"),
                Contact.createGmailContact("Peter"),
                Contact.createGmailContact("Linus")
        });
    }

    public ArrayList<Contact> getContacts(){
        return contactsList;
    }

    private ArrayList<Contact> arrayToArrayList(Contact[] array){
        ArrayList<Contact> ret = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            ret.add(array[i]);
        }
        return ret;
    }
}