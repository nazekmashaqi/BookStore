package com.example.bookstore;

import com.example.bookstore.controllers.BookController;
import com.example.bookstore.controllers.OrderController;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.OrderDetails;
import com.example.bookstore.models.User;
import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.repositories.OrderRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@ContextConfiguration(locations = {"classpath:WEB-INF/application-context.xml"})
public class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    OrderController orderController;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void getOrders() throws Exception {
        User user=new User(1,"nazek");
        OrderDetails order=new OrderDetails();
        order.setUser(user);

        List<OrderDetails> orders=  Collections.singletonList(order);
        Mockito.when(orderController.getOrders()).thenReturn(orders);

        mockMvc.perform(get("/orders")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].user.name", is("nazek")));
    }



    @Test
    public void getbookById() throws Exception {
        User user=new User(1,"nazek");
        OrderDetails order=new OrderDetails();
        order.setUser(user);

//       = new ArrayList<>(Arrays.asList(book, book2));
        List<OrderDetails> orders=  Collections.singletonList(order);

        ResponseEntity<List<OrderDetails>> responseEntity=new ResponseEntity<>(orders,HttpStatus.OK);

//        given(bookController.getBooks()).willReturn(books);
        Mockito.when(orderController.getUserOrders(user.getId())).thenReturn(responseEntity);

        mockMvc.perform(get("/order/"+user.getId())
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.name", is("nazek")));
    }


}
