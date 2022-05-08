package com.example.bookstore.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private @NonNull int id;
    private @NonNull String name;
    private

    @ManyToMany
    @JoinTable(
            name = "Wishlist",
            joinColumns = @JoinColumn(name = "User_id"),
            inverseJoinColumns = @JoinColumn(name = "Book_id"))
    List<Book> wishlist;

//    @OneToMany(mappedBy = "user")
////    @JoinTable(
////            name = "orders",
////            joinColumns = @JoinColumn(name = "User_id"),
////            inverseJoinColumns = @JoinColumn(name = "order_id"))
//    List<OrderDetails> orders;

}
