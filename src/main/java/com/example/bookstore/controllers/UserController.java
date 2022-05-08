package com.example.bookstore.controllers;

import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.repositories.UserRepository;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;


    @PostMapping("user")
    public String CreateUser(User user) {
        try {
            userRepository.save(user);
            return "Added";
        } catch (Exception e) {
            return "name should be unique";
        }
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        return new User();

    }

    @GetMapping("/users")
    public List<User> getUsers() {

        return userRepository.findAll();

    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable int id) {

        try {
            userRepository.deleteById(id);
            return "Deleted";
        } catch (Exception e) {
            return "category id not exist";
        }
    }

    @PostMapping("wishlist/{userId}/{bookId}")
    public String addtowishlist(@PathVariable int userId, @PathVariable int bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                if (user.get().getWishlist().contains(book.get()))
                    return "book already exist in wishlist";
                user.get().getWishlist().add(book.get());
                userRepository.save(user.get());
            } else return "user id not exist";
            return "Book added to wishlist";
        }

        return "Book id not exist";
    }

    @DeleteMapping("wishlist/{userId}/{bookId}")
    public String removeFromWishlist(@PathVariable int userId, @PathVariable int bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                if (!user.get().getWishlist().contains(book.get()))
                    return "book NOT exist in wishlist";
                user.get().getWishlist().remove(book.get());
                userRepository.save(user.get());
            } else return "user id not exist";
            return "Book removed from wishlist";
        }

        return "Book id not exist";
    }


    @GetMapping("wishlist/{userId}")
    public List<Book> getwishlist(@PathVariable int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent())
            return user.get().getWishlist();

        return new ArrayList<>();
    }


}
