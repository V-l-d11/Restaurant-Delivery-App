package com.oder.food.requests;


import lombok.Data;

@Data
public class IngredientCategryRequest {

    private String name;
    private Long restaurantId;
}
