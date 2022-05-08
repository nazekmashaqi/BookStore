package com.example.bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "order")
public class OrderDetails {
    @Id
    @GeneratedValue
    private int id;

//    @Temporal(TemporalType.DATE)
@Column(columnDefinition="TEXT")
private String createdDate;

    private double totalPrice;


    @ManyToOne()
    private User user;


    @OneToMany()
    private List<OrderItem> orderItems;


}
