package com.example.myfirebaseapp.model;

import androidx.annotation.Nullable;

import com.example.myfirebaseapp.Utils;

public class User {

     private String itemId;
     private String Name;
     private int Age;
     private String city;
     private String profession;

    public User(){

    }

     public User(String Name, int Age,String itemId,String city, String profession){
        this.Age= Age;
        this.Name =Name;
        this.itemId= itemId;
        this.city= city;
        this.profession= profession;
    }


    public String getName() {
        return Name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCity() {
        return city;
    }

    public String getProfession() {
        return profession;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof User){
            User user =(User)obj;
            return this.itemId.equals(user.getItemId());
        }
        else return false;
    }
}
