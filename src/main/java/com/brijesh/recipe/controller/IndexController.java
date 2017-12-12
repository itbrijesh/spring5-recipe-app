package com.brijesh.recipe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brijesh.recipe.repositories.CategoryRepository;
import com.brijesh.recipe.repositories.UnitsOfMeasureRepository;
import com.brijesh.recipe.service.RecipeService;

@Controller
public class IndexController {

	/*private CategoryRepository categoryRepo;
	private UnitsOfMeasureRepository uomRepo;
	
	public IndexController(CategoryRepository categoryRepo, UnitsOfMeasureRepository uomRepo) {
		super();
		this.categoryRepo = categoryRepo;
		this.uomRepo = uomRepo;
	}*/

	private final RecipeService service;
	
	public IndexController(RecipeService service) {
		super();
		this.service = service;
	}

	@RequestMapping({"","/","/index"})
	public String getIndex(Model model)
	{
		System.out.println("Index page...!");
		
		//System.out.println("Category Id : " + categoryRepo.findByDescription( "Mexican" ).get().getId() );
		//System.out.println("UOM Id : " + uomRepo.findByDescription( "Cup" ).get().getId() );
		
		model.addAttribute("recipes",service.getRecipes());
		
		return "index";
	}
}
