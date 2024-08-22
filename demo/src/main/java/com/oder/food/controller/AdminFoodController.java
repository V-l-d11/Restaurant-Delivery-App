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

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
        System.out.println("___====---==#################################");
        System.out.print(req + "All Request ---");
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantid());

        List<byte[]> decodedImages=new ArrayList<>();
        for(String imageBase64: req.getImages()){
            String base64Image= imageBase64.split(",")[1];
            byte[] decodedImage= Base64.getDecoder().decode(base64Image);
            decodedImages.add(decodedImage);
        }

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

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) Boolean vageterian,
            @RequestParam(required = false) Boolean seasonal,
            @RequestParam(required = false) Boolean nonveg,
            @RequestParam(required = false) String foodCategory
    ) throws  Exception{
        User user = userService.findUserByJwtToken(jwt);
        if (vageterian == null) vageterian = false;
        if (seasonal == null) seasonal = false;
        if (nonveg == null) nonveg = false;
        List<Food> foods = foodService.getRestaurantsFood(restaurantId, vageterian, nonveg, seasonal, foodCategory);
        System.out.println(foods +"Food Is Goood !!!!");
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }


}
