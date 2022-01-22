package com.fhtechnikum.webshop.controller;

import com.fhtechnikum.webshop.API.ApiResponse;
import com.fhtechnikum.webshop.model.Category;
import com.fhtechnikum.webshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/category")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> body = categoryService.listCategories();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category) {
        // Check if the category exists.
        if (Objects.nonNull(categoryService.readCategory(category.getCategoryName()))) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Good news everyone, category already exists"), HttpStatus.CONFLICT);
        }
        // Create new category
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "Celebrations a new category was created"), HttpStatus.CREATED);
    }


    @PostMapping("/update/{categoryID}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryID") Integer categoryID, @Valid @RequestBody Category category) {
        // Check if the category exists.
        if (Objects.nonNull(categoryService.readCategory(categoryID))) {
            // If category exists then update.
            categoryService.updateCategory(categoryID, category);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Congrats you have updated the category"), HttpStatus.OK);
        }

        // If the category doesn't exist then return a response of unsuccessful.
        return new ResponseEntity<>(new ApiResponse(false, "Sorry friend that category does not exist"), HttpStatus.NOT_FOUND);
    }
}
