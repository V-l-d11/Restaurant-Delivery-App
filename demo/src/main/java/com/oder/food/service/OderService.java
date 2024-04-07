package com.oder.food.service;

import com.oder.food.model.Oder;
import com.oder.food.model.User;
import com.oder.food.requests.OderRequest;
import org.springframework.core.annotation.Order;

import java.util.List;

public interface OderService {

    public Oder createOrder(OderRequest order, User user) throws Exception;

    public Oder updateOder(Long oderId, String oderStatus) throws  Exception;

    public void calncelOder(Long oderId) throws Exception;

    public List<Oder> getUsersOder(Long userId) throws Exception;

    public List<Oder> getRestaurantsOder(Long restaurantId, String oderStatus) throws  Exception;


    public Oder findOderById(Long oderId) throws  Exception;


}
