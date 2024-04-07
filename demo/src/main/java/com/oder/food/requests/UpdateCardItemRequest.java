package com.oder.food.requests;

import lombok.Data;

@Data
public class UpdateCardItemRequest {

    private Long cardItemId;
    private int quantity;

}
