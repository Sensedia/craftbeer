package com.beerhouse.service;

import com.beerhouse.exception.NotFoundException;
import com.beerhouse.model.Category;
import com.beerhouse.model.Ingredient;
import com.beerhouse.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientService {

    private static String MSG_INGREDIENT_NOT_FOUND = "Ingredient  not found";

    private final IngredientRepository ingredientRepository;

    public void create(String name) throws Exception {
        Optional<Ingredient> ingredientCreated = ingredientRepository.findByName(name);
        if(ingredientCreated.isPresent()){
            throw new Exception("Ingredient already registered");
        }
        Ingredient ingredient= new Ingredient(name);
        ingredientRepository.saveAndFlush(ingredient);
        log.info("Create ingredient: "+ingredient.toString());
    }

    public List<Ingredient> list(){
        log.info("List ingredient");
        return ingredientRepository.findAll();
    }

    public void delete(Long ingredientId) throws NotFoundException {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> new NotFoundException(MSG_INGREDIENT_NOT_FOUND));
        log.info("Delete ingredient: "+ingredient.toString());
        ingredientRepository.delete(ingredient);
    }
}
