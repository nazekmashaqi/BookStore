package com.example.bookstore.models;

import com.example.bookstore.models.Category;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    @Id
    @GeneratedValue
    private @NonNull int id;

    @Column(unique = true)
    private @NonNull String name;
    private @NonNull String autherName;
    private @Nullable
    double price;

    @Value("exist")
    private String status;

    @ManyToOne
    Category category;


}
