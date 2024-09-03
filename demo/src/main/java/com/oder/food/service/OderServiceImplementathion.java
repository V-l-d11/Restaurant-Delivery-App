package com.oder.food.service;


import com.oder.food.model.*;
import com.oder.food.repository.*;
import com.oder.food.requests.OderRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import java.util.*;


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
    public Page<Oder> getUsersOder(Long userId, int page , int size) throws Exception {
        Pageable pageable= PageRequest.of(page,size);
        return oderRepositry.findByCustomerId(userId,pageable);
    }

    @Override
    public Page<Oder> getRestaurantsOder(Long restaurantId, String oderStatus, int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        if (oderStatus == null) {
            return oderRepositry.findByRestaurantId(restaurantId, pageable);
        }
        return oderRepositry.findByRestaurantIdAndOderStatus(restaurantId, oderStatus, pageable);
    }

    @Override
    public Oder findOderById(Long oderId) throws Exception {
        Optional<Oder> optionalOder= oderRepositry.findById(oderId);
        if(optionalOder.isEmpty()){
            throw  new Exception("oder not found");
        }
        return optionalOder.get();
    }

//    @Override
//    public Page<Oder> getOderByCreateAt(Date createAt, int page, int size) throws Exception {
//       Pageable pageable= PageRequest.of(page,size);
//        java.sql.Date sqlDate = new java.sql.Date(createAt.getTime());
//        return oderRepositry.findByCreateAt(sqlDate, pageable);
//    }

    @Override
    public Page<Oder> getOderByCreateAt(Date createAt, int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);

        LocalDate localDate = createAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // Определение начала и конца дня
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.plusDays(1).atStartOfDay();

        // Преобразование LocalDateTime в Instant и затем в java.util.Date
        Date startDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

        // Преобразование в java.sql.Date
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

        return oderRepositry.findByCreateAtBetween(sqlStartDate, sqlEndDate, pageable);
    }


    @Override
    public Page<Oder> getOdersByDateRange(Date startDate, Date endDate, int page, int size ) throws Exception{
        Pageable pageable=PageRequest.of(page,size);
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
        return oderRepositry.findByCreateAtBetween(sqlStartDate, sqlEndDate, pageable);
    }


    @Override
    public Page<Oder> getOdersByCustomerFullName(String fullName, int page, int size) throws Exception{
        Pageable pageable=PageRequest.of(page,size);

        return oderRepositry.findByCustomerFullNameContaining(fullName,pageable);
    }


    @Override
    public Map<String,Long> getOrderStatusSummary(Long restaurantId) throws Exception{
        List<Object[]> results=oderRepositry.countOrdersByStatusForRestaurant(restaurantId);
        Map<String,Long> statusSummary = new HashMap<>();

        for(Object[] result: results){
            String status= (String) result[0];
            Long count= (Long) result[1];
            statusSummary.put(status,count);
        }
        return statusSummary;
    }

}
