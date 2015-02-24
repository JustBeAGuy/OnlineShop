package com.epam.kiev.onlineshop.entity;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 * Created by Администратор on 12/20/14.
 */
@Entity
public class Order implements Serializable {

    private String id;
    private String userId;
    private String good;
    private String status;
    private String timeChange;

    public Order(){}

    public String getUser() {
        return userId;
    }

    public void setUser(String user) {
        this.userId = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getTime() {
        return timeChange;
    }

    public void setTime(String time) {
        this.timeChange = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
