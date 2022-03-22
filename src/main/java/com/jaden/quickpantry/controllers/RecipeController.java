package com.jaden.quickpantry.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaden.quickpantry.models.Category;
import com.jaden.quickpantry.models.FavoriteRecipe;
import com.jaden.quickpantry.models.Ingredient;
import com.jaden.quickpantry.models.Nutrient;
import com.jaden.quickpantry.models.RecipeIngredient;
import com.jaden.quickpantry.models.RecipeResult;
import com.jaden.quickpantry.models.User;
import com.jaden.quickpantry.repositories.UserRepository;
import com.jaden.quickpantry.services.FavoriteRecipeService;

@Controller
public class RecipeController {
	
	@Autowired
	private FavoriteRecipeService favoriteRecipeService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/recipes")
    public String recipes(HttpSession session, Model model) {
		if (session.getAttribute("userId") == null) {
			 return "redirect:/";
		}
		
		User user = userRepository.findById((Long) session.getAttribute("userId")).get();
		List<Ingredient> userIngredients = user.getIngredients();
		List<RecipeResult> recipeResults = new ArrayList<RecipeResult>();
		String URIConstructor = "";
		
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("userIngredients", userIngredients);
		
		if (user.getIngredients().size() >= 3) {
			
			for (Ingredient ingredient : userIngredients) {
				URIConstructor += ingredient.getName() + "%2C%20";
			}
			URIConstructor = URIConstructor.replaceAll("\\s","%20");
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?&number=8&ignorePantry=false&ranking=2&ingredients=" + URIConstructor))
					.header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
					.header("x-rapidapi-key", "")
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();
			HttpResponse<String> response;
			try {
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
				ObjectMapper mapper = new ObjectMapper();
				JsonNode recipeResponse = mapper.readTree(response.body());
				for (JsonNode recipe : recipeResponse) {
					RecipeResult recipeResult = new RecipeResult();
					
					recipeResult.setTitle(recipe.get("title").asText());
					recipeResult.setRecipeID(recipe.get("id").asText());
					recipeResult.setImageURL(recipe.get("image").asText());
					
					for (JsonNode usedIngredient : recipe.get("usedIngredients")) {
						recipeResult.addUsedIngredient(usedIngredient.get("name").asText());
					}
					
					for (JsonNode unusedIngredient : recipe.get("unusedIngredients")) {
						recipeResult.addUnusedIngredient(unusedIngredient.get("name").asText());
					}
					
					for (JsonNode missedIngredient : recipe.get("missedIngredients")) {
						recipeResult.addMissedIngredient(missedIngredient.get("name").asText());
					}
					recipeResults.add(recipeResult);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			recipeResults.sort(Comparator.comparing(RecipeResult::getAmountofUsedIngredients).reversed());
			model.addAttribute("recipeResults", recipeResults);
		}
		return "/recipes/dashboard.jsp";
    }
	
	@GetMapping("/recipes/{id}")
    public String showRecipe(@Valid @ModelAttribute("favoriteRecipe") FavoriteRecipe favoriteRecipe, @PathVariable("id") Integer id, Model model, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			 return "redirect:/";
		}
		User user = userRepository.findById((Long) session.getAttribute("userId")).get();
		Boolean isFollowing = user.getFavoriteRecipes().stream().filter(o -> o.getRecipeID().equals(id)).findFirst().isPresent();
		if (isFollowing) {
			model.addAttribute("dbid", user.getFavoriteRecipes().stream().filter(o -> o.getRecipeID().equals(id)).findFirst().get().getId());
		}
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("userId", session.getAttribute("userId"));
		model.addAttribute("userIngredients", user.getIngredients());
		model.addAttribute("isFollowing", isFollowing);
		List<Nutrient> nutrientList = new ArrayList<Nutrient>();
		List<RecipeIngredient> ingredientList = new ArrayList<RecipeIngredient>();
		List<String> analyzedInstructions = new ArrayList<String>();
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + id + "/information?includeNutrition=true"))
				.header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
				.header("x-rapidapi-key", "")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode recipeResponse = mapper.readTree(response.body());
			Iterator<String> iterator = recipeResponse.fieldNames();
		    iterator.forEachRemaining(e -> {
		    	if (e == "nutrition" && recipeResponse.get("nutrition").get("nutrients") != null) {
		    		for (JsonNode nutrient : recipeResponse.get("nutrition").get("nutrients")) {
		    			Nutrient tempNutrient = new Nutrient();
		    			tempNutrient.setName(nutrient.get("name").asText());
		    			tempNutrient.setAmount(nutrient.get("amount").asDouble());
		    			tempNutrient.setUnit(nutrient.get("unit").asText());
		    			tempNutrient.setPercentOfDailyNeeds(nutrient.get("percentOfDailyNeeds").asDouble());
		    			nutrientList.add(tempNutrient);
		    		}
		    	}
		    	if (e == "extendedIngredients" && recipeResponse.get("extendedIngredients") != null) {
		    		for (JsonNode ingredient : recipeResponse.get("extendedIngredients")) {
		    			RecipeIngredient tempIngredient = new RecipeIngredient();
		    			tempIngredient.setName(ingredient.get("name").asText());
		    			tempIngredient.setAmount(ingredient.get("amount").asDouble());
		    			tempIngredient.setUnit(ingredient.get("unit").asText());
		    			ingredientList.add(tempIngredient);
		    		}
		    	}
		    	if (e == "analyzedInstructions" && recipeResponse.get("analyzedInstructions").get(0) != null) {
		    		for (JsonNode instruction : recipeResponse.get("analyzedInstructions").get(0).get("steps")) {
		    			analyzedInstructions.add(instruction.get("step").asText());
		    		}
		    	}
		    	else {
		    		model.addAttribute(e, recipeResponse.get(e).asText());
		    	}
		    	model.addAttribute("nutrientList", nutrientList);
		    	model.addAttribute("ingredientList", ingredientList);
		    	model.addAttribute("analyzedInstructions", analyzedInstructions);
		    });

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "/recipes/recipeDetail.jsp";
    }
	
	@GetMapping("/recipes/saved")
    public String showSavedRecipes(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			 return "redirect:/";
		}
		User user = userRepository.findById((Long) session.getAttribute("userId")).get();
		List<FavoriteRecipe> unassignedRecipes = user.getFavoriteRecipes();
		List<FavoriteRecipe> recipesToRemove = new ArrayList<FavoriteRecipe>();
		model.addAttribute("userId", user.getId());
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("categoryList", user.getCategories());
		for (Category userCategory : user.getCategories()) {
			for (FavoriteRecipe userFavoriteRecipe : user.getFavoriteRecipes()) {
				if (userCategory.getFavoriteRecipes().contains(userFavoriteRecipe)) {
					recipesToRemove.add(userFavoriteRecipe);
				}
			}
		}
		unassignedRecipes.removeAll(recipesToRemove);
		model.addAttribute("favoriteRecipeList", unassignedRecipes);
		return "/recipes/savedRecipes.jsp";
	}
	
    @PostMapping("/recipes/save/{id}")
    public String saveRecipe(@Valid @ModelAttribute("favoriteRecipe") FavoriteRecipe favoriteRecipe, @PathVariable("id") Integer id, Model model, HttpSession session) {
    	if (session.getAttribute("userId") == null) {
			 return "redirect:/";
		}
    	
    	favoriteRecipeService.createFavoriteRecipe(favoriteRecipe);
        return "redirect:/recipes/" + id;
    }
    
    @DeleteMapping(value={"/recipes/unsave/{dbid}", "/recipes/unsave/{dbid}/{id}"})
    public String unsaveRecipe(@PathVariable("dbid") Long dbid, @PathVariable("id") Optional<Integer> id, HttpSession session) {
    	if (session.getAttribute("userId") == null) {
			 return "redirect:/";
		}
    	
    	favoriteRecipeService.deleteFavoriteRecipe(dbid);
    	
    	if (id.isPresent()) {
    		return "redirect:/recipes/" + id.get();
    	}
    	else {
    		return "redirect:/recipes/saved";
    	}
    }
    
}