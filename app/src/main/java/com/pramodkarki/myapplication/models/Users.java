package com.pramodkarki.myapplication.models;

public class Users {
    String profilePic, email, password, userName, lastMessage, userId, status;

    public Users() {
    }

    public Users(String profilePic, String email, String password, String userName, String lastMessage, String userId, String status) {
        this.profilePic = profilePic;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.userId = userId;
        this.status = status;
    }

    // SignUp Constructor
    public Users(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
