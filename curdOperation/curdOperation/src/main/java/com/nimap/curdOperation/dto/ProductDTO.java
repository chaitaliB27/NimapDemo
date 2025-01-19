package com.nimap.curdOperation.dto;

import java.util.List;

public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private Long categoryId;  // Keep only categoryId, not the whole CategoryDTO
    private CategoryDTO category;
    // Constructors
    public ProductDTO() {}

    public ProductDTO(Long id, String name, Double price, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;  // Only assign categoryId
    }

    public ProductDTO(Long id, String name, Double price) {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
