package com.github.paulmarcelinbejan.toolbox.validathor.bfs.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Dog extends Animal {

	public Dog(String name, Short age) {
		super(AnimalType.DOG, name, age);
	}
	
}
