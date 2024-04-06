package com.oder.food.repository;

import com.oder.food.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CstegoryRepositry extends JpaRepository<Category, Long> {

    public List<Category> findByRestaurantId(Long id);



}
