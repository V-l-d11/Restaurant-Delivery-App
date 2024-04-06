package com.oder.food.repository;

import com.oder.food.model.IngridientsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngridientsCategoryRepositry extends JpaRepository<IngridientsCategory,Long> {

  List<IngridientsCategory> findByRestaurantId(Long id);



}
