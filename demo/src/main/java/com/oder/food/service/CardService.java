package com.oder.food.service;

import com.oder.food.model.Card;
import com.oder.food.model.CardItem;
import com.oder.food.model.User;
import com.oder.food.requests.AddCardItemRequest;

public interface CardService {

    public CardItem addItemToCard(AddCardItemRequest req, String jwt) throws  Exception;

    public CardItem updateCardItemQuantity(Long cardItemId, int quantity) throws  Exception;

    public Card removeItemFromCard(Long cardItemId, String jwt) throws  Exception;

    public Long calculateCardTotals(Card card) throws  Exception;

    public Card findCardById(Long id) throws  Exception;

    public Card findCardByUserId(String jwt) throws  Exception;

    public Card clearCard(String jwt) throws  Exception;


}
