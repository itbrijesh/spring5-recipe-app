package com.brijesh.recipe.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.brijesh.recipe.domain.Recipe;
import com.brijesh.recipe.repositories.RecipeRepository;

@Service
public class ImageServiceImpl implements ImageService{

	private final RecipeRepository repository;
	
	public ImageServiceImpl(RecipeRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {
		
		Optional<Recipe> recipeOptional = repository.findById( recipeId );
				
		if( recipeOptional.isPresent() )
		{
			try {
				Byte[] bytes = new Byte[ file.getBytes().length ];
				int i = 0;
				
				for( byte b : file.getBytes() )
				{
					bytes[i++] = b;
				}
				
				Recipe recipe = recipeOptional.get();
				recipe.setImage( bytes );
				
				repository.save( recipe );
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Image File saved !");
	}

}
