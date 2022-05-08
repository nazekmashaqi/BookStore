package com.example.bookstore;

import com.example.bookstore.controllers.BookController;
import com.example.bookstore.models.Book;
import com.example.bookstore.repositories.BookRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
public class BookControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookRepository bookRepository;

    @MockBean
    BookController bookController;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void getBooks() throws Exception {
        Book book = new Book();
        book.setName("nazek");
        book.setAutherName("nn");

        Book book2 = new Book();
        book2.setName("nazek2");
        book2.setAutherName("nn");

        List<Book> books = new ArrayList<>(Arrays.asList(book, book2));
        ;//Collections.singletonList(book);

//        given(bookController.getBooks()).willReturn(books);
        Mockito.when(bookController.getBooks()).thenReturn(books);

        mockMvc.perform(get("/books")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(book.getName())));
    }


    @Test
    public void AddBooks() throws Exception {
        Book book = new Book();
        book.setName("nazek");
        book.setAutherName("nn");
        book.setId(4);
        given(bookController.CreateBook(Mockito.any(Book.class))).willReturn(new ResponseEntity<>(book, HttpStatus.OK));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mockMvc.perform(post("/book")
//                                .content(mapper.writeValueAsString(book))
//                                .contentType(APPLICATION_JSON)
//                                .accept(APPLICATION_JSON)
                                .param("name", book.getName())
                                .param("autherName", book.getAutherName())
                                .param("status", book.getStatus())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(book.getId())));
    }


    @Test
    public void getbookById() throws Exception {
        Book book = new Book();
        book.setName("book1");

        given(bookController.getBook(book.getId())).willReturn(book);

        mockMvc.perform(get("/book/" + book.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(book.getId())));
    }

    @Test
    public void returnString() throws Exception {

        given(bookController.getString("book")).willReturn("book");
        mockMvc.perform(post("/string")
                        .content(mapper.writeValueAsString("book")
                        )
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .param("string", "book")

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("book")));
    }


}
