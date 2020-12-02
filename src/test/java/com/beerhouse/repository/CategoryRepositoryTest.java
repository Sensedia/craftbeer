package com.beerhouse.repository;

import com.beerhouse.model.Category;
import com.beerhouse.repository.CategoryRepository;
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
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;
    private Category category;

    @Before
    public void before() {
        create();
    }

    private void  create(){
        category = new Category();
        category.setName("Witbier");
        repository.save(category);
    }

    @Test
    public void test01_save() {
        assertTrue(category.getId() != null);
    }

    @Test
    public void test02_findById() {
        Optional<Category> optionalTrue = repository.findById(category.getId()); //
        assertTrue(optionalTrue.isPresent());
    }

    @Test
    public void test03_update() {
        String name = "Vienna Lager";
        category.setName(name);
        repository.save(category);
        assertEquals(category.getName(), name);
    }

    @After
    public void after() {
        repository.delete(category);
    }
}
