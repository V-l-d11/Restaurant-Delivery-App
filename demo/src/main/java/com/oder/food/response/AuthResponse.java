package com.oder.food.response;

import com.oder.food.model.USER_ROL;
import lombok.Data;

@Data
public class AuthResponse {
    private  String jwt;
    private String message;
    private USER_ROL role;
}
