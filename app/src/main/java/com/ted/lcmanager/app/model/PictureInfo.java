package com.ted.lcmanager.app.model;

import java.util.Date;

/**
 * Created by Ted on 2015/4/6.
 */
public class PictureInfo {
    private int all;
    private int featured;
    private int other;
    private int building;
    private int food;
    private int nature;
    private int people;
    private int technology;
    private int object;
    private Date updatedAt;
    private String objectId;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getTechnology() {
        return technology;
    }

    public void setTechnology(int technology) {
        this.technology = technology;
    }

    public int getObject() {
        return object;
    }

    public void setObject(int object) {
        this.object = object;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }
}
