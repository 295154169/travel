package com.zzxx.travel.service;

import com.zzxx.travel.domain.Category;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.exception.LoginException;

import java.util.List;

public interface UserService {
    boolean checkUserExist(String username);
    boolean registerUser(User user);

    boolean active(String code);
    User login(String username,String password) throws LoginException;

}
