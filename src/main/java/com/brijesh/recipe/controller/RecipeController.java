package com.brijesh.recipe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brijesh.recipe.commands.RecipeCommand;
import com.brijesh.recipe.domain.Recipe;
import com.brijesh.recipe.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {

	RecipeService service;
	
	public RecipeController(RecipeService service) {
		super();
		this.service = service;
	}

	@GetMapping
	@RequestMapping( "recipe/show/{id}" )
	public String showRecipe(Model model, @PathVariable Long id )
	{
		log.info( "Showing Recipe for the id ::: " + id );
		model.addAttribute( "recipe" ,service.findById( id ) );
		
		return "recipe/show";
	}
	
	@GetMapping
	@RequestMapping( "recipe/new" )
	public String newRecipe( Model model )
	{
		model.addAttribute("recipe", new RecipeCommand() );
		return "recipe/recipeform";
	}
	
	@PostMapping
	@RequestMapping( "recipe" )
	public String saveOrUpdate(@ModelAttribute RecipeCommand command )
	{
		RecipeCommand recipe = service.saveRecipeCommand( command );
		
		return "redirect:/recipe/show/" + recipe.getId();
	}
	
	@GetMapping
	@RequestMapping( "recipe/{id}/update" )
	public String updateRecipe( @PathVariable Long id, Model model )
	{
		RecipeCommand recipe= service.findCommandById( id );
		log.info( "Recipe command object in updateRecipe : " + recipe );
		model.addAttribute( "recipe" , recipe);
		return "recipe/recipeform";
	}
	
	@GetMapping
	@RequestMapping( "recipe/{id}/delete" )
	public String deleteRecipe( @PathVariable Long id )
	{
		log.info( "Deleting Recipe for the ID : " + id );
		service.deleteRecipe( id );
		return "redirect:/index";
	}
}
