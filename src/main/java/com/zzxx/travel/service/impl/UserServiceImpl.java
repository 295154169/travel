package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.UserDao;
import com.zzxx.travel.dao.impl.UserDaoImpl;
import com.zzxx.travel.domain.Category;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.exception.LoginException;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.util.MailUtils;
import com.zzxx.travel.util.UuidUtil;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public boolean checkUserExist(String username) {
        try {
            userDao.findUserByUsername(username);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean registerUser(User user) {
        try {
            user.setStatus("N");
            user.setCode(UuidUtil.getUuid());
            userDao.saveUser(user);
            String text = "<a href='http://localhost:80/travel/user/active?code="+user.getCode()+"'>账号激活</a>";
            MailUtils.sendMail(user.getEmail(),text,"测试");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean active(String code) {
        int count = userDao.updateUserStatus(code);
        return count != 0;
    }

    @Override
    public User login(String username, String password) throws LoginException {
        User user = userDao.findByUsernameAndPassword(username, password);
        if (user == null){
            throw new LoginException("账号未找到");
        }else if (user.getStatus().equals("N")){
            throw new LoginException("账号未激活");
        }else {
            return user;
        }
    }

}
