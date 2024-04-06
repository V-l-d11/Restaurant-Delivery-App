package com.oder.food.service;

import com.oder.food.model.IngridientsCategory;

public interface IngredientsService {

    public IngridientsCategory createIngridientCategory(String name,Long restaurantId) throws  Exception;
    

}
