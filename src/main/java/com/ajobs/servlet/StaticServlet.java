package com.ajobs.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.servlets.DefaultServlet;


public class StaticServlet extends DefaultServlet {

//  protected String pathPrefix = "/WEB-INF";
//
//  public void init(ServletConfig config) throws ServletException {
//    super.init(config);
//    if (config.getInitParameter("pathPrefix") != null) {
//      pathPrefix = config.getInitParameter("pathPrefix");
//    }
//  }
//
//  @Override
//  protected String getRelativePath(HttpServletRequest req) {
//    System.out.println(super.getRelativePath(req));
//    return pathPrefix + super.getRelativePath(req);
//  }
}