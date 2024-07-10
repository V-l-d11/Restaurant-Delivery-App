package com.oder.food.requests;

import java.util.List;
import com.oder.food.model.Address;
import lombok.Data;

@Data
public class OderRequest {
 private Long restaurantId;
 private Address deliveryAddress;

}
