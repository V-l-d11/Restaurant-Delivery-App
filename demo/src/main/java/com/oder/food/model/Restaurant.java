package com.oder.food.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @ToString.Exclude
    private User owner;

    private String name;

    private String description;

    private String cuisineType;

    @OneToOne
    private Address address;

    @Embedded
    private ContactInfo contactInformathion;

    private String openingHours;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Oder> oders= new ArrayList<>();

    @ElementCollection
    @Column(length = 1000)
    private List<String>images;

    private LocalDateTime registristrationDate;

    private boolean open;

    @JsonIgnore
    @OneToMany(mappedBy ="restaurant", cascade = CascadeType.ALL)
    private List<Food> foods= new ArrayList<>();


    @Override
    public String toString() {
        return "Restaurant{id=" + id + ", name='" + name + "', description='" + description + "'}";
    }

}
