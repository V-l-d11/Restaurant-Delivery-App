package com.oder.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oder.food.dto.RestaurantDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.ElementCollection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="app_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private String fullName;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String password;


    private USER_ROL role=USER_ROL.ROLE_RESTAURANT_OWNER;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private List<Oder> oders= new ArrayList<>();

    @ElementCollection
    @ToString.Exclude
    private List<RestaurantDto>favorites= new ArrayList();

    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Address> adresses=new ArrayList<>();

    @Override
    public String toString() {
        return "User{id=" + id + ", fullName='" + fullName + "', email='" + email + "'}";
    }

}
