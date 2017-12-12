package com.brijesh.recipe.service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.brijesh.recipe.commands.UnitOfMeasureCommand;
import com.brijesh.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.brijesh.recipe.repositories.UnitsOfMeasureRepository;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	UnitsOfMeasureRepository repository;
	UnitOfMeasureToUnitOfMeasureCommand command;
	
	public UnitOfMeasureServiceImpl(UnitsOfMeasureRepository repository, UnitOfMeasureToUnitOfMeasureCommand command) {
		super();
		this.repository = repository;
		this.command = command;
	}

	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {
		
		Set<UnitOfMeasureCommand> set = StreamSupport.stream( repository.findAll().spliterator() , false )
		             .map( command::convert )
		             .collect(Collectors.toSet());
		return set;
	}

}
