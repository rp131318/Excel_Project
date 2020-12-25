package com.data.android.excelproject;


public class User {
    public String name, email, phone, age, city, gender;

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public User(){

    }

    public User(String name, String email, String phone, String age, String city, String gender) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.city = city;
        this.gender = gender;
    }
}
