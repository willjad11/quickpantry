package com.jaden.quickpantry.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.jaden.quickpantry.models.Ingredient;
import com.jaden.quickpantry.models.User;
import com.jaden.quickpantry.repositories.UserRepository;
import com.jaden.quickpantry.services.IngredientService;

@Controller
public class IngredientController {
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/pantry")
    public String pantry(@Valid @ModelAttribute("ingredient") Ingredient ingredient, BindingResult result, HttpSession session, Model model) {
		if (session.getAttribute("userId") == null) {
			 return "redirect:/";
		}
		User user = userRepository.findById((Long) session.getAttribute("userId")).get();
		model.addAttribute("userID", user.getId());
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("ingredientList", user.getIngredients());
		
		return "/pantry/ingredients.jsp";
    }
	
    @PostMapping("/ingredients/save")
    public String saveIngredient(@Valid @ModelAttribute("ingredient") Ingredient ingredient, BindingResult result, Model model, HttpSession session) {
    	if (session.getAttribute("userId") == null) {
			 return "redirect:/";
		}
    	
    	ingredientService.createIngredient(ingredient, result);
    	
        if (result.hasErrors()) {
        	User user = userRepository.findById((Long) session.getAttribute("userId")).get();
    		model.addAttribute("userName", user.getUserName());
    		model.addAttribute("ingredientList", user.getIngredients());
            return "/pantry/ingredients.jsp";
        }
        else {
            return "redirect:/pantry";
        }
    }
    
    @PutMapping(value="/ingredients/{id}")
    public String update(@Valid @ModelAttribute("ingredient") Ingredient ingredient, BindingResult result, Model model, HttpSession session) {
    	if (session.getAttribute("userId") == null) {
			 return "redirect:/";
		}
    	
    	ingredientService.updateIngredient(ingredient, result);
    	
        if (result.hasErrors()) {
        	User user = userRepository.findById((Long) session.getAttribute("userId")).get();
    		model.addAttribute("userName", user.getUserName());
    		model.addAttribute("ingredientList", user.getIngredients());
            return "/pantry/ingredients.jsp";
        } 
        
        return "redirect:/pantry";
    }
    
    @DeleteMapping("/ingredients/{id}")
    public String deleteIngredient(@PathVariable("id") Long id, HttpSession session) {
    	if (session.getAttribute("userId") == null) {
			 return "redirect:/";
		}
    	ingredientService.deleteIngredient(id);
        return "redirect:/pantry";
    }
    
}