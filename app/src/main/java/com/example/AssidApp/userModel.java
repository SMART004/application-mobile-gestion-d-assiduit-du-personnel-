package com.example.AssidApp;

public class userModel {

    private String userID, userName, userEmail, userPassword, userPhone, userStatut;

    public userModel(){}

    public userModel(String userID, String userName, String userEmail, String userPassword, String userPhone,String userStatut) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userStatut = userStatut;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserStatut() {
        return userStatut;
    }
}
