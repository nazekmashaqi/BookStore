package com.example.bookstore;

import com.example.bookstore.controllers.BookController;
import com.example.bookstore.controllers.UserController;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.User;
import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.repositories.UserRepository;
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
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserController userController;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void getUsers() throws Exception {
        User user = new User();
        user.setName("nazek");

        User user2 = new User();
        user2.setName("nazek2");


        List<User> users = new ArrayList<>(Arrays.asList(user, user2));

        Mockito.when(userController.getUsers()).thenReturn(users);

        mockMvc.perform(get("/users")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(user.getName())));
    }


    @Test
    public void AddUser() throws Exception {
        User user = new User();
        user.setName("nazek");
        given(userController.CreateUser(Mockito.any(User.class))).willReturn("Added");


        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mockMvc.perform(post("/user")
                                .param("name", user.getName())

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Added")));
    }


    @Test
    public void getUserById() throws Exception {
        User user = new User();
        user.setName("user1");

        given(userController.getUser(user.getId())).willReturn(user);

        mockMvc.perform(get("/user/" + user.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(user.getId())));
    }


}
