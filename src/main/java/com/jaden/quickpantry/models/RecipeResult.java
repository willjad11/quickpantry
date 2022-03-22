package com.jaden.quickpantry.models;

import java.util.ArrayList;
import java.util.List;

public class RecipeResult {
    
    private String title;
	
    private String imageURL;
	
    private String recipeID;
    
    private List<String> usedIngredients;
    
    private List<String> missedIngredients;
    
    private List<String> unusedIngredients;
    
    public RecipeResult() {
    	this.usedIngredients = new ArrayList<String>();
    	this.missedIngredients = new ArrayList<String>();
    	this.unusedIngredients = new ArrayList<String>();
    	
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getRecipeID() {
		return recipeID;
	}

	public void setRecipeID(String recipeID) {
		this.recipeID = recipeID;
	}

	public List<String> getUsedIngredients() {
		return usedIngredients;
	}
	
	public Integer getAmountofUsedIngredients() {
		return usedIngredients.size();
	}
	

	public void setUsedIngredients(List<String> usedIngredients) {
		this.usedIngredients = usedIngredients;
	}
	
	public void addUsedIngredient(String usedIngredient) {
		this.usedIngredients.add(usedIngredient);
	}

	public List<String> getMissedIngredients() {
		return missedIngredients;
	}

	public void setMissedIngredients(List<String> missedIngredients) {
		this.missedIngredients = missedIngredients;
	}
	
	public void addMissedIngredient(String missedIngredient) {
		this.missedIngredients.add(missedIngredient);
	}

	public List<String> getUnusedIngredients() {
		return unusedIngredients;
	}

	public void setUnusedIngredients(List<String> unusedIngredients) {
		this.unusedIngredients = unusedIngredients;
	}
	
	public void addUnusedIngredient(String unusedIngredient) {
		this.unusedIngredients.add(unusedIngredient);
	}
	
}

