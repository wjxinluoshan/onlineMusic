package com.ajobs.dao;

import com.ajobs.pojo.User;

public interface UserDao {

  int addUser(Object... param);

  int deleteUser(int id);

  int updateUser(Object... param);

  User queryUser(int id);

  int login(String username, String password);

}
