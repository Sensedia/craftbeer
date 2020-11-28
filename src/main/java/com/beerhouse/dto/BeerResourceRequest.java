package com.beerhouse.dto;

import com.beerhouse.model.Beer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class BeerResourceRequest {

    @NotNull(message = "name: required field")
    private String name;

    @NotNull(message = "ingredients: required field")
    private String ingredients;

    @NotNull(message = "price: required field")
    private BigDecimal price;

    @NotNull(message = "alcohol content: required field")
    private String alcoholContent;

    @NotNull(message = "category: required field")
    private String category;

    public Beer convertAsObject() {
        return new Beer(name, ingredients, price, alcoholContent, category);
    }
}
