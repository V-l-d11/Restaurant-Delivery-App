package com.oder.food.controller;


import com.oder.food.model.Food;
import com.oder.food.model.Restaurant;
import com.oder.food.model.User;
import com.oder.food.requests.CreateFoodRequest;
import com.oder.food.service.FoodService;
import com.oder.food.service.RestaurantService;
import com.oder.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/search/food")
    public ResponseEntity<List<Food>> searchFoodCustomer(@RequestParam String name) throws  Exception{
         System.out.println("Search"+name);
         List<Food> foods=foodService.searchFood(name);
         return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,@RequestHeader("Authorization") String jwt) throws  Exception{

        User user=userService.findUserByJwtToken(jwt);

        List<Food> foods=foodService.searchFood(name);


        return new ResponseEntity<>(foods, HttpStatus.CREATED);
    }



    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam boolean vageterian,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonveg,
            @RequestParam(required = false) String foodCategory,
            @PathVariable Long restaurantId,

            @RequestHeader("Authorization") String jwt) throws  Exception{

        User user=userService.findUserByJwtToken(jwt);

        List<Food> foods=foodService.getRestaurantsFood(restaurantId,vageterian,nonveg,seasonal,foodCategory);


        return new ResponseEntity<>(foods, HttpStatus.OK);
    }




}
