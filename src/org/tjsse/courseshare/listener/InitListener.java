package org.tjsse.courseshare.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.tjsse.courseshare.util.Config;

public class InitListener implements ServletContextListener {

  @Override
  public void contextDestroyed(ServletContextEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    InetAddress addr = null;
    int port = 8080;
    try {
      addr = InetAddress.getLocalHost();
      Config.SERVER_URL = String.format("http://%s:%d", addr.getHostAddress(), port);
    } catch (UnknownHostException e) {
      Config.SERVER_URL = "http://localhost:8080";
    }
    System.out.println(Config.SERVER_URL);
  }

}
