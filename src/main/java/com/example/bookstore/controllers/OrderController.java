package com.example.bookstore.controllers;

import com.example.bookstore.repositories.OrderItemRepository;
import com.example.bookstore.repositories.OrderRepository;
import com.example.bookstore.repositories.UserRepository;
import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.models.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class OrderController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    Map<Integer, Integer> shoppingCart = new HashMap<>();

    @PostMapping("cart/{bookId}")
    public ResponseEntity<Map<Integer, Integer>> addToCart(@PathVariable int bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            if (shoppingCart.containsKey(book.get().getId())) {
                shoppingCart.put(book.get().getId(), shoppingCart.get(book.get().getId()).intValue() + 1);
                log.info("book alredy exist and quantity added by 1");
                return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
            }
            shoppingCart.put(book.get().getId(), 1);
            log.info("book added to shipping cart");
            return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
        }
        log.info("book not exist");
        return new ResponseEntity<>(shoppingCart, HttpStatus.ALREADY_REPORTED);


    }

    @DeleteMapping("cart/{bookId}")
    public ResponseEntity<Map<Integer, Integer>> removeFromCart(@PathVariable int bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            if (shoppingCart.containsKey(book.get().getId())) {
                shoppingCart.remove(book.get().getId());
                log.info("book removed from cart");
                return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
            }

        }
        log.info("book not exist");
        return new ResponseEntity<>(shoppingCart, HttpStatus.ALREADY_REPORTED);


    }

    @PostMapping("order/{userId}")
    public ResponseEntity<OrderDetails> addOrder(@PathVariable int userId) {
        if (shoppingCart.isEmpty()) {
            log.info("empty cart");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            OrderDetails order = new OrderDetails();
            order.setCreatedDate(DateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis())));

//            order.setCreatedDate(new Date(System.currentTimeMillis()));

            order.setUser(user.get());

            List<OrderItem> items = new ArrayList<>();

            double totalPrice = 0;
            for (Map.Entry<Integer, Integer> entry : shoppingCart.entrySet()) {
                System.out.println("--------------------------inner-----");
                Book book = bookRepository.findById(entry.getKey()).get();
                totalPrice += book.getPrice() * entry.getValue();
                OrderItem item = new OrderItem();
                item.setPrice(book.getPrice() * entry.getValue());
                item.setQuantity(entry.getValue());
                item.setBook(book);
                orderItemRepository.save(item);
                items.add(item);
//        System.out.println(order.getOrderItems());

            }
            order.setTotalPrice(totalPrice);
            order.setOrderItems(items);
            System.out.println(order.getOrderItems());

            System.out.println("-----------------------------------------------------");
            System.out.println(order.getCreatedDate());
            System.out.println("-----------------------------------------------------");
            orderRepository.save(order);
            log.info("order successed");
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        log.info("user not exist");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "order/{userId}")
    public ResponseEntity<List<OrderDetails>> getUserOrders(@PathVariable int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<OrderDetails> orders = orderRepository.findAll().stream().filter(order -> order.getUser().getId() == userId).collect(Collectors.toList());
            log.info("order response sent");
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }

        log.info("user not exist");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("orders/{userId}")
    public ResponseEntity<List<userOrderInterface>> userOrders(@PathVariable int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            log.info("order response sent");

//        orderRepository.Joining(userId).stream().map(i->i.getCretedDate().getClass()).forEach(System.out::println);
            return new ResponseEntity<>(orderRepository.Joining(userId), HttpStatus.OK);
        }

        log.info("user not exist");
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
@GetMapping("/orders")
    public List<OrderDetails> getOrders(){
        return orderRepository.findAll();
    }
//    @GetMapping("/string")
//    public String string(){
//        return "string";
//    }

}
