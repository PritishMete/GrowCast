package com.example.growcast;

public class RegUserDet {
    private String userName,mail,phoneNumber,pass;

    public RegUserDet(String userName, String mail, String phoneNumber) {
        this.userName = userName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.pass = pass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
