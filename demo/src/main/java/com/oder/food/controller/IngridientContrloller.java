package com.oder.food.controller;


import com.oder.food.model.IngredientsItem;
import com.oder.food.model.IngridientsCategory;
import com.oder.food.requests.IngredientCategryRequest;
import com.oder.food.requests.IngredientRequest;
import com.oder.food.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingridients")
public class IngridientContrloller {

    @Autowired
    private IngredientsService ingredientsService;


    @PostMapping("/category/ingridients/add")
    public ResponseEntity<IngridientsCategory> createIngridientsCategory(
            @RequestBody IngredientCategryRequest req
    ) throws  Exception{
        System.out.println(req + "REQUESRT");
        IngridientsCategory category= ingredientsService.createIngridientsCategory(req.getName(),req.getRestaurantId(),req.getIngredients());
        return  new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<IngridientsCategory> createIngedientCategry(
            @RequestBody IngredientCategryRequest req
            ) throws Exception {
        IngridientsCategory item=ingredientsService.createIngridientCategory(req.getName(),req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngedientItem(
            @RequestBody IngredientRequest req
    ) throws Exception {
        System.out.println(req+ "requesrt------");
        IngredientsItem item=ingredientsService.createIngridientItem(req.getRestaurantId(),req.getName(),req.getCategoryId(), req.getPrice());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }


    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateIngridientStock(
            @PathVariable Long id
    ) throws Exception {
        IngredientsItem item=ingredientsService.updateSrock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

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

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteIngredientCategory(
            @PathVariable Long id
    ) throws Exception{
        ingredientsService.deleteIngredientCategory(id);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredientItem(
            @PathVariable Long id
    ) throws Exception{
        ingredientsService.deleteIngredientItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
