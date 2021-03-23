package com.beerhouse;

import com.beerhouse.exception.NotFoundException;
import com.beerhouse.model.Beer;
import com.beerhouse.model.Category;
import com.beerhouse.model.Ingredient;
import com.beerhouse.repository.BeerRepository;
import com.beerhouse.repository.CategoryRepository;
import com.beerhouse.repository.IngredientRepository;
import com.beerhouse.service.CategoryService;
import com.beerhouse.service.IngredientService;
import io.restassured.RestAssured;

import net.minidev.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

/**
 * @author Janaina Militão
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestPropertySource(locations = "classpath:application-test.yml")
public class BeerApiRestTest {
    @LocalServerPort
    private int port;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BeerRepository beerRepository;
    
    @Autowired
    private IngredientService ingredientService;
    
    @Autowired
    private IngredientRepository ingredientRepository;

    private static final String CONTEXT_PATH = "/beerhouse/";
    private static final String CATEGORY_NAME = "Standard American Lager";
    private static final String BEER_NAME = "Bier Hollf Premium";
    private static final String INGREDIENT_NAME_1 = "Cevada";
    private static final String INGREDIENT_NAME_2 = "Alcool";

    @Before
    public void before(){
        RestAssured.baseURI="http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void test01_must_create_beer() throws Exception {
        categoryService.create(CATEGORY_NAME);
        Category category = categoryRepository.findByName(CATEGORY_NAME).get();

        ingredientService.create(INGREDIENT_NAME_1);
        ingredientService.create(INGREDIENT_NAME_2);
        List<String> ingredientsName = new ArrayList<>();
        ingredientsName.add(INGREDIENT_NAME_1);
        ingredientsName.add(INGREDIENT_NAME_2);
        List<Ingredient> ingredients = ingredientRepository.findAllByListName(ingredientsName);

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", BEER_NAME);
        requestParams.put("ingredients", ingredients.stream().map( i -> i.getId()).collect(Collectors.toList()));
        requestParams.put("price", new BigDecimal(15));
        requestParams.put("alcoholContent", "4,8%");
        requestParams.put("categoryId", category.getId());

        int  statusCode =  given().
                contentType("application/json").body(requestParams.toJSONString()).
                when().post(CONTEXT_PATH+"beers").
                then().
                assertThat().
                statusCode(HttpStatus.CREATED.value()).extract().statusCode();

        assertEquals(HttpStatus.CREATED.value(), statusCode);
        if(statusCode!=HttpStatus.CREATED.value()){
            categoryService.delete(category.getId());
        }
    }

    @Test
    public void test02_must_list_beer(){
        int  statusCode =  given().
                contentType("application/json").
                when().get(CONTEXT_PATH+"beers").
                then().
                assertThat().
                statusCode(HttpStatus.OK.value()).extract().statusCode();

        assertEquals(HttpStatus.OK.value(), statusCode);
    }

    @Test
    public void test03_must_put_beer() {
        Category category = categoryRepository.findByName(CATEGORY_NAME).get();
        List<String> ingredientsName = new ArrayList<>();
        ingredientsName.add(INGREDIENT_NAME_1);
        ingredientsName.add(INGREDIENT_NAME_2);
        List<Ingredient> ingredients = ingredientRepository.findAllByListName(ingredientsName);
        Long beerId = beerRepository.findByName(BEER_NAME).get().getId();

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", BEER_NAME);
        requestParams.put("ingredients", ingredients.stream().map( i -> i.getId()).collect(Collectors.toList()));
        requestParams.put("price", new BigDecimal(18));
        requestParams.put("alcoholContent", "4,8%");
        requestParams.put("categoryId", category.getId());

        int  statusCode =  given().
                contentType("application/json").body(requestParams.toJSONString()).
                when().put(CONTEXT_PATH+"beers/"+beerId).
                then().
                assertThat().
                statusCode(HttpStatus.OK.value()).extract().statusCode();

        assertEquals(HttpStatus.OK.value(), statusCode);
    }

    @Test
    public void test04_must_patch_beer() {
        Long beerId = beerRepository.findByName(BEER_NAME).get().getId();

        JSONObject requestParams = new JSONObject();
        requestParams.put("alcoholContent", "7,5%");

        int  statusCode =  given().
                contentType("application/json").body(requestParams.toJSONString()).
                when().patch(CONTEXT_PATH+"beers/"+beerId).
                then().
                assertThat().
                statusCode(HttpStatus.OK.value()).extract().statusCode();

        assertEquals(HttpStatus.OK.value(), statusCode);
    }

    @Test
    public void test05_must_delete_beer() throws NotFoundException {
        Optional<Beer> beer = beerRepository.findByName(BEER_NAME);
        Long beerId = beer.get().getId();

        ingredientService.delete(ingredientRepository.findByName(INGREDIENT_NAME_1).get().getId());
        ingredientService.delete(ingredientRepository.findByName(INGREDIENT_NAME_2).get().getId());

        int  statusCode =  given().
                contentType("application/json").
                when().delete(CONTEXT_PATH+"beers/"+beerId).
                then().
                assertThat().
                statusCode(HttpStatus.NO_CONTENT.value()).extract().statusCode();

        assertEquals(HttpStatus.NO_CONTENT.value(), statusCode);
        categoryService.delete(categoryRepository.findByName(CATEGORY_NAME).get().getId());
    }


    @After
    public void after(){
        RestAssured.reset();
    }
}
