package com.company.models;

public class User {
    private long id;
    protected String email;
    protected String username;
    protected String password;
    protected String admin;

    public User(long id, String email, String username, String password, String admin) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public User(String email, String username, String password, String admin) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public User(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
