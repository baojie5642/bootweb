package com.baojie.admin.pojo;

import java.io.Serializable;

public class AccountQuery implements Serializable {

    private static final long serialVersionUID = 4781731661087232639L;
    private String username;


    public AccountQuery() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AccountQuery{" +
                "username='" + username + '\'' +
                '}';
    }
}
