package com.jaden.quickpantry.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.jaden.quickpantry.models.Ingredient;
import com.jaden.quickpantry.repositories.IngredientRepository;

@Service
public class IngredientService {

	@Autowired
	private IngredientRepository ingredientRepository;

	public List<Ingredient> allIngredients() {
		return ingredientRepository.findAll();
	}

	public Ingredient createIngredient(Ingredient ingredient, BindingResult result) {
		
		if (ingredient.getName().length() - ingredient.getName().replaceAll(" ", "").length() > 1) {
    	    result.rejectValue("name", "Matches", "Invalid ingredient!");
    	}
		
		if (!ingredient.getName().replaceAll(" ", "").matches("[a-zA-Z]+")) {
			result.rejectValue("name", "Matches", "Invalid ingredient!");
		}
		
		if (ingredientRepository.findByName(ingredient.getName()).isPresent()) {
    	    result.rejectValue("name", "Matches", "You already submitted that ingredient!");
    	}
		
		if(result.hasErrors()) {
    	    return null;
    	}
		
		return ingredientRepository.save(ingredient);
	}

	public Ingredient findIngredient(Long id) {
		Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
		if(optionalIngredient.isPresent()) {
	    	return optionalIngredient.get();
	    }
		else {
	    	return null;
	    }
	}
	
	public void deleteIngredient(Long id) {
		Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
		if(optionalIngredient.isPresent()) {
			ingredientRepository.deleteById(id);
	    }
	}
	
	public Ingredient updateIngredient(@Valid Ingredient ingredient, BindingResult result) {
		
		if(result.hasErrors()) {
    	    return null;
    	}
		
		Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredient.getId());
		if(optionalIngredient.isPresent()) {
	    	return ingredientRepository.save(ingredient);
	    }
		else {
	    	return null;
	    }
	}
	
}

