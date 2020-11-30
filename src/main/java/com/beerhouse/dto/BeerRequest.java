package com.beerhouse.dto;

import com.beerhouse.model.Beer;
import com.beerhouse.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ToString
@Getter @Setter
public class BeerRequest {

    @NotNull(message = "name: required field")
    private String name;

    @NotNull(message = "ingredients: required field")
    private String ingredients;

    @NotNull(message = "price: required field")
    private BigDecimal price;

    @NotNull(message = "alcohol content: required field")
    private String alcoholContent;

    @NotNull(message = "categoryId: required field")
    private Long categoryId;

    public Beer convertAsObject() {
        return new Beer(name, ingredients, price, alcoholContent);
    }
}
