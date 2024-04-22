package com.angel.CandyOrder.Repository;

import com.angel.CandyOrder.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    public User findUserByDni(int dni);

}
