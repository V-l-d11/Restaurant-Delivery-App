package com.oder.food.model;

import lombok.Data;

import java.util.List;

@Data
public class FoodCustomer {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Long foodCategoryId;
    private  Long restaurantId;
    private List<String> images;
    private boolean available;

}
