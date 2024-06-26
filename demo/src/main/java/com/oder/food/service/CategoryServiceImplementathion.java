package com.oder.food.service;

import com.oder.food.model.Category;
import com.oder.food.model.Restaurant;
import com.oder.food.repository.CstegoryRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementathion implements  CategoryService {


    @Autowired
    private RestaurantService restaurantService;


    @Autowired
    private CstegoryRepositry categoryRepositry;

    @Override
    public Category createCategory(String name, Long userId) throws  Exception {
        Restaurant restaurant= restaurantService.getRestaurantByUserId(userId);
        Category category= new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        return  categoryRepositry.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        Restaurant restaurant= restaurantService.getRestaurantByUserId(id);

        return  categoryRepositry.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> optionalCategory= categoryRepositry.findById(id);
        if(optionalCategory.isEmpty()){
            throw new  Exception("category not found");
        }
        return optionalCategory.get();
    }
}
