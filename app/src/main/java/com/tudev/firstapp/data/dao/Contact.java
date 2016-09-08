package com.tudev.firstapp.data.dao;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Саша on 26.07.2016.
 */

public class Contact implements Serializable, Comparable<Contact>{

    public static final String EMPTY = "";
    private static final String ISO_DATE = "yyyy-MM-dd";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_DATE, Locale.ENGLISH);

    private String name = EMPTY;
    private String email = EMPTY;
    private String phone = EMPTY;
    private long id;
    private String image = EMPTY;
    private String date = EMPTY;

    public Contact(long id){
        this.id = id;
    }

    public Contact(String n, String e){
        name = n;
        email = e;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getId(){
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String ref){
        image = ref;
    }

    public Date getNativeDate(){
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public void setNativeDate(Date date){
        this.date = dateFormat.format(date);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(@NonNull Contact contact) {
        long externalId = contact.getId();
        return id < externalId ? -1
                : id == externalId ? 0
                : 1;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                name + ", " +
                email + ", ";
    }

    public static class ContactSimple implements Serializable{
        private String name = EMPTY;
        private String phone = EMPTY;
        private String imageThumb = EMPTY;
        private long id;

        public ContactSimple(long id){
            this.id = id;
        }
        public ContactSimple(long id, Contact contact){
            this(id);
            name = contact.name;
            phone = contact.phone;
            imageThumb = contact.image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getId() {
            return id;
        }

        public String getImageThumb(){
            return imageThumb;
        }

        public void setImageThumb(String imageThumb){
            this.imageThumb = imageThumb;
        }

        @Override
        public boolean equals(Object obj) {
            return !(obj == null || !(obj instanceof ContactSimple)) && hashCode() == obj.hashCode();
        }

        @Override
        public int hashCode() {
            return (int)id;
        }
    }
}