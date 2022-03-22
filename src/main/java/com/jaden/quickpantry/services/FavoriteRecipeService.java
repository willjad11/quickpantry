package com.jaden.quickpantry.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaden.quickpantry.models.FavoriteRecipe;
import com.jaden.quickpantry.repositories.FavoriteRecipeRepository;

@Service
public class FavoriteRecipeService {
 
	@Autowired
    private FavoriteRecipeRepository favoriteRecipeRepository;

	public List<FavoriteRecipe> allFavoriteRecipes() {
		return favoriteRecipeRepository.findAll();
	}

	public FavoriteRecipe createFavoriteRecipe(FavoriteRecipe favoriteRecipe) {
		return favoriteRecipeRepository.save(favoriteRecipe);
	}

	public FavoriteRecipe findFavoriteRecipe(Long id) {
		Optional<FavoriteRecipe> optionalFavoriteRecipe = favoriteRecipeRepository.findById(id);
		if(optionalFavoriteRecipe.isPresent()) {
	    	return optionalFavoriteRecipe.get();
	    }
		else {
	    	return null;
	    }
	}
	
	public void deleteFavoriteRecipe(Long id) {
		Optional<FavoriteRecipe> optionalFavoriteRecipe = favoriteRecipeRepository.findById(id);
		if(optionalFavoriteRecipe.isPresent()) {
			favoriteRecipeRepository.deleteById(id);
	    }
	}
	
}

