package com.beerhouse.controller;

import com.beerhouse.model.Ingredient;
import com.beerhouse.service.IngredientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Janaina Militão
 */
@RestController
@RequestMapping("/ingredients")
@Api(tags = "Ingredients", description = "Ingredient-related operations")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @ApiOperation(value = "Create Ingredient", response = Ingredient.class)
    @RequestMapping(method= RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE} )
    public ResponseEntity<?> createIngredient(@RequestParam(required=true) String name) throws Exception {
        ingredientService.create(name);
        return new ResponseEntity<Object>(null, HttpStatus.CREATED);
    }

    @ApiOperation(value = "List Ingredient")
    @RequestMapping(method= RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Ingredient>> listIngredient() {
        return new ResponseEntity<>(ingredientService.list(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Ingredient", response = Ingredient.class)
    @DeleteMapping(value = "/{ingredientId}")
    public ResponseEntity<?> deleteIngredient(@PathVariable("ingredientId") Long ingredientId) throws Exception {
        ingredientService.delete(ingredientId);
        return new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
    }
}
