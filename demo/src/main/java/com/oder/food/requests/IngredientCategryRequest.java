package com.oder.food.requests;


import lombok.Data;

import java.util.List;

@Data
public class IngredientCategryRequest {

    private String name;
    private Long restaurantId;
    private List<IngredientRequest> ingredients;
}
