package com.paulmarcelinbejan.validator.test.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarStatus {

	private boolean problems;
	
	private String garageService;
	
}
