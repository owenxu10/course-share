package org.tjsse.courseshare.controller;

import javax.servlet.http.HttpServletRequest;

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
		  						HttpServletRequest request) { 
		  
		  System.out.println(username+"/"+password);
		  int ID = userService.loginUser(username,password);
		  if(ID==0)
		    return "false";
		  else{
			  request.getSession().setAttribute("username", username);
			  request.getSession().setAttribute("id", ID);
			  System.out.println("go to the problemset");
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
			  						HttpServletRequest request) { 
		  
		  System.out.println(username+"/"+password+"/"+email);
		  User user = userService.registerUser(username,password,email);
		  boolean NoSame = userService.checkUser(username);
		  request.getSession().setAttribute("username", user.getUsername());
		  request.getSession().setAttribute("id", user.getId());
		  
          if(NoSame==true)
		  return "redirect:/problemset";
          else 
		  return "false";
	  }




}
