package com.oder.food.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping("/home")
    public ResponseEntity<String> HomeController(){
        return new ResponseEntity<>("Welcome to food app", HttpStatus.OK);
    }
}
