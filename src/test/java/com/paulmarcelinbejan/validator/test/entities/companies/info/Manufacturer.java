package com.paulmarcelinbejan.validator.test.entities.companies.info;

import java.time.LocalDate;

import com.paulmarcelinbejan.validator.test.entities.companies.Headquarters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Manufacturer {

	private String name;
	
	private LocalDate foundation;
	
	private Headquarters headquarters;
	
}
