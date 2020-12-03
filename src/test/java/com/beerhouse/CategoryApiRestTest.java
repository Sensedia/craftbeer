package com.beerhouse;

import com.beerhouse.Application;
import com.beerhouse.model.Category;
import com.beerhouse.repository.CategoryRepository;
import com.beerhouse.service.CategoryService;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

/**
 * @author Janaina Militão
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestPropertySource(locations = "classpath:application-test.yml")
public class CategoryApiRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CategoryRepository repository;

    private static final String CONTEXT_PATH = "/beerhouse/";

    private static final String CATEGORY_NAME = "Standard American Lager";

    @Before
    public void before(){
        RestAssured.baseURI="http://localhost";
        RestAssured.port = port;
    }

    @After
    public void after(){
        RestAssured.reset();
    }

    @Test
    public void test01_must_create_category(){
        int  statusCode =  given().
                contentType("application/json").queryParam("name", CATEGORY_NAME).
                when().post(CONTEXT_PATH+"categories").
                then().
                assertThat().
                statusCode(HttpStatus.CREATED.value()).extract().statusCode();

        assertEquals(HttpStatus.CREATED.value(), statusCode);
    }

    @Test
    public void test02_must_list_category(){
        int  statusCode =  given().
                contentType("application/json").
                when().get(CONTEXT_PATH+"categories").
                then().
                assertThat().
                statusCode(HttpStatus.OK.value()).extract().statusCode();

        assertEquals(HttpStatus.OK.value(), statusCode);
    }

    @Test
    public void test03_must_delete_category(){

        Optional<Category> category = repository.findByName(CATEGORY_NAME);
        Long categoryId = category.get().getId();

        int  statusCode =  given().
                contentType("application/json").
                when().delete(CONTEXT_PATH+"categories/"+categoryId).
                then().
                assertThat().
                statusCode(HttpStatus.NO_CONTENT.value()).extract().statusCode();

        assertEquals(HttpStatus.NO_CONTENT.value(), statusCode);
    }

}