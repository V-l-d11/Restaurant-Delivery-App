package com.oder.food.dto;



import lombok.Data;

import java.util.List;

import javax.persistence.Column;

import javax.persistence.Embeddable;
@Data
@Embeddable
public class RestaurantDto {


    private String title;


    @Column(length=1000)
    private byte[] images;


    private String description;
    private Long id;

}
