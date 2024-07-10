package com.oder.food.repository;

import com.oder.food.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngridientItemRepositry  extends JpaRepository<IngredientsItem,Long> {

List<IngredientsItem> findByRestaurantId(Long id);

List<IngredientsItem> findByCategoryId(Long id);

}
