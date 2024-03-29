package com.brijesh.recipe.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Ingredient {

	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id;
	private String description;
	private BigDecimal amount;
	
	@ManyToOne
	private Recipe recipe;
	
	@OneToOne
	private UnitOfMeasure uom;
	
	public Ingredient(String ingredient, BigDecimal amount, UnitOfMeasure uom ) {
		this.description = ingredient;
		this.amount = amount;
		this.uom = uom; 
	}
	
	public Ingredient(String ingredient, BigDecimal amount, UnitOfMeasure uom, Recipe recipe ) {
		this.description = ingredient;
		this.amount = amount;
		this.uom = uom;
		this.recipe = recipe;
	}
	
	public Ingredient() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public UnitOfMeasure getUom() {
		return uom;
	}

	public void setUom(UnitOfMeasure uom) {
		this.uom = uom;
	}
	
}
