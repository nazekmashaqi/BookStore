package com.example.bookstore;

import com.example.bookstore.controllers.BookController;
import com.example.bookstore.controllers.CategoryController;
import com.example.bookstore.models.Category;
import com.example.bookstore.repositories.CategoryRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ContextConfiguration(locations = {"classpath:WEB-INF/application-context.xml"})
public class CategoryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    CategoryController categoryController;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void getcategories() throws Exception {
        Category category = new Category();
        category.setName("TestCat");

        Category category2 = new Category();
        category2.setName("nazek2");

        List<Category> categories = new ArrayList<>(Arrays.asList(category, category2));

        Mockito.when(categoryController.getCategories()).thenReturn(categories);

        mockMvc.perform(get("/categories")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(category.getName())));
    }


    @Test
    public void AddCategory() throws Exception {
        Category category = new Category();
        category.setName("cat1");
        given(categoryController.CreateCategory(Mockito.any(Category.class))).willReturn("Added");

//        given(categoryController.unique()).willReturn("should be unique");

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mockMvc.perform(post("/category")
                        .param("name", category.getName())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Added")));
    }


    @Test
    public void getCategoryById() throws Exception {
        Category category = new Category();
        category.setName("cat1");

        given(categoryController.getCategory(category.getId())).willReturn(category);

        mockMvc.perform(get("/category/" + category.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(category.getId())));
    }


}
