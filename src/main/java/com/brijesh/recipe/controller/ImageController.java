package com.brijesh.recipe.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.HttpResource;

import com.brijesh.recipe.commands.RecipeCommand;
import com.brijesh.recipe.service.ImageService;
import com.brijesh.recipe.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ImageController {

	private final RecipeService recipeService;
	private final ImageService imageService;
	
	public ImageController(RecipeService recipeService, ImageService imageService) {
		super();
		this.recipeService = recipeService;
		this.imageService = imageService;
	}
	
	@GetMapping( "recipe/{recipeId}/image" )
	public String showImageForm( @PathVariable Long recipeId, Model model )
	{
		log.info("Showing image form !!!");
		model.addAttribute("recipe", recipeService.findById( recipeId ));
		return "recipe/imageuploadform";
	}
	
	@PostMapping( "recipe/{recipeId}/image" )
	public String uploadImageFile( @PathVariable Long recipeId, @RequestParam("imagefile") MultipartFile file, Model model )
	{
		log.info("Uploading an image file !!!");
		imageService.saveImageFile( recipeId, file );
		model.addAttribute("recipe", recipeService.findById( recipeId ));
		return "redirect:/recipe/show/" + recipeId;
	}
	
	@GetMapping( "/recipe/{recipeId}/recipeimage" )
	public void renderImageFromDatabase( @PathVariable Long recipeId, HttpServletResponse response )
	{
		RecipeCommand command = recipeService.findCommandById( recipeId );
		
		if( command.getImageBytes() == null )
		{
			log.info("*** NO IMAGE DATA FOUND *** ");
		}
		
		byte[] imageBytes = new byte[ command.getImageBytes().length ];
		
		int i = 0;
		for( byte b : command.getImageBytes() )
		{
			imageBytes[i++] = b;
		}
		
		response.setContentType("image/jpeg");
		InputStream is = new ByteArrayInputStream( imageBytes );
		
		try {
			IOUtils.copy( is, response.getOutputStream() );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
