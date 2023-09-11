package com.paulmarcelinbejan.toolbox.validathor.dfs.entities;

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
