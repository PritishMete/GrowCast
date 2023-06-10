package com.example.growcast;

public class RegUserDet {
    private String userName,mail,phoneNumber;

    public RegUserDet(String userName, String mail, String phoneNumber) {
        this.userName = userName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
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

}
