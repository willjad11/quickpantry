package com.jaden.quickpantry.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.jaden.quickpantry.models.Category;
import com.jaden.quickpantry.models.FavoriteRecipe;
import com.jaden.quickpantry.models.User;
import com.jaden.quickpantry.repositories.UserRepository;
import com.jaden.quickpantry.services.CategoryService;

@Controller
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	UserRepository userRepository;
	
    @PostMapping("/category/create")
    public String saveCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model, HttpSession session) {
    	if (session.getAttribute("userId") == null) {
			 return "redirect:/";
		}
    	
    	categoryService.createCategory(category, result);
    	
        if (result.hasErrors()) {
        	User user = userRepository.findById((Long) session.getAttribute("userId")).get();
    		model.addAttribute("userId", user.getId());
    		model.addAttribute("userName", user.getUserName());
    		model.addAttribute("favoriteRecipeList", user.getFavoriteRecipes());
    		model.addAttribute("categoryList", user.getCategories());
            return "/recipes/savedRecipes.jsp";
        }
        else {
            return "redirect:/recipes/saved";
        }
    }
    
    @PutMapping(value="/category/{id}")
    public String updateCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, @PathVariable("id") Long id, Model model, HttpSession session) {
    	if (session.getAttribute("userId") == null) {
			 return "redirect:/";
		}
    	Category userCategory = categoryService.findCategory(id);
    	List<FavoriteRecipe> userCategoryRecipes = userCategory.getFavoriteRecipes();
    	
        if (result.hasErrors()) {
        	User user = userRepository.findById((Long) session.getAttribute("userId")).get();
    		List<FavoriteRecipe> unassignedRecipes = user.getFavoriteRecipes();
    		List<FavoriteRecipe> recipesToRemove = new ArrayList<FavoriteRecipe>();
    		model.addAttribute("userId", user.getId());
    		model.addAttribute("userName", user.getUserName());
    		model.addAttribute("categoryList", user.getCategories());
    		for (Category tempCategory : user.getCategories()) {
    			for (FavoriteRecipe userFavoriteRecipe : user.getFavoriteRecipes()) {
    				if (tempCategory.getFavoriteRecipes().contains(userFavoriteRecipe)) {
    					recipesToRemove.add(userFavoriteRecipe);
    				}
    			}
    		}
    		unassignedRecipes.removeAll(recipesToRemove);
    		model.addAttribute("favoriteRecipeList", unassignedRecipes);
            return "/recipes/savedRecipes.jsp";
        }
        
        if (!category.getName().equals(userCategory.getName())) {
    		userCategory.setName(category.getName());
    		categoryService.updateCategory(userCategory, result);
    	}
    	else if (userCategoryRecipes.contains(category.getFavoriteRecipes().get(0))) {
    		userCategoryRecipes.remove(category.getFavoriteRecipes().get(0));
			category.setFavoriteRecipes(userCategoryRecipes);
			categoryService.updateCategory(category, result);
    	}
    	else if (!userCategoryRecipes.contains(category.getFavoriteRecipes().get(0))){
    		userCategoryRecipes.add(category.getFavoriteRecipes().get(0));
    		category.setFavoriteRecipes(userCategoryRecipes);
        	categoryService.updateCategory(category, result);
    	}
    	
        
        return "redirect:/recipes/saved";
    }
    
    @DeleteMapping("/category/{id}")
    public String deleteCategory(@PathVariable("id") Long id, HttpSession session) {
    	if (session.getAttribute("userId") == null) {
			 return "redirect:/";
		}
    	categoryService.deleteCategory(id);
        return "redirect:/recipes/saved";
    }
    
}