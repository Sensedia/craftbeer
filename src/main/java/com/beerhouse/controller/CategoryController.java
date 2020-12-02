package com.beerhouse.controller;

import com.beerhouse.model.Category;
import com.beerhouse.service.CategoryService;
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
@RequestMapping("/categories")
@Api(tags = "Categories", description = "Category-related operations")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Create Category", response = Category.class)
    @RequestMapping(method= RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE} )
    public ResponseEntity<?> createCategory(@RequestParam(required=true) String name) throws Exception {
        categoryService.create(name);
        return new ResponseEntity<Object>(null, HttpStatus.CREATED);
    }

    @ApiOperation(value = "List Category")
    @RequestMapping(method= RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Category>> listCategory() {
        return new ResponseEntity<>(categoryService.list(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Category", response = Category.class)
    @DeleteMapping(value = "/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) throws Exception {
        categoryService.delete(categoryId);
        return new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
    }
}