package com.brijesh.recipe.repositories;

import org.springframework.data.repository.CrudRepository;

import com.brijesh.recipe.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
