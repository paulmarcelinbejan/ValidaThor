package io.github.paulmarcelinbejan.toolbox.validathor.dfs.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import io.github.paulmarcelinbejan.toolbox.validathor.dfs.entities.level1.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Car {

	private Manufacturer manufacturer;
	
	private String model;
	
	private int numberOfSeats;
	
	private int horsePower;
	
	private LocalDate registrationDate;
	
	private List<Owner> owners;
	
	private Map<YEAR, CarStatus> carMaintenance;
	
}
