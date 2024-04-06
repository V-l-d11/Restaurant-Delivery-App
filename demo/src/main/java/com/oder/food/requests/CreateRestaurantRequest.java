package com.oder.food.requests;

import com.oder.food.model.Address;
import com.oder.food.model.ContactInfo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateRestaurantRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInfo contactInformathion;
    private String opiningHours;
    private List<String> images;
    private LocalDateTime register;

}
