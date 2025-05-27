package com.example.growcast;

public class RegUserDet {
    private String mail;
    private String pass;
    private String userName;

    public RegUserDet(String userName2, String mail2, String pass2) {
        this.userName = userName2;
        this.mail = mail2;
        this.pass = pass2;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName2) {
        this.userName = userName2;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail2) {
        this.mail = mail2;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String phoneNumber) {
        this.pass = phoneNumber;
    }
}
