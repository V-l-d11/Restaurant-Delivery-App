package com.oder.food.service;

import com.oder.food.dto.RestaurantDto;
import com.oder.food.model.Address;
import com.oder.food.model.ContactInfo;
import com.oder.food.model.Restaurant;
import com.oder.food.model.User;
import com.oder.food.repository.AddresRepositry;

import com.oder.food.repository.RestaurantRepository;
import com.oder.food.repository.UserRepository;
import com.oder.food.requests.CreateRestaurantRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImplement  implements RestaurantService{

    private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceImplement.class);

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddresRepositry addresRepositry;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address= addresRepositry.save(req.getAddress());
        logger.info("Saved address: {}", address);
        Restaurant restaurant= new Restaurant();

        restaurant.setAddress(address);
        restaurant.setContactInformathion(req.getContactInformathion());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpiningHours());
        restaurant.setRegistristrationDate(LocalDateTime.now());
        restaurant.setOwner(user);


        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        if (updatedRestaurant != null) {

            restaurant.setName(updatedRestaurant.getName());
            restaurant.setDescription(updatedRestaurant.getDescription());
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
            restaurant.setOpeningHours(updatedRestaurant.getOpiningHours());
            restaurant.setImages(updatedRestaurant.getImages());
            restaurant.setRegistristrationDate(updatedRestaurant.getRegister());


            if (updatedRestaurant.getAddress() != null) {
                Address updatedAddress = updatedRestaurant.getAddress();
                Address savedAddress = addresRepositry.save(updatedAddress);
                restaurant.setAddress(savedAddress);
            }


            if (updatedRestaurant.getContactInformathion() != null) {
                restaurant.setContactInformathion(updatedRestaurant.getContactInformathion());
            }
        }

        // Сохраняем ресторан
        return restaurantRepository.save(restaurant);
    }


    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
     Restaurant restaurant =findRestaurantById(restaurantId);
     restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {

        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt=restaurantRepository.findById(id);

        if(opt.isEmpty()){
            throw  new Exception("restaurant not found id"+id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        System.out.println(userId+ "USERID INTO SERVICE");
        Restaurant restaurant =restaurantRepository.findByOwnerId(userId);
        System.out.println(restaurant+ "Restaurant INTO SERVICE");
        if(restaurant ==null){
            throw  new Exception("Restaurant not found with owner id"+userId);
        }

        return  restaurant;
    }



    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {

        Restaurant restaurant =findRestaurantById(restaurantId);

        RestaurantDto dto=new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
       // dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        boolean isFavorite=false;
        List<RestaurantDto> favorites=user.getFavorites();
        for(RestaurantDto favorite: favorites){
            if(favorite.getId().equals(restaurantId)){
                isFavorite=true;
                break;
            }

        }

        if(isFavorite){
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        }else{
            favorites.add(dto);
        }


        userRepository.save(user);

        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {

        Restaurant restaurant=findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}
