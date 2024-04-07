package com.oder.food.service;

import com.oder.food.model.Card;
import com.oder.food.model.CardItem;
import com.oder.food.model.Food;
import com.oder.food.model.User;
import com.oder.food.repository.CardItemRepositry;
import com.oder.food.repository.CardRepository;
import com.oder.food.repository.FoodRepositry;
import com.oder.food.requests.AddCardItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CardServiceImplementathion implements  CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
     private UserService userService;


    @Autowired
    private CardItemRepositry cardItemRepositry;


    @Autowired
    private FoodService foodService;

    @Override
    public CardItem addItemToCard(AddCardItemRequest req, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(req.getFoodId());

        Card card= cardRepository.findByCustomerId(user.getId());

        for(CardItem cardItem: card.getItem()){
            if(cardItem.getFood().equals(food)){
                int newQuantity=cardItem.getQuantity() + req.getQuantity();
                return  updateCardItemQuantity(cardItem.getId(),newQuantity);
            }
        }

        CardItem newCardItem= new CardItem();
        newCardItem.setFood(food);
        newCardItem.setCard(card);
        newCardItem.setQuantity(req.getQuantity());
        newCardItem.setIngredients(req.getIngredients());
        newCardItem.setTotalPrice(req.getQuantity() * food.getPrice());

        CardItem savedCardItem= cardItemRepositry.save(newCardItem);
        card.getItem().add(savedCardItem);

        return savedCardItem;
    }

    @Override
    public CardItem updateCardItemQuantity(Long cardItemId, int quantity) throws Exception {

        Optional<CardItem> cardItemOptional=cardItemRepositry.findById(cardItemId);
        if(cardItemOptional.isEmpty()){
            throw  new Exception("card item not exist or not found");
        }

        CardItem item=cardItemOptional.get();
        item.setQuantity(quantity);



        item.setTotalPrice(item.getFood().getPrice()* quantity);

        return  cardItemRepositry.save(item);
    }

    @Override
    public Card removeItemFromCard(Long cardItemId, String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);

        Card card=cardRepository.findByCustomerId(user.getId());

        Optional<CardItem> cardItemOptional=cardItemRepositry.findById(cardItemId);
        if(cardItemOptional.isEmpty()){
            throw  new Exception("card item not  found");
        }

        CardItem item=cardItemOptional.get();
        card.getItem().remove(item);

        return cardRepository.save(card);
    }

    @Override
    public Long calculateCardTotals(Card card) throws Exception {

        Long total=0L;

        for(CardItem cardItem:card.getItem()){
            total+=cardItem.getFood().getPrice()*cardItem.getQuantity();
        }

        return total;
    }

    @Override
    public Card findCardById(Long id) throws Exception {
       Optional<Card> optionalCard=cardRepository.findById(id);
       if(optionalCard.isEmpty()){
          throw new  Exception("card not found with id"+id);
       }
        return optionalCard.get();
    }

    @Override
    public Card findCardByUserId(Long userId) throws Exception {
        //User user=userService.findUserByJwtToken();
        Card card= cardRepository.findByCustomerId(userId);
        card.setTotal(calculateCardTotals(card));
        return card;
    }

    @Override
    public Card clearCard(Long userId) throws Exception {
    //    User user=userService.findUserByJwtToken(userId);

        Card card=findCardByUserId(userId);
        card.getItem().clear();
        return cardRepository.save(card);
    }
}
