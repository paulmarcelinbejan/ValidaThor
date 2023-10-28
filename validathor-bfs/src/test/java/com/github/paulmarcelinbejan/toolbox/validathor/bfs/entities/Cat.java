package com.github.paulmarcelinbejan.toolbox.validathor.bfs.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Cat extends Animal {

	public Cat(String name, Short age) {
		super(AnimalType.CAT, name, age);
	}
	
}
