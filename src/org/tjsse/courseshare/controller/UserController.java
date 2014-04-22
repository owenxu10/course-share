package org.tjsse.courseshare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
	  public ModelAndView login(@RequestParam("username") String username,
			  					@RequestParam("password") String password) { 
		  
		  System.out.println(username+"/"+password);
	    return new ModelAndView();
	  }
	  
	  /* 
	   * Action: '/register', Method: POST
	   * register.
	   */
	  @RequestMapping(value = "/register", method = RequestMethod.POST)
	  public String  register( @RequestParam("username") String username,
			  						@RequestParam("password") String password,
			  						@RequestParam("email")    String email, 
			  						final RedirectAttributes redirectAttributes) { 
		  
		 // System.out.println(username+"/"+password+"/"+email);
		  User user = userService.registerUser(username,password,email);
		  
		  ModelMap userMap = new ModelMap();

		  redirectAttributes.addFlashAttribute("username", user.getUsername());
		  redirectAttributes.addFlashAttribute("id", user.getId());
		  
	      //return new ModelAndView("userMap", userMap);
		  //else
		  return "redirect:/problemset";
		  
	  }




}
