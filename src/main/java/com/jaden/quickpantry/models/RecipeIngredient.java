package com.jaden.quickpantry.models;

public class RecipeIngredient {
    
    private String name;
	
    private Double amount;
	
    private String unit;

    public RecipeIngredient() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}

