package com.brijesh.recipe.service;

import java.util.Set;

import com.brijesh.recipe.commands.RecipeCommand;
import com.brijesh.recipe.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);
    
    RecipeCommand findCommandById(Long id);
    
    void deleteRecipe( Long id );
}
