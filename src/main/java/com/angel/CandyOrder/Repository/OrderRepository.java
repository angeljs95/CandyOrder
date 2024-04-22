package com.angel.CandyOrder.Repository;

import com.angel.CandyOrder.Entity.OrderPurchase;
import com.angel.CandyOrder.Entity.User;
import com.angel.CandyOrder.Enum.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderPurchase, Integer> {

    public List<OrderPurchase> findByUserId(Integer user_id);

    public List<OrderPurchase> findByStatus(Status status);

}
