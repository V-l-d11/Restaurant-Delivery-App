package com.oder.food.service;


import com.oder.food.model.IngredientsItem;
import com.oder.food.model.IngridientsCategory;
import com.oder.food.model.Restaurant;
import com.oder.food.repository.IngridientItemRepositry;
import com.oder.food.repository.IngridientsCategoryRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngridientsServiceImplementathion implements  IngredientsService {


    @Autowired
    private IngridientItemRepositry ingridientItemRepositry;

    @Autowired
    private IngridientsCategoryRepositry ingridientsCategoryRepositry;

    @Autowired
    private RestaurantService restaurantService;



    @Override
    public IngridientsCategory createIngridientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);

        IngridientsCategory category=new IngridientsCategory();
        category.setRestaurant(restaurant);
        category.setName(name);

        return ingridientsCategoryRepositry.save(category);
    }

    @Override
    public IngridientsCategory findIngridientCategoryById(Long id) throws Exception {
        Optional<IngridientsCategory> opt=ingridientsCategoryRepositry.findById(id);

        if(opt.isEmpty()){
            throw  new Exception("ingridients category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngridientsCategory> findIngridientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingridientsCategoryRepositry.findByRestaurantId(id);

    }

    @Override
    public IngredientsItem createIngridientItem(Long restaurantId, String ingridientName, Long categoryId) throws Exception {
     Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
     IngridientsCategory category=findIngridientCategoryById(categoryId);

     IngredientsItem item= new IngredientsItem();
     item.setName(ingridientName);
     item.setRestaurant(restaurant);
     item.setCategory(category);


     IngredientsItem ingredient=ingridientItemRepositry.save(item);
     category.getIngredients().add(ingredient);

     return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
        return ingridientItemRepositry.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateSrock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngrientsItem= ingridientItemRepositry.findById(id);
        if(optionalIngrientsItem.isEmpty()){
            throw  new Exception("ingredients not found");
        }
        IngredientsItem ingredientsItem=optionalIngrientsItem.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());

        return  ingridientItemRepositry.save(ingredientsItem);
    }

}
