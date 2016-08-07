package com.tudev.firstapp.data.dao;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Саша on 26.07.2016.
 */

public class Contact implements Serializable, Comparable<Contact>{
    private String name = "";
    private String email = "";
    private String phone = "";
    private long id;
    private String image = "";

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

    @Override
    public int compareTo(@NonNull Contact contact) {
        long externalId = contact.getId();
        return id < externalId ? -1
                : id == externalId ? 0
                : 1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: ").append(id)
                .append(name).append(", ")
                .append(email).append(", ");
        return builder.toString();
    }

    public static class ContactSimple implements Serializable{
        private String name = "";
        private String phone = "";
        private String imageThumb = "";
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