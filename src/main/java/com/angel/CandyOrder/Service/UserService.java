package com.angel.CandyOrder.Service;

import com.angel.CandyOrder.Entity.OrderPurchase;
import com.angel.CandyOrder.Entity.User;
import com.angel.CandyOrder.Enum.OrderType;
import com.angel.CandyOrder.Enum.Role;
import com.angel.CandyOrder.Enum.Status;
import com.angel.CandyOrder.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    String nl = System.lineSeparator();

    public User createUser(Scanner sc){
        User user =new User();
        logger.info("Register");
        logger.info(nl);
        logger.info("Enter your name: ");
        user.setName( sc.next());
        logger.info("Enter your Document: ");
        user.setDni(sc.nextInt());
        logger.info("""
        Are you an employee or supervisor?
        1.Employee
        2.Supervisor
        """);
        var role= 0;
        while(role==0){
            role=sc.nextInt();
            if(role==1){
            user.setRole(Role.EMPLOYEE);
            } else if(role==2){
                user.setRole(Role.SUPERVISOR);
            } else{
                logger.info("Selected a validate option");
                role=0;
            }
        }
       return save(user);
    }

    public User login(Scanner sc) {
        logger.info("Enter your name: ");
        var name = sc.next();
        logger.info("Enter your Document: ");
        var dni = sc.nextInt();
        User response = findUserByDni(dni);
        if (response == null) {
            logger.info("User not Found: ");
        }
        return response;
    }

    @Override
    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> users() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public User findUserByDni(int dni) {
        User user = userRepository.findUserByDni(dni);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    public void AcceptOrder(User user, OrderPurchase orderPurchase) {
        if (user.getRole().equals("Supervisor")) {
            orderPurchase.setStatus(Status.APPROVED);
            System.out.println("The order has been Aproved");
        } else {
            System.out.println("You don't have the authority to approve the order");
        }
    }

    public void rejectedOrder(User user, OrderPurchase orderPurchase) {
        if (user.getRole().equals("Supervisor")) {
            orderPurchase.setStatus(Status.REJECTED);
            System.out.println("The order has been Aproved");
        } else {
            System.out.println("You don't have the authority to approve the order");
        }
    }

}
