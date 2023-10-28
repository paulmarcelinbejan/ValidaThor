package com.github.paulmarcelinbejan.toolbox.validathor.bfs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Animal {

	private AnimalType animalType;
	
	private String name;
	
	private Short age;
	
}
