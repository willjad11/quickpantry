package com.jaden.quickpantry.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.jaden.quickpantry.models.Category;
import com.jaden.quickpantry.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> allCategorys() {
		return categoryRepository.findAll();
	}

	public Category createCategory(Category category, BindingResult result) {
		
		if(result.hasErrors()) {
    	    return null;
    	}
		
		return categoryRepository.save(category);
	}

	public Category findCategory(Long id) {
		Optional<Category> optionalCategory = categoryRepository.findById(id);
		if(optionalCategory.isPresent()) {
	    	return optionalCategory.get();
	    }
		else {
	    	return null;
	    }
	}
	
	public void deleteCategory(Long id) {
		Optional<Category> optionalCategory = categoryRepository.findById(id);
		if(optionalCategory.isPresent()) {
			categoryRepository.deleteById(id);
	    }
	}
	
	public Category updateCategory(@Valid Category category, BindingResult result) {
		
		if(result.hasErrors()) {
    	    return null;
    	}
		
		Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
		if(optionalCategory.isPresent()) {
	    	return categoryRepository.save(category);
	    }
		else {
	    	return null;
	    }
	}
	
}

