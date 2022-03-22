package com.jaden.quickpantry.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jaden.quickpantry.models.FavoriteRecipe;

@Repository
public interface FavoriteRecipeRepository extends CrudRepository<FavoriteRecipe, Long>{

    List<FavoriteRecipe> findAll();
}