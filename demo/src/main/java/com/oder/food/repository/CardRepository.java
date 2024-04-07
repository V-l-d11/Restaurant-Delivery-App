package com.oder.food.repository;

import com.oder.food.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Long> {

    public Card findByCustomerId(Long userId);



}
