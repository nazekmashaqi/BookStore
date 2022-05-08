package com.example.bookstore.controllers;


import com.example.bookstore.models.bookNameAndPrice;
import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.repositories.CategoryRepository;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.Category;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping("/book")
    public ResponseEntity<Book> CreateBook( Book book) {
        try {
            System.out.println("book id="+book.getId());
            bookRepository.save(book);
//            return new ResponseEntity<>("Added", HttpStatus.OK);
            return new ResponseEntity<>(book,HttpStatus.OK);
        } catch (Exception e) {
//            return new ResponseEntity<>("name should be unique",HttpStatus.CONFLICT) ;
        return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable int id) {

        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent())
            return book.get();
        log.info("book not exist");
        return new Book();

    }

    @GetMapping("/books")
    public List<Book> getBooks() {

        return bookRepository.findAll();

    }

//    @Value("${welcome.message}")
//    String string;

    @PostMapping("string")
    public String getString(String string) {
        return string;
    }

    @DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable int id) {

        try {
            bookRepository.deleteById(id);
            return "Deleted";
        } catch (Exception e) {
            return "book id not exist";
        }
    }

    @PostMapping("addCategoryToBook/{bookid}/{catid}")
    public String addCatToBook(@PathVariable int bookid, @PathVariable int catid) {
        Optional<Book> book = bookRepository.findById(bookid);
        if (!book.isPresent())
            return "Book not exist";

        Optional<Category> category = categoryRepository.findById(catid);
        if (!category.isPresent())
            return "Category not exist";

        book.get().setCategory(category.get());
        bookRepository.save(book.get());
        return "Category Added to Book";
    }

    @GetMapping("Categorybooks/{catname}")
    public List<Book> Categorybooks(@PathVariable("catname") String catName) {

        Optional<Category> category = categoryRepository.findByName(catName);
        if (category.isPresent())
            return bookRepository.findAllByCategory(category.get());

        return new ArrayList<>();
    }

    @GetMapping("/prices")
    public List<bookNameAndPrice> prices(){
        return bookRepository.getNamesAndPrice();
    }

}
