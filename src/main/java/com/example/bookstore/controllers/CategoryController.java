package com.example.bookstore.controllers;

import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.repositories.CategoryRepository;
import com.example.bookstore.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class CategoryController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping("category")
    public String CreateCategory(Category category) {
        try {
            categoryRepository.save(category);
            return "Added";
        } catch (Exception e) {
           return unique();
        }
    }
public String unique(){ return "name should be unique"; }
    @GetMapping("/category/{id}")
    public Category getCategory(@PathVariable int id) {

        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent())
            return category.get();
        return new Category();

    }

    @GetMapping("/categories")
    public List<Category> getCategories() {

        return categoryRepository.findAll();

    }

    @DeleteMapping("/category/{id}")
    public String deleteCategory(@PathVariable int id) {

        try {
            categoryRepository.deleteById(id);
            return "Deleted";
        } catch (Exception e) {
            return "category id not exist";
        }
    }

}
