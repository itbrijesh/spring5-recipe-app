package com.brijesh.recipe.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.brijesh.recipe.domain.UnitOfMeasure;

public interface UnitsOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

	Optional<UnitOfMeasure> findByDescription(String desc);
	
}
