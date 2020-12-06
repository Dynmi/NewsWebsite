package com.example.demo.services;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    //自动注入一个userDao
    @Autowired
    private UserDao userDao;

    //用户注册逻辑
    public String register(User user) {
        //判断用户是否存在
        if (userDao.getOneUser(user.getUsername()) == null) {
            userDao.addUser(user);
            return "注册成功";
        }
        else {
            return "该用户名已被使用";
        }
    }
    //用户登陆逻辑
    public String login(String username,String password) {
        //通过用户名获取用户
        User dbUser = userDao.getOneUser(username);

        //若获取失败
        if (dbUser == null) {
            return "该用户不存在";
        }
        //获取成功后，将获取用户的密码和传入密码对比
        else if (!dbUser.getPassword().equals(password)){
            return "密码或用户名错误";
        }
        else {
            //若密码也相同则登陆成功
            //让传入用户的属性和数据库保持一致
            return "登陆成功";
        }
    }

    //用户修改密码逻辑
    public String chPassword(String username,String oldpassword,String password,String rpassword) {
        //通过用户名获取用户
        User cpUser = userDao.getOneUser(username);
        //
        //判断更改密码过程
        if (cpUser == null) {
            return "用户名输入错误";
        } else if (!Objects.equals(password, rpassword)) {
            return "两次密码不一致";
        } else if (!cpUser.getPassword().equals(oldpassword)) {
            return "密码或用户名错误";
        } else {
            userDao.updatePassword(password,username);
            return "更改成功！";
        }
    }

}
