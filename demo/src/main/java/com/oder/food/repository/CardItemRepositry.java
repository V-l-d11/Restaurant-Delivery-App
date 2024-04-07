package com.oder.food.repository;

import com.oder.food.model.CardItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardItemRepositry extends JpaRepository<CardItem,Long> {
}
