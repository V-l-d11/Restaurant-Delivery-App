package com.oder.food.controller;

import com.oder.food.model.CardItem;
import com.oder.food.model.Oder;
import com.oder.food.model.User;
import com.oder.food.requests.AddCardItemRequest;
import com.oder.food.requests.OderRequest;
import com.oder.food.service.OderService;
import com.oder.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OderController {


    @Autowired
    private OderService oderService;

    @Autowired
    private UserService userService;


    @PostMapping("/oder")
    public ResponseEntity< Oder> createOder(@RequestBody OderRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Oder oder= oderService.createOrder(req,user);
        return new ResponseEntity<>(oder, HttpStatus.OK);
    }

    @GetMapping("/oder/user")
    public ResponseEntity<Page<Oder>> getOderHistory(
                                           @RequestHeader("Authorization") String jwt,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size
                                           ) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
       Page<Oder> oders= oderService.getUsersOder(user.getId(), page,size);
        return new ResponseEntity<>(oders, HttpStatus.OK);
    }

    @PutMapping("/oder")
    public ResponseEntity<Oder> updateOderStatus(
            @RequestParam Long oderId,
            @RequestParam String status,
            @RequestHeader("Authorization") String jwt) throws Exception{
        Oder oder= oderService.updateOder(oderId, status);
        return new ResponseEntity<>(oder,HttpStatus.OK);
    }




}
