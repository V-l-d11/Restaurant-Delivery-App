package com.oder.food.model;


import lombok.Data;

import java.util.List;

@Data
public class RestaurantCustomer {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInfo contactInformathion;
    private String openingHours;
    private boolean open;
    private List<Food> foods;


}
