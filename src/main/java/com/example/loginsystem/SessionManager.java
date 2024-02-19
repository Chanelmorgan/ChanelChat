package com.example.loginsystem;


public class SessionManager {
    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String newUsername) {
        username = newUsername;
    }
}