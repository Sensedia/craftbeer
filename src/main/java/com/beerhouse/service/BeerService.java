package com.beerhouse.service;

import com.beerhouse.dto.BeerRequest;
import com.beerhouse.exception.NotFoundException;
import com.beerhouse.model.Beer;
import com.beerhouse.model.Category;
import com.beerhouse.model.Ingredient;
import com.beerhouse.repository.BeerRepository;
import com.beerhouse.repository.CategoryRepository;
import com.beerhouse.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Janaina Militão
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BeerService {

    private final BeerRepository beerRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;

    private static String MSG_BEER_NOT_FOUND = "Beer not found";
    private static String MSG_CATEGORY_NOT_FOUND = "Category not found";

    public void create(BeerRequest beerRequest) throws Exception {
        Optional<Beer> beerCreated = beerRepository.findByName(beerRequest.getName());

        if(beerCreated.isPresent()){
            throw new Exception("Beer already registered");
        }
        Category category = categoryRepository.findById(beerRequest.getCategoryId()).orElseThrow(() -> new NotFoundException(MSG_CATEGORY_NOT_FOUND));
        List<Ingredient> ingredients = ingredientRepository.findAllByListId(beerRequest.getIngredients());

        Beer beer = beerRequest.convertAsObject();
        beer.setCategory(category);
        beer.setIngredients(ingredients);
        beerRepository.saveAndFlush(beer);

        ingredients.stream().forEach( i-> {
          i.setBeer(beer);
          ingredientRepository.saveAndFlush(i);
        });

        log.info("Create beer: " + beer.toString());
    }

    public List<Beer> list(){
       log.info("List beer");
        List<Beer> beersReturn = new ArrayList<>();
        List<Beer> beers = beerRepository.findAll();

        for(Beer beer: beers){
            beer.setIngredients(ingredientRepository.findByBeer_Id(beer.getId()));
            beersReturn.add(beer);
        }

       return beersReturn;
    }

    public Beer recover(Long beerId) throws Exception {
        log.info("Recover beer: " + beerId);
        return beerRepository.findById(beerId).orElseThrow(() -> new NotFoundException(MSG_BEER_NOT_FOUND));
    }

    public Beer recoverBeerByIngredient(Long ingredientId) throws Exception {
        log.info("Recover beer: " + ingredientId);

        Ingredient ingredient = ingredientRepository.findById(ingredientId).get();

        Beer beer = beerRepository.findByIngredientsIn(Arrays.asList(ingredient)).orElseThrow(() -> new NotFoundException(MSG_BEER_NOT_FOUND));

        beer.setIngredients(new ArrayList<>());
        beer.getIngredients().add(ingredient);

        return beer;
    }


    public void edit(Long beerId, BeerRequest beerResourceRequest) throws NotFoundException {
        Beer beer = beerRepository.findById(beerId).orElseThrow(() -> new NotFoundException(MSG_BEER_NOT_FOUND));
        Category category = categoryRepository.findById(beerResourceRequest.getCategoryId()).orElseThrow(() -> new NotFoundException(MSG_CATEGORY_NOT_FOUND));
        List<Ingredient> ingredients = ingredientRepository.findAllByListId(beerResourceRequest.getIngredients());
        ingredients.stream().findAny().orElseThrow(() -> new NotFoundException("Ingredient not found."));
        log.info("Edit beer - original fields: "+beer.toString());

        Beer beerEdit = beerResourceRequest.convertAsObject();
        beerEdit.setCategory(category);
        beerEdit.setIngredients(ingredients);
        beerEdit.setId(beer.getId());
        log.info("Edit beer - edited fields: "+beerEdit.toString());
        beerRepository.saveAndFlush(beerEdit);
    }

    public void edit(Long beerId, Map<String, Object> fields) throws NotFoundException {
        Beer beer = beerRepository.findById(beerId).orElseThrow(() -> new NotFoundException(MSG_BEER_NOT_FOUND));
        log.info("Edit beer - original fields: "+beer.toString());

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Beer.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, beer, v);
        });

        log.info("Edit beer - edited fields: "+beer.toString());
        beerRepository.saveAndFlush(beer);
    }

    public void delete(Long beerId) throws NotFoundException {
        Beer beer = beerRepository.findById(beerId).orElseThrow(() -> new NotFoundException(MSG_BEER_NOT_FOUND));

        log.info("Delete beer: "+beer.toString());
        beerRepository.delete(beer);
    }
}