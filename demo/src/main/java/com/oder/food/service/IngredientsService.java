package com.oder.food.service;

import com.oder.food.model.IngredientsItem;
import com.oder.food.model.IngridientsCategory;

import java.util.List;

public interface IngredientsService {

    public IngridientsCategory createIngridientCategory(String name,Long restaurantId) throws  Exception;


    public IngridientsCategory findIngridientCategoryById(Long id) throws  Exception;

    public List<IngridientsCategory> findIngridientCategoryByRestaurantId(Long id) throws  Exception;

    public IngredientsItem createIngridientItem(Long restaurantId, String  ingridientName, Long categoryId) throws  Exception;

    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId);


    public IngredientsItem updateSrock(Long id) throws  Exception;

}
