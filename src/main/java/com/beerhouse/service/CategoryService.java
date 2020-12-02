package com.beerhouse.service;

import com.beerhouse.exception.NotFoundException;
import com.beerhouse.model.Category;
import com.beerhouse.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Janaina Militão
 */
@Slf4j
@Service
public class CategoryService extends GenericService<Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    private static String MSG_CATEGORY_NOT_FOUND = "Category not found";

    public void create(String name){
        Category category = new Category(name);
        categoryRepository.saveAndFlush(category);
        log.info("Create category: "+category.toString());
    }

    public List<Category> list(){
        log.info("List category");
        return categoryRepository.findAll();
    }

    public void delete(Long categoryId) throws NotFoundException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(MSG_CATEGORY_NOT_FOUND));
        log.info("Delete beer: "+category.toString());
        categoryRepository.delete(category);
    }
}