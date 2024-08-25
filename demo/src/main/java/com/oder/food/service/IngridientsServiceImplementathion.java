package com.oder.food.service;


import com.oder.food.model.IngredientsItem;
import com.oder.food.model.IngridientsCategory;
import com.oder.food.model.Restaurant;
import com.oder.food.repository.IngridientItemRepositry;
import com.oder.food.repository.IngridientsCategoryRepositry;
import com.oder.food.requests.IngredientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    }  @Override
    @Transactional
    public IngridientsCategory createIngridientsCategory(String name , Long restaurantId, List<IngredientRequest> ingredients) throws  Exception{
        Restaurant restaurant =restaurantService.findRestaurantById(restaurantId);

        IngridientsCategory category= new IngridientsCategory();
        category.setRestaurant(restaurant);
        category.setName(name);
        IngridientsCategory savedCategory= ingridientsCategoryRepositry.save(category);

        if(ingredients != null && !ingredients.isEmpty()){
            List<IngredientsItem> items= ingredients.stream().map(ingredientRequest -> {
                IngredientsItem item= new IngredientsItem();
                item.setName(ingredientRequest.getName());
                item.setRestaurant(restaurant);
                item.setCategory(savedCategory);
                item.setPrice(ingredientRequest.getPrice());
                return item;
            }).collect(Collectors.toList());
            ingridientItemRepositry.saveAll(items);
            savedCategory.setIngredients(items);
        }
        return savedCategory;
    }

    @Override
    public List<IngridientsCategory> findIngridientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingridientsCategoryRepositry.findByRestaurantId(id);

    }

    @Override
    public IngredientsItem createIngridientItem(Long restaurantId, String ingridientName, Long categoryId, Long price) throws Exception {
     Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
     IngridientsCategory category=findIngridientCategoryById(categoryId);

     IngredientsItem item= new IngredientsItem();
     item.setName(ingridientName);
     item.setRestaurant(restaurant);
     item.setCategory(category);
     item.setPrice(price);


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


    @Override
   public void deleteIngredientCategory(Long id) throws  Exception{
        IngridientsCategory category=findIngridientCategoryById(id);
        List<IngredientsItem> items = ingridientItemRepositry.findByCategoryId(id);

        for(IngredientsItem item: items){
            ingridientItemRepositry.delete(item);
        }
        ingridientsCategoryRepositry.delete(category);
    }

    @Override
    public  void deleteIngredientItem(Long id) throws Exception{
        Optional<IngredientsItem> optionalItem= ingridientItemRepositry.findById(id);

        if(optionalItem.isEmpty()){
            throw  new Exception("Ingrdient item not found");
        }
        ingridientItemRepositry.delete(optionalItem.get());
    }

    @Override
    public IngredientsItem findIngredintById(Long id) throws Exception{
        Optional<IngredientsItem> optionalIngredient = ingridientItemRepositry.findById(id);
        if(optionalIngredient.isEmpty()){
            throw new Exception("Ingridient not found with id" + id);
        }
        return optionalIngredient.get();
    }

}
