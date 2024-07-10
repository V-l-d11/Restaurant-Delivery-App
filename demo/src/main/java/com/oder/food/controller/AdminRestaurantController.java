package com.oder.food.controller;
import com.oder.food.model.Restaurant;
import com.oder.food.model.User;
import com.oder.food.requests.CreateRestaurantRequest;
import com.oder.food.response.MassageResponse;
import com.oder.food.service.RestaurantService;
import com.oder.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

     @Autowired
     private RestaurantService restaurantService;

     @Autowired
     private UserService userService;


     @PostMapping()
     public ResponseEntity<Restaurant> createRestaurant(
             @RequestBody CreateRestaurantRequest req,
             @RequestHeader("Authorization") String jwt
             ) throws  Exception{
          System.out.println(req +"Request blaidiafejdiqjd" );
          User user=userService.findUserByJwtToken(jwt);
          Restaurant restaurant= restaurantService.createRestaurant(req,user);

          return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
     }


     @PutMapping("/{id}")
     public ResponseEntity<Restaurant> updateRestaurant(
             @RequestBody CreateRestaurantRequest req,
             @RequestHeader("Authorization") String jwt,
             @PathVariable Long id
     ) throws  Exception{
          System.out.println(req + "REQUEST UPDATE _____-----_________-----______");
          User user=userService.findUserByJwtToken(jwt);
          Restaurant restaurant= restaurantService.updateRestaurant(id,req);

          return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
     }


     @DeleteMapping ("/{id}")
     public ResponseEntity<MassageResponse> deleteRestaurant(
             @RequestHeader("Authorization") String jwt,
             @PathVariable Long id
     ) throws  Exception{

          User user=userService.findUserByJwtToken(jwt);
          restaurantService.deleteRestaurant(id);
          MassageResponse res=new MassageResponse();
          res.setMessage("restaurant  deleted Succesfuly");

          return new ResponseEntity<>(res, HttpStatus.OK);
     }

     @PutMapping("/{id}/status")
     public ResponseEntity<Restaurant> updateRestaurantStatus(
             @RequestHeader("Authorization") String jwt,
             @PathVariable Long id
     ) throws  Exception{

          User user=userService.findUserByJwtToken(jwt);

         Restaurant restaurant= restaurantService.updateRestaurantStatus(id);

          return new ResponseEntity<>(restaurant, HttpStatus.OK);
     }


     @GetMapping("/user")
     public ResponseEntity<Restaurant> findRestaurantByUserId(
             @RequestHeader("Authorization") String jwt
     ) throws  Exception{

          User user=userService.findUserByJwtToken(jwt);


          Restaurant restaurant= restaurantService.getRestaurantByUserId(user.getId());
          System.out.println(restaurant + "Restaurant Lets Go &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
          System.out.println("--------------------------------------------------------%%%%%%_--------------------");

          return new ResponseEntity<>(restaurant, HttpStatus.OK);
     }



}
