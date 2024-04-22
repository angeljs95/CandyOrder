package com.angel.CandyOrder.Entity;

import com.angel.CandyOrder.Enum.OrderType;
import com.angel.CandyOrder.Enum.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    @Enumerated(EnumType.STRING)
    private Status status= Status.DRAFT;
    @ManyToOne
    private User user;


}
