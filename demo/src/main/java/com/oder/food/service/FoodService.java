package com.oder.food.service;

import com.oder.food.model.Category;
import com.oder.food.model.Food;
import com.oder.food.model.IngredientsItem;
import com.oder.food.model.Restaurant;
import com.oder.food.requests.CreateFoodRequest;

import java.util.List;

public interface FoodService {

   public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant, List<IngredientsItem> ingredients);

   void deleteFood(Long foodId) throws  Exception;

   public List<Food> getRestaurantsFood(Long restaurantId,
                                        boolean isVegetarian,
                                        boolean isNonVegetarian,
                                        boolean isSeasonal,
                                        String foodCategory);


   public List<Food> searchFood(String keyword);

   public Food findFoodById(Long  foodId) throws  Exception;

   public Food updateAvailibilityStatus(Long foodId) throws  Exception;


}
