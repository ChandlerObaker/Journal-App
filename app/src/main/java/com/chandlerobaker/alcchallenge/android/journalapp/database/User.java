package com.chandlerobaker.alcchallenge.android.journalapp.database;

public class User {


    private String userAccountId;
    private String userName;
    private String userEmail;


    public User(String userAccountId, String userName, String userEmail) {

        this.userAccountId = userAccountId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public User() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAccountId() {
        return userAccountId;
    }


}
