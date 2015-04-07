package com.ted.lcmanager.app.model;

/**
 * Created by Ted on 2015/3/29.
 */
public class StealDataModel {
    private int id;
    private String author;
    private String image_src;
    private String color;
    private String date;
    private String modified_date;
    private float ratio;
    private int width;
    private int height;
    private int featured;
    private int category;
    private int corrupt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCorrupt() {
        return corrupt;
    }

    public void setCorrupt(int corrupt) {
        this.corrupt = corrupt;
    }
}
