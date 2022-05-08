package com.example.bookstore.repositories;

import com.example.bookstore.models.OrderDetails;
import com.example.bookstore.models.userOrderInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderDetails, Integer> {

    @Query(value = " SELECT u.name as userName, o.created_date as createdDate ,o.total_price as price,b.name as bookName from Users u join Order_details" +
            " o on o.user_id=?1 join order_details_order_items oi on o.id=oi.order_details_id " +
            "join order_item oii on oii.id=oi.order_items_id join book b on b.id=oii.book_id where u.id=?1", nativeQuery = true)
    List<userOrderInterface> Joining(int id);
}
