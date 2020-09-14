package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Category;
import com.zzxx.travel.domain.User;

import java.util.List;

public interface UserDao {
    User findUserByUsername(String username);

    void saveUser(User user);

    int updateUserStatus(String code);

    User findByUsernameAndPassword(String username,String password);
}
