package com.ajobs.servlet;

import com.ajobs.dao.UserDao;
import com.ajobs.daoImp.UserDaoImp;
import com.ajobs.pojo.User;
import com.google.gson.Gson;
import com.wy.music.api.WyMusic;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet {

  private UserDao userDao = null;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    PrintWriter printWriter = null;
    switch (req.getPathInfo()) {
      case "/login":
        String number = "119 3 84 43 329 33392 4ejc 9201 543 32 ";
        printWriter = resp.getWriter();
        printWriter.write(number + login(req));
        break;
      case "/registry":
        printWriter = resp.getWriter();
        printWriter.write(registry(req));
        break;
      case "/queryInfo":
        resp.setCharacterEncoding("utf-8");
        printWriter = resp.getWriter();
        printWriter.write(queryUserInfo(req.getParameter("data")));
        break;
      case "/lineMusic":
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        printWriter = resp.getWriter();
        printWriter.write(lineMusic(req.getParameter("data")));
        printWriter.flush();
        break;
      case "/musicUrl":
        resp.setContentType("application/json");
        printWriter = resp.getWriter();
        printWriter.write(getMusicUrl(req.getParameter("id")));
        break;
    }
    if (printWriter != null) {
      printWriter.close();
    }
  }


  /**
   * 登陆的逻辑操作
   *
   * @param request
   * @return
   */
  private String login(HttpServletRequest request) {
    initUserDaoImp();
    int id = userDao.login(request.getParameter("username"), request.getParameter("password"));
    if (id == 0) {
      return "null";
    }
    return id + "";
  }

  private final String SPLITSTRING = " olinemusic ";

  /**
   * 注册的逻辑操作
   */
  private String registry(HttpServletRequest request) {
    initUserDaoImp();
    String[] dataArr = request.getParameter("data").split(SPLITSTRING);
    if (userDao.login(dataArr[0], dataArr[1]) == 1) {
      System.out.println("registry fail!!!");
      return "0";
    }
    String result = userDao.addUser(dataArr[0],
        dataArr[1],
        dataArr[2],
        dataArr[3],
        dataArr[4],
        dataArr[5],
        dataArr[6],
        dataArr[7],
        dataArr[8],
        dataArr[9],
        dataArr[10],
        dataArr[11]) + "";
    return result;
  }

  private String queryUserInfo(String id) {
    initUserDaoImp();
    User user = userDao.queryUser(Integer.parseInt(id));
    if (user != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder
          .append(user.getUsername()).append(SPLITSTRING)
          .append(user.getPic_url());
//          .append(user.getId()).append(SPLITSTRING)
//          .append(user.getId()).append(SPLITSTRING)
//          .append(user.getId()).append(SPLITSTRING)
//          .append(user.getId()).append(SPLITSTRING)
//          .append(user.getId()).append(SPLITSTRING)
//          .append(user.getId()).append(SPLITSTRING)
//          .append(user.getId()).append(SPLITSTRING)
//          .append(user.getId()).append(SPLITSTRING)
//          .append(user.getId()).append(SPLITSTRING)
      return stringBuilder.toString();
    }
    return "null";
  }

  private String getMusicUrl(String data) {
    return  new Gson().toJson(WyMusic.getMusicDetail(Integer.parseInt(data)));
  }
  /**
   * 音乐获取
   */
  private String lineMusic(String keyName) {
    return new Gson().toJson(WyMusic.searchMusic(keyName, 10, 1, 0));
  }

  private void initUserDaoImp() {
    if (userDao == null) {
      synchronized (this) {
        if (userDao == null) {
          userDao = new UserDaoImp();
        }
      }
    }
  }
}
