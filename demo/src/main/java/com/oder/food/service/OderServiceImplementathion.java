package com.oder.food.service;


import com.oder.food.model.*;
import com.oder.food.repository.*;
import com.oder.food.requests.OderRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OderServiceImplementathion  implements  OderService{

    @Autowired
    private OderRepositry oderRepositry;


    @Autowired
    private OderItemRepositry oderItemRepositry;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddresRepositry addresRepositry;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CardService cardService;

    @Override
    public Oder createOrder(OderRequest order, User user) throws Exception {

        Address shippAddress=order.getDeliveryAddress();
        Address savedAddress=addresRepositry.save(shippAddress);

        if(!user.getAdresses().contains(savedAddress)){
            user.getAdresses().add(savedAddress);
          userRepository.save(user);
        }


        Restaurant restaurant= restaurantService.findRestaurantById(order.getRestaurantId());



            Oder createdOder = new Oder();
            createdOder.setCustomer(user);
            createdOder.setCreateAt(new Date());
            createdOder.setOderStatus("PENDING");
            createdOder.setDeliveryAddress(savedAddress);
            createdOder.setRestaurant(restaurant);

            Card card = cardService.findCardByUserId(user.getId());

            List<OderItem> oderItems = new ArrayList<>();

            for (CardItem cardItem : card.getItem()) {
                OderItem oderItem = new OderItem();
                oderItem.setFood(cardItem.getFood());
                oderItem.setIngredients(cardItem.getIngredients());
                oderItem.setQuantity(cardItem.getQuantity());
                oderItem.setTotalPrice(cardItem.getTotalPrice());

                OderItem savedItem = oderItemRepositry.save(oderItem);
                oderItems.add(savedItem);
            }

            Long totalPrice = cardService.calculateCardTotals(card);

            createdOder.setItems(oderItems);
            createdOder.setTotalPrice(totalPrice);

            Oder savedOder = oderRepositry.save(createdOder);
            restaurant.getOders().add(savedOder);



        return createdOder;
    }

    @Override
    public Oder updateOder(Long oderId, String oderStatus) throws Exception {
        Oder order= findOderById(oderId);

        if(oderStatus.equals("OUT_FOR_DELIVERY") || oderStatus.equals("DELIVERED") ||  oderStatus.equals("COMPLETED") || oderStatus.equals("PENDING")){
        order.setOderStatus(oderStatus);
        return oderRepositry.save(order);
        }

       throw new Exception("Please select a valid order status");
    }

    @Override
    public void calncelOder(Long oderId) throws Exception {

        Oder oder= findOderById(oderId);
        oderRepositry.deleteById(oderId);


    }

    @Override
    public List<Oder> getUsersOder(Long userId) throws Exception {
        return oderRepositry.findByCustomerId(userId);
    }

    @Override
    public List<Oder> getRestaurantsOder(Long restaurantId, String oderStatus) throws Exception {
        List<Oder> orders= oderRepositry.findByRestaurantId(restaurantId);
        if(oderStatus!=null){
            orders=orders.stream().filter(order-> order.getOderStatus().equals(oderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Oder findOderById(Long oderId) throws Exception {
        Optional<Oder> optionalOder= oderRepositry.findById(oderId);
        if(optionalOder.isEmpty()){
            throw  new Exception("oder not found");
        }
        return optionalOder.get();
    }
}
