package com.oder.food.controller;

import com.oder.food.model.Category;
import com.oder.food.model.User;
import com.oder.food.service.CategoryService;
import com.oder.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private UserService userService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization") String jwt) throws  Exception{

        User user=userService.findUserByJwtToken(jwt);
        Category createdCategory=categoryService.createCategory(category.getName(),user.getId());

        return  new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }


    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(
                                                   @RequestHeader("Authorization") String jwt) throws  Exception{

        User user=userService.findUserByJwtToken(jwt);
        List<Category> categories=categoryService.findCategoryByRestaurantId(user.getId());

        return  new ResponseEntity<>(categories, HttpStatus.CREATED);
    }


    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<Long> deleteCategory(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception{
    System.out.println(id + "Why");
    User user=userService.findUserByJwtToken(jwt);
    System.out.println(user + "User");
    categoryService.deleteCategory(id,user.getId());
    return new ResponseEntity<>(id,HttpStatus.OK);
    }

}
