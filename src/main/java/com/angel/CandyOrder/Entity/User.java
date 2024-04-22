package com.angel.CandyOrder.Entity;

import com.angel.CandyOrder.Enum.Role;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int dni;
    @Enumerated(EnumType.STRING)
    private Role role;
}
