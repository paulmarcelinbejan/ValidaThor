package com.paulmarcelinbejan.toolbox.validathor.dfs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Animal {

	private AnimalType animalType;
	
	private String name;
	
	private Short age;
	
}
