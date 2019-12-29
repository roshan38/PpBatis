package com.pp.dao;

import com.pp.bean.User;

import java.util.List;

public interface UserDao {
    List<User> selectAllUsers();

    int deleteUserById(String id);

    void testException();
}
