package com.oder.food.repository;

import com.oder.food.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CstegoryRepositry extends JpaRepository<Category, Long> {

    public List<Category> findByRestaurantId(Long id);

    @Query("SELECT c FROM Category c")
    public  List<Category> findAllCategories();

}
