package com.beerhouse.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Beer implements Serializable {

    @Id
    @SequenceGenerator(name = "beer_seq", sequenceName = "beer_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "beer_seq")
    private Long id;

    @NotNull(message = "name: required field")
    private String name;

    @NotNull(message = "ingredients: required field")
    private String ingredients;

    @NotNull(message = "price: required field")
    private BigDecimal price;

    @NotNull(message = "alcohol content: required field")
    private String alcoholContent;

    @NotNull(message = "category: required field")
    @OneToOne
    private Category category;

    public Beer( String name, String ingredients, BigDecimal price, String alcoholContent){
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        this.alcoholContent = alcoholContent;
    }

}
