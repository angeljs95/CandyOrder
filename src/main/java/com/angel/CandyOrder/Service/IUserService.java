package com.angel.CandyOrder.Service;

import com.angel.CandyOrder.Entity.User;

import java.util.List;

public interface IUserService {

    public User getUser(int id);
    public List<User> users();

    public User save(User user);

    public void delete(int id);

}
