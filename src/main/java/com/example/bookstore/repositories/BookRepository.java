package com.example.bookstore.repositories;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.Category;
import com.example.bookstore.models.bookNameAndPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

    List<Book> findAllByCategory(Category category);

    @Query("select new com.example.bookstore.models.bookNameAndPrice(b.name ,b.price) from Book b")
    List<bookNameAndPrice> getNamesAndPrice();

}
