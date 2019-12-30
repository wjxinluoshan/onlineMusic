package com.ajobs.daoImp;

import com.ajobs.dao.UserDao;
import com.ajobs.pojo.User;
import com.ajobs.tool.JdbcUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImp implements UserDao {

  private PreparedStatement preparedStatement;
  private String addUserSql = "insert into tb_user(username,password,nickname,gender,pic_url,age,province,city,area,email,phone,remark) values(?,?,?,?,?,?,?,?,?,?,?,?)";
  private String deleteUserSql = "delete from tb_user where id=?";
  private String updateUserSql = "update tb_user set username=?,password=?,nickname=?,gender=?,pic_url=?,age=?,province=?,city=?,area=?,email=?,phone=?,remark=? where id=?";
  private String queryUserSql = "select * from tb_user where id=?";
  private String loginUserSql = "select id from tb_user where username=? and password=?";

  @Override
  public int addUser(Object... param) {
    preparedStatement = JdbcUtil.getPreparedStatement(addUserSql);
    try {
      addAndUpdateInsertPreparedStatementData(param);
      return preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      JdbcUtil.closeConnection();
    }
    return 0;
  }

  @Override
  public int deleteUser(int id) {
    preparedStatement = JdbcUtil.getPreparedStatement(deleteUserSql);
    try {
      preparedStatement.setInt(1, id);
      return preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      JdbcUtil.closeConnection();
    }
    return 0;
  }

  @Override
  public int updateUser(Object... param) {
    preparedStatement = JdbcUtil.getPreparedStatement(updateUserSql);
    try {
      addAndUpdateInsertPreparedStatementData(param);
      preparedStatement.setInt(13, (Integer) param[12]);
      return preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      JdbcUtil.closeConnection();
    }
    return 0;
  }

  @Override
  public User queryUser(int id) {
    preparedStatement = JdbcUtil.getPreparedStatement(queryUserSql);
    ResultSet resultSet = null;
    try {
      preparedStatement.setInt(1, id);
      resultSet = preparedStatement.executeQuery();
      if(resultSet.next()){
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setNickname(resultSet.getString("nickname"));
        user.setGender(resultSet.getString("gender"));
        user.setPic_url(resultSet.getString("pic_url"));
        user.setAge(resultSet.getInt("age"));
        user.setProvince(resultSet.getString("province"));
        user.setCity(resultSet.getString("city"));
        user.setArea(resultSet.getString("area"));
        user.setEmail(resultSet.getString("email"));
        user.setPhone(resultSet.getString("email"));
        user.setRemark(resultSet.getString("remark"));
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      JdbcUtil.closeConnection(resultSet);
    }
    return null;
  }

  @Override
  public int login(String username, String password) {
    preparedStatement = JdbcUtil.getPreparedStatement(loginUserSql);
    ResultSet resultSet = null;
    try {
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        System.out.println("login done");
        return resultSet.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try{
        if (resultSet.next()) {
          JdbcUtil.closeConnection();
        } else {
          JdbcUtil.closeConnection(resultSet);
        }
      }catch (Exception e){
        e.printStackTrace();
      }
    }
    System.out.println("login fail");
    return 0;
  }


  private void addAndUpdateInsertPreparedStatementData(Object... param) throws SQLException {
    for (int i = 0; i < 5; i++) {
      preparedStatement.setString(i + 1, (String) param[i]);
    }
    preparedStatement.setInt(6,Integer.parseInt((String)param[5]));
    for (int i = 7; i < 13; i++) {
      preparedStatement.setString(i, (String) param[i - 1]);
    }
  }
}
