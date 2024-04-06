package com.oder.food.requests;

import com.oder.food.model.Category;
import com.oder.food.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;

    private Long restaurantid;
    private boolean vegetarian;
    private boolean seasional;
    private List<IngredientsItem> ingridients;


}
