package com.optimussoftware.db;

/**
 * Created by guerra on 27/09/16.
 */
public class Recovery implements java.io.Serializable {

    private String email;

    public Recovery() {
    }

    Recovery(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
