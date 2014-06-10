package org.tjsse.courseshare.controller;

import java.util.List;

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
import org.tjsse.courseshare.bean.Orders;
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
		  System.out.println("id:"+ID);
		  if(ID==0)
		    return "error";
		  else{
			  System.out.println("id!=0");
			  request.getSession().setAttribute("username", username);
			  request.getSession().setAttribute("id", ID);
			  request.getSession().setMaxInactiveInterval(0); 
			  if(rememberMe=true) {
				  Cookie cusername = new Cookie("username",username);
				  cusername.setMaxAge(3600);
				  cusername.setPath("/");
				  response.addCookie(cusername);
			  }
			  return "success";   
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
		
		  boolean NoSame = userService.checkUser(username);
		  
		  
          if(NoSame==true){
        	  //new user
        	  User user = userService.registerUser(username,password,email);
        	  request.getSession().setAttribute("username", user.getUsername());
    		  request.getSession().setAttribute("id", user.getId());
    		  request.getSession().setMaxInactiveInterval(0); 
    		  Cookie cusername = new Cookie("username",user.getUsername());
    		  response.addCookie(cusername);
        	  return "redirect:/problemset";
          }
		 
          else 
		  return "false";
	  }

	  
	  /* 
	   * Action: '/register', Method: POST
	   * register.
	   */
	  @RequestMapping(value = "/modify", method = RequestMethod.POST)
	  public String modify(@RequestParam("userid") int userid, 
			  				@RequestParam("username") String username,
			  				@RequestParam("password") String password,
			  				@RequestParam("email")    String email, 
			  				HttpServletRequest request,HttpServletResponse response) { 
		  
		  	
			  
        	  User user = userService.modifyUser(userid,username,password,email);
    		  
        	  return "redirect:/user/info";
         
	  }

	  
	  @RequestMapping(value = "/info", method = RequestMethod.GET)
	  public ModelAndView info(HttpServletRequest request,HttpServletResponse response) {
		  ModelMap userMap = new ModelMap();
		 int userid =  (int) request.getSession().getAttribute("id");
		 String email;
		 String password;
		// String username;
		 //get user info by id
		 List<User> Users = userService.getUser(userid);
		 email = Users.get(0).getEmail();
		 password = Users.get(0).getPassword();
		// username = Users.get(0).getUsername();
		 userMap.addAttribute("ID", userid);
		 //userMap.addAttribute("username", username);
		 userMap.addAttribute("email", email);
		 userMap.addAttribute("password", password);
		 return new ModelAndView("info", userMap);
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
