package com.oder.food.controller;


import com.oder.food.model.Food;
import com.oder.food.model.FoodCustomer;
import com.oder.food.service.FoodService;
import com.oder.food.service.RestaurantService;
import com.oder.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/foodl")
public class CustomerFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<FoodCustomer>> searchFood(@RequestParam String name) throws  Exception{
        System.out.println("Hello"+ name);
        List<Food> foods=foodService.searchFood(name);
        List<FoodCustomer> foodCustomers= new ArrayList<>();

        for (Food food: foods){
            FoodCustomer customerFood=new FoodCustomer();
            customerFood.setId(food.getId());
            customerFood.setName(food.getName());
            customerFood.setDescription(food.getDescription());
            customerFood.setPrice(food.getPrice());
            customerFood.setRestaurantId(food.getRestaurant().getId());
            customerFood.setFoodCategoryId(food.getFoodCategory().getId());
            customerFood.setImages(food.getImages());
            customerFood.setAvailable(food.isAvailable());
            foodCustomers.add(customerFood);
        }

        return new ResponseEntity<>(foodCustomers, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodCustomer>> getRestaurantFoodCustomer(
            @RequestParam (required = false) boolean vageterian,
            @RequestParam (required = false) boolean seasonal,
            @RequestParam(required = false) boolean nonveg,
            @RequestParam(required = false) String foodCategory,
            @PathVariable Long restaurantId

    )throws  Exception{
        List<Food> foods= foodService.getRestaurantsFood(restaurantId,vageterian,nonveg,seasonal,foodCategory);
         List<FoodCustomer> foodCustomer=new ArrayList<>();

         for(Food food:foods){
             FoodCustomer customerFood=new FoodCustomer();
             customerFood.setId(food.getId());
             customerFood.setName(food.getName());
             customerFood.setDescription(food.getDescription());
             customerFood.setImages(food.getImages());
             customerFood.setPrice(food.getPrice());
             customerFood.setRestaurantId(food.getRestaurant().getId());
             customerFood.setFoodCategoryId(food.getFoodCategory().getId());
             customerFood.setAvailable(food.isAvailable());
             foodCustomer.add(customerFood);

         }

        return new ResponseEntity<List<FoodCustomer>>(foodCustomer,HttpStatus.OK);
    }


}
