package org.tjsse.courseshare.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.tjsse.courseshare.service.UserService;
import org.tjsse.courseshare.bean.User;


@Controller
@RequestMapping("/user")
public class UserController {
	  @Autowired
	  private UserService userService;

	  /* 
	   * Action: '/index', Method: GET
	   * Default index page.
	   */
	  @RequestMapping(value = "", method = RequestMethod.GET)
	  public ModelAndView index() {
		  ModelMap userMap = new ModelMap();
		  userMap.addAttribute("ID", 0);
		  return new ModelAndView("user", userMap);
	  }
	 
	 
	 /* 
	   * Action: '/login', Method: POST
	   * login.
	   */
	  @RequestMapping(value = "/login", method = RequestMethod.POST)
	  @ResponseBody
	  public String login(@RequestParam("username") String username,
			  					@RequestParam("password") String password, 
			  					@RequestParam("rememberMe") boolean rememberMe, 
		  						HttpServletRequest request,HttpServletResponse response) { 
		  
		  System.out.println(username+"/"+password+"   "+rememberMe);
		  int ID = userService.loginUser(username,password);
		  request.getSession().setAttribute("username", username);
		  request.getSession().setAttribute("id", ID);
		  if(ID==0)
		    return "false";
		  else{
			  if(rememberMe=true) {
				  Cookie cusername = new Cookie("username",username);
				  cusername.setMaxAge(3600);
				  cusername.setPath("/");
				  response.addCookie(cusername);
			  }
				 
			  return "redirect:/problemset";   
		  }
	  }
	  
	  /* 
	   * Action: '/register', Method: POST
	   * register.
	   */
	  @RequestMapping(value = "/register", method = RequestMethod.POST)
	  public String register( @RequestParam("username") String username,
			  						@RequestParam("password") String password,
			  						@RequestParam("email")    String email, 
			  						HttpServletRequest request,HttpServletResponse response) { 
		  
		  System.out.println(username+"/"+password+"/"+email);
		  User user = userService.registerUser(username,password,email);
		  boolean NoSame = userService.checkUser(username);
		  request.getSession().setAttribute("username", user.getUsername());
		  request.getSession().setAttribute("id", user.getId());
		  Cookie cusername = new Cookie("username",user.getUsername());
		  
		  response.addCookie(cusername);
		  
          if(NoSame==true)
		  return "redirect:/problemset";
          else 
		  return "false";
	  }

	  /* 
	   * Action: '/logout', Method: POST
	   * register.
	   */
	  @RequestMapping(value = "/logout", method = RequestMethod.POST)
	  public String logout(HttpServletRequest request) { 
		  
		//delete all userinfo in the session and store the logout in the session
		  request.getSession().invalidate();
		  request.getSession().setAttribute("logout", "logout");
		  
		  return "redirect:/user";
	  }



}
