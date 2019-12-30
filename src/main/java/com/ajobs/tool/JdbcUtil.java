package com.ajobs.tool;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtil {

  private static Connection connection;
  private static PreparedStatement preparedStatement;
  private static Properties properties;
  private static boolean LOAD_DRIVER = false;

  private static void loadDriver(String propertiesFile) {
    if (LOAD_DRIVER) {
      return;
    }
    LOAD_DRIVER = true;
    properties = new Properties();
    try {
      //properties.load(new FileInputStream("src/main/resources/jdbc.properties"));

      properties.load(new FileInputStream(
          Thread.currentThread().getContextClassLoader().getResource("/").getPath()
              + propertiesFile));

      Class.forName(properties.getProperty("driverClassName"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 得到数据库连接
   *
   * @param propertiesFile
   * @return
   */
  private static Connection getConnection(String propertiesFile) {
    if (connection != null) {
      return connection;
    }
    loadDriver(propertiesFile);
    try {
      //properties.load(new FileInputStream("src/main/resources/jdbc.properties"));

      properties.load(new FileInputStream(
          Thread.currentThread().getContextClassLoader().getResource("/").getPath()
              + propertiesFile));

      //判断是否含有用户名和密码的设置
      if (properties.getProperty("username") == null) {
        return connection = DriverManager.getConnection(properties.getProperty("url"));
      }
      return connection = DriverManager.getConnection(properties.getProperty("url"),
          properties.getProperty("username"),
          properties.getProperty("password"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 得到预处理语句
   *
   * @param sql
   * @return
   */
  public static PreparedStatement getPreparedStatement(String sql) {
    closePreparedStatement();
    /*
     *得到数据库连接
     */
    getConnection("jdbc.properties");
    try {
      return preparedStatement = connection.prepareStatement(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 关闭预处理语句
   */
  public static void closePreparedStatement() {
    try {
      if (preparedStatement != null) {
        preparedStatement.close();
        preparedStatement = null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 关闭连接
   */
  public static void closeConnection() {
    if (connection == null) {
      return;
    }
    try {
      closePreparedStatement();
      connection.close();
      connection = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 关闭连接的方法重构
   *
   * @param resultSet ：ResultSet
   */
  public static void closeConnection(ResultSet resultSet) {
    try {
      resultSet.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();
    }
  }


}
