package com.oder.food.dto;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Image {

    private String url;
}
