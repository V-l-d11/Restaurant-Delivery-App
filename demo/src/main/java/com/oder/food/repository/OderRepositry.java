package com.oder.food.repository;

import com.oder.food.model.Oder;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OderRepositry extends JpaRepository<Oder,Long> {

    public List<Oder> findByCustomerId(Long userId);

    public List<Oder> findByRestaurantId(Long restaurantId);



}
