package com.example.mealplanner.Model;

public class UserSession {


    private static UserSession instance;


    private String uid;
    private String email;
    private String username;

    private UserSession() {
    }

    // Step 4: Provide a public static method to get the instance of the class
    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void clearSession() {
        instance = null;
    }
}

