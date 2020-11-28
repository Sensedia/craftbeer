package com.beerhouse.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter @Setter
public class Beer {

    @Id
    @SequenceGenerator(name = "beer_seq", sequenceName = "beer_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "beer_seq")
    private Long id;

    @NotNull(message = "name: required field")
    private String name;

    @NotNull(message = "name: required field")
    private String ingredients;

    @NotNull(message = "price: required field")
    private BigDecimal price;

    @NotNull(message = "alcohol content: required field")
    private BigDecimal alcoholContent;

    @NotNull(message = "name: required field")
    private String category;

    @NotNull(message = "flavor: required field")
    private Flavor flavor;

    @NotNull(message = "source: required field")
    private Source source;

}
