package com.github.paulmarcelinbejan.toolbox.validathor.bfs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Owner {

	private String name;
	
	private String surname;
	
	private Short age;
	
	private Animal animal;
	
}
