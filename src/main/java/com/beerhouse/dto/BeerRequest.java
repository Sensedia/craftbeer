package com.beerhouse.dto;

import com.beerhouse.model.Beer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
public class BeerRequest {

    @NotNull(message = "name: required field")
    private String name;

    @NotNull(message = "ingredients: required field")
    private String ingredients;
}
