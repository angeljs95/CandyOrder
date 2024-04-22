package com.angel.CandyOrder.Service;

import com.angel.CandyOrder.Entity.OrderPurchase;

import java.util.List;

public interface IOrderService {

    public OrderPurchase getOrder(Integer id);
    public List<OrderPurchase> listOrders();

    public OrderPurchase saveOrder(OrderPurchase order);

    public void deleteOrder(Integer id);
}
