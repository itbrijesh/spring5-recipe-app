package com.brijesh.recipe.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.brijesh.recipe.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	Optional<Category> findByDescription(String desc);
}
