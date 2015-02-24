package com.epam.kiev.onlineshop.entity;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by Администратор on 12/19/14.
 */
@Entity
public class User implements Serializable {

    private String login;
    private String password;
    private String id;
    private int permission;
    private String email;

    public User(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
