package com.oder.food.controller;

import com.oder.food.model.Food;
import com.oder.food.model.Restaurant;
import com.oder.food.model.User;
import com.oder.food.requests.CreateFoodRequest;
import com.oder.food.response.MassageResponse;
import com.oder.food.service.FoodService;
import com.oder.food.service.RestaurantService;
import com.oder.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;


    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req, @RequestHeader("Authorization") String jwt) throws  Exception{

        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantid());
        Food food=foodService.createFood(req, req.getCategory(),restaurant);


        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<MassageResponse> deleteFood(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws  Exception{

        User user=userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);
        MassageResponse res=new MassageResponse();
        res.setMessage("Foood delete Succesfuly");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws  Exception{

        User user=userService.findUserByJwtToken(jwt);
        Food food=foodService.updateAvailibilityStatus(id);


        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

}
