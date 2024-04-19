package com.oder.food.controller;


import com.oder.food.dto.RestaurantCustomerConvert;
import com.oder.food.model.Restaurant;
import com.oder.food.model.RestaurantCustomer;
import com.oder.food.model.User;
import com.oder.food.service.RestaurantService;
import com.oder.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/customers/restaurants")
public class CustomerRestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantCustomerConvert restaurantCustomerConvert;



    @GetMapping("/search")
    public ResponseEntity<List<RestaurantCustomer>> searchRestaurant(
            @RequestParam String keyword
    ) throws  Exception{
        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
        List<RestaurantCustomer> customerRestaurants = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            RestaurantCustomer customerRestaurant =
                restaurantCustomerConvert.convertToCustomerRestaurant(restaurant);
            customerRestaurants.add(customerRestaurant);
        }
        return new ResponseEntity<>(customerRestaurants, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<RestaurantCustomer>> getAllRestaurant() throws Exception {
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();
        List<RestaurantCustomer> customerRestaurants = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            RestaurantCustomer customerRestaurant = restaurantCustomerConvert.convertToCustomerRestaurant(restaurant);
            customerRestaurants.add(customerRestaurant);
        }
        return new ResponseEntity<>(customerRestaurants, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @PathVariable Long id
    ) throws  Exception{
        Restaurant restaurant= restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}

