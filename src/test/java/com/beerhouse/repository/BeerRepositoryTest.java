package com.beerhouse.repository;

import com.beerhouse.model.Beer;
import com.beerhouse.model.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author Janaina Militão
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation= Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.yml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BeerRepositoryTest {

    @Autowired
    private BeerRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Beer beer;

    @Before
    public void before() {
        create();
    }

    private void  create(){
        beer = new Beer();
        beer.setName("Bamberg");
        beer.setIngredients("Álcool e ceveda");
        beer.setAlcoholContent("6%");
        beer.setPrice(new BigDecimal(15.50));
        beer.setCategory(create_category());
        repository.save(beer);
    }

    private Category create_category(){
        Category category = new Category();
        category.setName("Rauchibier");
        categoryRepository.save(category);
        return category;
    }

    @Test
    public void test01_save() {
        assertTrue(beer.getId() != null);
    }

    @Test
    public void test02_findById() {
        Optional<Beer> optionalTrue = repository.findById(beer.getId()); //
        assertTrue(optionalTrue.isPresent());
    }

    @Test
    public void test03_update() {
        beer.setAlcoholContent("7.5%");
        beer.setPrice(new BigDecimal(19.50));
        repository.save(beer);
        assertEquals(beer.getAlcoholContent(), "7.5%");
        assertEquals(beer.getPrice(), 19.50);
    }

    @After
    public void after() {
        categoryRepository.delete(beer.getCategory());
        repository.delete(beer);
    }
}
