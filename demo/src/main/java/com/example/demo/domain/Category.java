package com.example.demo.domain;

public class Category {
    private Integer id ;
    private String categoryName;
    private Integer parentId;
    private String nameAll;

    
    public Category() {
    }
    
    public Category(Integer id, String categoryName, Integer parentId, String nameAll) {
        this.id = id;
        this.categoryName = categoryName;
        this.parentId = parentId;
        this.nameAll = nameAll;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Integer getParentId() {
        return parentId;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public String getNameAll() {
        return nameAll;
    }
    public void setNameAll(String nameAll) {
        this.nameAll = nameAll;
    }

    

}
