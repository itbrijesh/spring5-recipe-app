package com.brijesh.recipe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brijesh.recipe.commands.IngredientCommand;
import com.brijesh.recipe.commands.RecipeCommand;
import com.brijesh.recipe.commands.UnitOfMeasureCommand;
import com.brijesh.recipe.service.IngredientService;
import com.brijesh.recipe.service.RecipeService;
import com.brijesh.recipe.service.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

	RecipeService service;
	IngredientService ingredientService;
	UnitOfMeasureService uomService;
	
	public IngredientController(RecipeService service, IngredientService ingredientService,
			UnitOfMeasureService uomService) {
		super();
		this.service = service;
		this.ingredientService = ingredientService;
		this.uomService = uomService;
	}

	@GetMapping
	@RequestMapping( "/recipe/{recipeId}/ingredients" )
	public String showIngredients(Model model, @PathVariable Long recipeId )
	{
		model.addAttribute("recipe", service.findCommandById( recipeId ));
		return "recipe/ingredient/list";
	}
	
	@GetMapping
	@RequestMapping( "/recipe/{recipeId}/ingredient/{ingredientId}/show" )
	public String showIngredientDetails( Model model, @PathVariable Long recipeId, @PathVariable Long ingredientId)
	{
		model.addAttribute( "ingredient" , ingredientService.findIngredientByRecipeIdIngredientId(recipeId, ingredientId) );
		return "recipe/ingredient/show";
	}
	
	@GetMapping
	@RequestMapping( "/recipe/{recipeId}/ingredient/{ingredientId}/update" )
	public String updateIngredient( Model model, @PathVariable Long recipeId, @PathVariable Long ingredientId)
	{
		model.addAttribute( "ingredient", ingredientService.findIngredientByRecipeIdIngredientId( recipeId, ingredientId) );
		model.addAttribute( "uoms", uomService.listAllUoms() );
		
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping
	@RequestMapping( "/recipe/{recipeId}/ingredient" )
	public String saveOrUpdate( @ModelAttribute IngredientCommand command, Model model )
	{
		IngredientCommand commandSaved = ingredientService.saveIngredientCommand( command );
		
		model.addAttribute( "ingredient", commandSaved );
		//return "recipe/" + commandSaved.getRecipeId() + "/ingredient/"+ commandSaved.getId() +"/show";
		return "recipe/ingredient/show";
	}
	
	@GetMapping
	@RequestMapping( "/recipe/{recipeId}/ingredient/new" )
	public String newIngredient( @PathVariable Long recipeId, Model model )
	{
		RecipeCommand recipeCommand = service.findCommandById( recipeId );
		
		IngredientCommand ingredient = new IngredientCommand();
		ingredient.setRecipeId( recipeId );
		ingredient.setUnitOfMeasure( new UnitOfMeasureCommand());
		recipeCommand.getIngredients().add( ingredient );
		
		
		model.addAttribute( "ingredient" , ingredient );
		model.addAttribute( "uoms", uomService.listAllUoms() );
		
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping
	@RequestMapping( "/recipe/{recipeId}/ingredient/{ingredientId}/delete" )
	public String deleteIngredient( @PathVariable Long recipeId, @PathVariable Long ingredientId, Model model )
	{
		ingredientService.deleteIngredientByRecipeIdIngredientId( recipeId, ingredientId );
		//model.addAttribute( "recipe" , service.findById( recipeId ));
		return "redirect:/recipe/"+recipeId+"/ingredients";
	}
}
