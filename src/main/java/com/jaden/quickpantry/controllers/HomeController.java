package com.jaden.quickpantry.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jaden.quickpantry.models.LoginUser;
import com.jaden.quickpantry.models.User;
import com.jaden.quickpantry.services.UserService;

@Controller
public class HomeController {
 
	 @Autowired
	 private UserService userServ;
 
	 @GetMapping("/")
	 public String index(HttpSession session, Model model) {
		 
		 if (session.getAttribute("userId") != null) {
			 return "redirect:/recipes";
		 }
	     model.addAttribute("newUser", new User());
	     model.addAttribute("newLogin", new LoginUser());
	     return "index.jsp";
	 }
	 
	 @GetMapping("/logout")
	 public String logout(HttpSession session) {
		 session.removeAttribute("userId");
		 session.invalidate();
	     return "redirect:/";
	 }
	 
	 @PostMapping("/register")
	 public String register(@Valid @ModelAttribute("newUser") User newUser, 
	         BindingResult result, Model model, HttpSession session) {
		 
		 User user = userServ.register(newUser, result);
	     
	     if(result.hasErrors()) {
	         model.addAttribute("newLogin", new LoginUser());
	         return "index.jsp";
	     }
	     
	     session.setAttribute("userId", user.getId());
	 
	     return "redirect:/recipes";
	 }
	 
	 @PostMapping("/login")
	 public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
	         BindingResult result, Model model, HttpSession session) {
	     
	     User user = userServ.login(newLogin, result);
	 
	     if(result.hasErrors()) {
	         model.addAttribute("newUser", new User());
	         return "index.jsp";
	     }
	     
	     session.setAttribute("userId", user.getId());
	 
	     return "redirect:/recipes";
	 }
 
}
