// Additional model for user status
package com.example.model;

public class UserStatus {
    private String username;
    private boolean online;
    private String lastSeen;

    // Default constructor
    public UserStatus() {}

    // Constructor with parameters
    public UserStatus(String username, boolean online) {
        this.username = username;
        this.online = online;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }
}