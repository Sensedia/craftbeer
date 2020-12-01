package com.beerhouse.repository.category;

import static org.junit.Assert.assertTrue;

import com.beerhouse.model.Category;
import com.beerhouse.repository.CategoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation= Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.yml")
public class CategoryInsertTest {

    @Autowired
    private CategoryRepository repository;
    private Category category;

    @Before
    public void before() {
        category = new Category();
        category.setName("");
    }

    @Test
    public void save() {
        category = repository.save(category);
        assertTrue(category.getId() != null);
    }

    @After
    public void after() {
        repository.delete(category);
    }
}
