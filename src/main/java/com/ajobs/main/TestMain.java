package com.ajobs.main;

import com.ajobs.dao.UserDao;
import com.ajobs.daoImp.UserDaoImp;

public class TestMain {

  public static void main(String[] args) {
    UserDao userDao = new UserDaoImp();
    userDao.addUser("yellowdog",
        "123",
        "大仙",
        "男",
        "http://47.96.11.185/images/a.jpg",
        23,
        "信阳",
        "罗山",
        "子路",
        "ewfwe@qq.com",
        "12345677899",
        "mmp");

  }
}
