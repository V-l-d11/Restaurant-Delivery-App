package com.oder.food.service;

import com.oder.food.model.Oder;
import com.oder.food.model.User;
import com.oder.food.requests.OderRequest;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OderService {

    public Oder createOrder(OderRequest order, User user) throws Exception;

    public Oder updateOder(Long oderId, String oderStatus) throws  Exception;

    public void calncelOder(Long oderId) throws Exception;

    public Page<Oder> getUsersOder(Long userId,int page,int size) throws Exception;

    public Page<Oder> getRestaurantsOder(Long restaurantId, String oderStatus, int page, int size) throws  Exception;


    public Oder findOderById(Long oderId) throws  Exception;

    Page<Oder> getOderByCreateAt(Date createAt, int page, int size) throws Exception;

    Page<Oder> getOdersByDateRange(Date startDate, Date endDate, int page, int size) throws  Exception;

    Page<Oder> getOdersByCustomerFullName(String fullName,int page, int size) throws  Exception;

}
