package com.example.bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue
    private int id;

    private int quantity;

    private double price;

    @OneToOne
    private Book book;

//    @ManyToOne
//    private OrderDetails order;

}
