package com.example.demo.domain;

public class Item {
    private Integer id;
    private String name;
    private Integer price;
    private String category;
    private Integer categoryId;
    private String brand;
    private Integer condition;
    private String description;


    
    public Item() {
    }
    public Item(Integer id, String name, Integer price, String category, Integer categoryId, String brand,
            Integer condition, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.categoryId = categoryId;
        this.brand = brand;
        this.condition = condition;
        this.description = description;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public Integer getCondition() {
        return condition;
    }
    public void setCondition(Integer condition) {
        this.condition = condition;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    
   
}
