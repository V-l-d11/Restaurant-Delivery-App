package com.oder.food.dto;

import com.oder.food.model.Restaurant;
import com.oder.food.model.RestaurantCustomer;
import org.springframework.stereotype.Component;

@Component
public class RestaurantCustomerConvert {

    public RestaurantCustomer convertToCustomerRestaurant(Restaurant restaurant) {
        RestaurantCustomer customerRestaurant = new RestaurantCustomer();
        customerRestaurant.setId(restaurant.getId());
        customerRestaurant.setName(restaurant.getName());
        customerRestaurant.setDescription(restaurant.getDescription());
        customerRestaurant.setCuisineType(restaurant.getCuisineType());
        customerRestaurant.setAddress(restaurant.getAddress());
        customerRestaurant.setContactInformathion(restaurant.getContactInformathion());
        customerRestaurant.setOpeningHours(restaurant.getOpeningHours());
        customerRestaurant.setOpen(restaurant.isOpen());
        customerRestaurant.setFoods(restaurant.getFoods());
        customerRestaurant.setImages(restaurant.getImages());
        return customerRestaurant;
    }
}
