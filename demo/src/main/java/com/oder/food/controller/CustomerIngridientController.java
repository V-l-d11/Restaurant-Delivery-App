package com.oder.food.controller;


import com.oder.food.model.IngredientsItem;
import com.oder.food.model.IngridientsCategory;
import com.oder.food.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/customer/ingridients")
public class CustomerIngridientController {

    @Autowired
    private IngredientsService ingredientsService;


    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngridient(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientsItem> items=ingredientsService.findRestaurantsIngredients(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }


    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngridientsCategory>> getRestaurantIngridientCategry(
            @PathVariable Long id
    ) throws Exception {
        List<IngridientsCategory> items=ingredientsService.findIngridientCategoryByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }



}
