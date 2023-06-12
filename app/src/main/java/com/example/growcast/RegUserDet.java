package com.example.growcast;

public class RegUserDet {
    private String userName,mail,pass;

    public RegUserDet(String userName, String mail, String pass) {
        this.userName = userName;
        this.mail = mail;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String phoneNumber) {
        this.pass = phoneNumber;
    }

}
