package com.oder.food.repository;

import com.oder.food.model.Oder;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface OderRepositry extends JpaRepository<Oder,Long> {

    public Page<Oder> findByCustomerId(Long userId, Pageable pageable);

    public Page<Oder> findByRestaurantId(Long restaurantId, Pageable pageable);

    Page<Oder> findByRestaurantIdAndOderStatus(Long restaurantId, String oderStatus, Pageable pageable);

    Page<Oder> findByCreateAt(Date oderDate, Pageable pageable);

    Page<Oder> findByCreateAtBetween(Date startDate, Date endDate, Pageable pageable);
}
