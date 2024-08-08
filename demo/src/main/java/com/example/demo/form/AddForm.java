package com.example.demo.form;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
@Validated
public class AddForm {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private Integer price;
    @NotBlank
    private String parentCategory;
    @NotBlank
    private String childCategory;
    @NotBlank
    private String grandChildCategory;
    @NotBlank
    private String brand;
    @NotNull
    private Integer condition;
    @NotBlank
    private String description;

    
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
    public String getParentCategory() {
        return parentCategory;
    }
    public void setParentCategory(String patentCategory) {
        this.parentCategory = patentCategory;
    }
    public String getChildCategory() {
        return childCategory;
    }
    public void setChildCategory(String childCategory) {
        this.childCategory = childCategory;
    }
    public String getGrandChildCategory() {
        return grandChildCategory;
    }
    public void setGrandChildCategory(String grandChilsCategory) {
        this.grandChildCategory = grandChilsCategory;
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
