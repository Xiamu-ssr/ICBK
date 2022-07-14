package com.me.pojo;

public class HaveLogin {
    private String email;
    public HaveLogin(){}
    public HaveLogin(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "HaveLogin{" +
                "email='" + email + '\'' +
                '}';
    }
}
