package com.oder.food.requests;


import com.oder.food.model.Address;
import lombok.Data;

@Data
public class OderRequest {
 private Long restaurantId;
 private Address deliveryAddress;

}
