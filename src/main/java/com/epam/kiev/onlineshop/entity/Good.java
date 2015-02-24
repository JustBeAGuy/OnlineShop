package com.epam.kiev.onlineshop.entity;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by Администратор on 12/20/14.
 */
@Entity
public class Good implements Serializable{
    private String id;
    private String name;
    private String price;
    private String img;
    private String category;
    private String description;
    private String availability;

    public Good(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getAvailability() {
        return availability;
    }
}
