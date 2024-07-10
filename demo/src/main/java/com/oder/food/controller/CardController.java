package com.oder.food.controller;

import com.oder.food.model.Card;
import com.oder.food.model.CardItem;
import com.oder.food.model.User;
import com.oder.food.requests.AddCardItemRequest;
import com.oder.food.requests.UpdateCardItemRequest;
import com.oder.food.service.CardService;
import com.oder.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;



    @Autowired
    private UserService userService;

    @PutMapping("/card/add")
    public ResponseEntity<CardItem> addItemToCard(@RequestBody AddCardItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {



        CardItem cardItem=cardService.addItemToCard(req,jwt);
        return new ResponseEntity<>(cardItem, HttpStatus.OK);
    }

    @PutMapping("/card-item/update")
    public ResponseEntity<CardItem> updateCardItemQuantity(
                                                  @RequestBody UpdateCardItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {

        CardItem cardItem=cardService.updateCardItemQuantity(req.getCardItemId(),req.getQuantity());
        return new ResponseEntity<>(cardItem, HttpStatus.OK);
    }


    @DeleteMapping("/card-item/{id}/remove")
    public ResponseEntity<Card> removeCardItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        Card card=cardService.removeItemFromCard(id,jwt);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }


    @PutMapping("/card/clear")
    public ResponseEntity<Card> clearCard(

            @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Card card=cardService.clearCard(user.getId());
        return new ResponseEntity<>(card, HttpStatus.OK);
    }


    @GetMapping("/card")
    public ResponseEntity<Card> findUserCard(

            @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Card card=cardService.findCardByUserId(user.getId());
        return new ResponseEntity<>(card, HttpStatus.OK);
    }


}
