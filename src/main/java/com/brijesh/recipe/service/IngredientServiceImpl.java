package com.brijesh.recipe.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.brijesh.recipe.commands.IngredientCommand;
import com.brijesh.recipe.converters.IngredientCommandToIngredient;
import com.brijesh.recipe.converters.IngredientToIngredientCommand;
import com.brijesh.recipe.domain.Ingredient;
import com.brijesh.recipe.domain.Recipe;
import com.brijesh.recipe.repositories.RecipeRepository;
import com.brijesh.recipe.repositories.UnitsOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private RecipeRepository repository;
	private IngredientToIngredientCommand ingredientCommad;
	private IngredientCommandToIngredient commandToIngredient; 
	private UnitsOfMeasureRepository unitOfMeasureRepository;
	
	public IngredientServiceImpl(RecipeRepository repository, IngredientToIngredientCommand ingredientCommad,
			IngredientCommandToIngredient commandToIngredient, UnitsOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.repository = repository;
		this.ingredientCommad = ingredientCommad;
		this.commandToIngredient = commandToIngredient;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public IngredientCommand findIngredientByRecipeIdIngredientId(Long recipeId, Long ingredientId) {

		log.info( "IngredientServiceImpl >> findIngredientByRecipeIdIngredientId " );
		
		Optional<Recipe> recipeOptional = repository.findById( recipeId );
		
		if( ! recipeOptional.isPresent() ) {
			log.info( "No Recipe found for the recipeId : " + recipeId );
			return null;
		}
		
		Recipe recipe = recipeOptional.get();

		Optional<IngredientCommand> ingredientCommand = recipe.getIngredients().stream()
				.filter( ingredient -> ingredient.getId().equals( ingredientId ) )
				.map( ingredientCommad::convert ).findFirst();
				// .map( ingredient -> ingredientCommad.convert( ingredient ) ).findFirst(); both are same
		
		 
		return ingredientCommand.get();
	}
	
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		
		log.info( "IngredientServiceImpl >> saveIngredientCommand ::::::" );
		
		Optional<Recipe> recipeOptional = repository.findById( command.getRecipeId() );
		
		if( !recipeOptional.isPresent() ){
			log.info( "No Recipe found for the recipeId : " + command.getRecipeId() );
			return command;
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<Ingredient> ingredientOptional =  recipe.getIngredients().stream()
									.filter( ingredient -> ingredient.getId().equals( command.getId() ) )
									.findFirst();
		
		if( ingredientOptional.isPresent() )
		{
			Ingredient ingredientFound = ingredientOptional.get();
			
			ingredientFound.setAmount( command.getAmount() );
			ingredientFound.setDescription( command.getDescription() );
			ingredientFound.setUom( unitOfMeasureRepository.findById( command.getUnitOfMeasure().getId() )
									.orElseThrow( ()-> new RuntimeException("UOM Not found") ) );
		}
		else {
			Ingredient ingredient = commandToIngredient.convert( command );
			ingredient.setRecipe( recipe );
			recipe.addIngredient( ingredient );
		}
		
		Recipe updatedRecipe = repository.save( recipe );
		
		// This will return the object when user will update the ingredient.
		// But will not return the object in case of new ingredient.
		Optional<Ingredient> updatedIngredientOptional = updatedRecipe.getIngredients()
				.stream()
				.filter( ingredients -> ingredients.getId().equals( command.getId() ) )
				.findFirst();
		
		// This will be used to return the object when user will add new ingredient
		// Check all the properties of new object vs existing ones.
		// Not the best method but will work for most of
		if( !updatedIngredientOptional.isPresent() )
		{
			updatedIngredientOptional = updatedRecipe.getIngredients()
					.stream()
					.filter( ingredient -> ingredient.getAmount().equals( command.getAmount()) )
					.filter( ingredient -> ingredient.getDescription().equals( command.getDescription()) )
					.filter( ingredient -> ingredient.getUom().getId().equals( command.getUnitOfMeasure().getId() ) )
					.findFirst();
		}
		
		return ingredientCommad.convert( updatedIngredientOptional.get() );
		
	}

	public void deleteIngredientByRecipeIdIngredientId( Long recipeId, Long ingredientId )
	{
		Optional<Recipe> recipeOptional = repository.findById( recipeId );
		
		if( recipeOptional.isPresent() )
		{
			Recipe recipe = recipeOptional.get();
			
			Ingredient deleteMe = recipe.getIngredients()
									.stream()
									.filter( ingredient -> ingredient.getId().equals( ingredientId ) )
									.findFirst()
									.get();
			deleteMe.setRecipe( null );
			
			recipe.getIngredients().remove( deleteMe );
			
			repository.save( recipe );
			
			log.info("Ingredient with id " + ingredientId + " has been deleted.");
		}
		else
		{
			log.info( "No recipe exists for id " + recipeId );
		}
	}
	 
}
