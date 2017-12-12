package com.brijesh.recipe.service;

import java.util.Set;

import com.brijesh.recipe.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listAllUoms();
	
}
