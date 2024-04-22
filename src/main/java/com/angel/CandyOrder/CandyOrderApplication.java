package com.angel.CandyOrder;

import com.angel.CandyOrder.Entity.OrderPurchase;
import com.angel.CandyOrder.Entity.User;
import com.angel.CandyOrder.Enum.OrderType;
import com.angel.CandyOrder.Enum.Role;
import com.angel.CandyOrder.Enum.Status;
import com.angel.CandyOrder.Repository.UserRepository;
import com.angel.CandyOrder.Service.OrderService;
import com.angel.CandyOrder.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CandyOrderApplication implements CommandLineRunner {

    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(CandyOrderApplication.class);
    String nl = System.lineSeparator();


    public static void main(String[] args) {
        SpringApplication.run(CandyOrderApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        var exit = false;
        User user = null;
// Acceso al sistema o registro
        while (user == null) {
            user = login(sc, userService);
        }
        //el login nos devuelve un usuario
        logger.info("Welcome To CandyOrder :" + user.getName());
        while (!exit) { // validamos si el usuario es supervisor o no
            if (user.getRole().toString() == "SUPERVISOR") {
                showMenuSupervisor();
                exit = executeOptionSupervisor(sc, user);
            } else {
                showMenuEmployee();
                exit = executeOptionUser(sc, user);
            }
        }
    }

    public boolean executeOptionSupervisor(Scanner sc, User user) {
        var exit = false;
        OrderPurchase order = new OrderPurchase();
        var option = sc.nextInt();
        switch (option) {
            case 1 -> {
                List<OrderPurchase> list = orderService.listOrders();
                list.forEach(orders -> logger.info(orders.toString() + nl));
            }
            case 2 -> {
                List<OrderPurchase> lista = orderService.OrdersByStatus(Status.PENDING);
                lista.forEach(orders -> logger.info(orders.toString() + nl));
            }
            case 3 -> {
                var aux = false;
                while (!aux) {
                    List<OrderPurchase> ordersPending = orderService.OrdersByStatus(Status.PENDING);
                    ordersPending.forEach(orders -> logger.info(orders.toString() + nl));
                    logger.info("Enter the order ID for approval: ");
                    var read = sc.nextInt();
                    OrderPurchase orderToApproved = orderService.getOrder(read);
                    if (orderToApproved != null && orderToApproved.getStatus().equals(Status.PENDING)) {
                        orderToApproved.setStatus(Status.APPROVED);
                        orderService.saveOrder(orderToApproved);
                        logger.info("Order has been Approved with id: "+ orderToApproved.getId());
                        aux = true;
                    } else {
                        logger.info("enter a valid option"+ nl);
                    }
                }
            }
            case 4 -> {
                var aux = false;
                while (!aux) {
                    List<OrderPurchase> ordersPending = orderService.OrdersByStatus(Status.PENDING);
                    ordersPending.forEach(orders -> logger.info(orders.toString() + nl));
                    logger.info("Enter the order ID for Rejected: ");
                    var read = sc.nextInt();
                    OrderPurchase orderToApproved = orderService.getOrder(read);
                    if (orderToApproved != null && orderToApproved.getStatus().equals(Status.PENDING)) {
                        orderToApproved.setStatus(Status.REJECTED);
                        orderService.saveOrder(orderToApproved);
                        logger.info("The Order has been Rechazed with id: "+ orderToApproved.getId());
                        aux = true;
                    } else {
                        logger.info("enter a valid option"+ nl);
                    }
                }
            }
            case 5 -> {
                logger.info("See you later: " + user.getName() + nl);
                exit = true;
            }
            default -> logger.info("Option not found: " + option);
        }
        return exit;


    }

    public boolean executeOptionUser(Scanner sc, User user) {
        var exit = false;
        OrderPurchase order = new OrderPurchase();
        var option = sc.nextInt();
        switch (option) {
            case 1 -> {
                order = orderService.createOrder(sc, user);
                logger.info("The order has been created with Id " + order.getId() + nl);
            }
            case 2 -> {
                order.setStatus(Status.PENDING);
                orderService.saveOrder(order);
                logger.info("The order has been send" + nl);
            }
            case 3 -> {
                List<OrderPurchase> list = orderService.ordersByUser(user);
                list.forEach(orders -> logger.info(orders.toString() + nl));
            }
            case 4 -> {
                List<OrderPurchase> list = orderService.ordersApprovedByUser(user);
                list.forEach(orders -> logger.info(orders.toString() + nl));
                if (list.isEmpty()) {
                    logger.info("No approved orders found" + nl);
                }
            }
            case 5 -> {
                logger.info("See you later: " + user.getName() + nl);
                exit = true;
            }
            default -> logger.info("Option not found: " + option);
        }
        return exit;

    }

    private User login(Scanner sc, UserService userService) {
        User userResponse = new User();
        logger.info("""
                ***********Login****************
                 1. Sing in 
                 2. Register
                 3. Exit
                 """);
        logger.info("Enter a option to realize: ");
        var read = sc.nextInt();
        switch (read) {
            case 1 -> {
                userResponse = userService.login(sc);
            }
            case 2 -> {
                userResponse = userService.createUser(sc);
            }
            case 3 -> {
                logger.info("See you later");
            }
            default -> logger.info(" Unknow Option: " + read);
        }
        return userResponse;
    }

    private void showMenuEmployee() {
        logger.info(nl);
        logger.info("""
                ***********Candy Order****************
                 1. Create Order
                 2. Send Order
                 3. Show Pending Orders
                 4. Show Approved Orders
                 5. Exit
                                   
                 """);
        logger.info("Enter a option to realize: ");
    }

    private void showMenuSupervisor() {
        logger.info(nl);
        logger.info("""
                ***********Candy Order Administrative ****************
                 1. Show purchase orders
                 2. Show Pending Orderes
                 3. Validate Orders
                 4. Rejected Orders
                 5. Exit
                 """);
        logger.info("Enter a option to realize: ");
    }
}


