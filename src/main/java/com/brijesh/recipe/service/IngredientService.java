package com.brijesh.recipe.service;

import com.brijesh.recipe.commands.IngredientCommand;
import com.brijesh.recipe.domain.Ingredient;

public interface IngredientService {

	IngredientCommand findIngredientByRecipeIdIngredientId( Long recipeId, Long ingredientId );
	IngredientCommand saveIngredientCommand( IngredientCommand command );
	void deleteIngredientByRecipeIdIngredientId( Long recipeId, Long ingredientId );
}
