package com.angel.CandyOrder.Service;

import com.angel.CandyOrder.Entity.OrderPurchase;
import com.angel.CandyOrder.Entity.User;
import com.angel.CandyOrder.Enum.OrderType;
import com.angel.CandyOrder.Enum.Status;
import com.angel.CandyOrder.Repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    String nl = System.lineSeparator();


    public OrderPurchase createOrder(Scanner sc, User user) {
        OrderPurchase order = new OrderPurchase();

        order.setUser(user);
        logger.info(" Enter the description of the order: ");
        order.setDescription(sc.next());
        logger.info(""" 
                Enter the type of purchase: 
                1.Detail
                2. Wholesaler 
                """);
        var type = 0;
        while (type == 0) {
            type = sc.nextInt();
            if (type == 1) {
                order.setOrderType(OrderType.RETAIL);
            } else if (type == 2) {
                order.setOrderType(OrderType.WHOLESALER);
            } else {
                logger.info("Selected a validate option");
                type = 0;
            }
        }
        return saveOrder(order);
    }

    public List<OrderPurchase> ordersByUser(User user) {
        List<OrderPurchase> list = orderRepository.findByUserId(user.getId());
        return list;
    }

    public List<OrderPurchase> ordersApprovedByUser(User user) {
        List<OrderPurchase> list = orderRepository.findByUserId(user.getId());
        List<OrderPurchase> ordersApproved = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            OrderPurchase a = null;
            a = list.get(i);
            if (a.getStatus().equals("APPROVED")) {
                ordersApproved.add(a);
            }
        }
        return ordersApproved;
    }

    public List<OrderPurchase> OrdersByStatus(Status status){
        return orderRepository.findByStatus(status);
    }

    @Override
    public OrderPurchase getOrder(Integer id) {
        OrderPurchase order = orderRepository.findById(id).orElse(null);
        return order;
    }

    @Override
    public List<OrderPurchase> listOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderPurchase saveOrder(OrderPurchase order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}
