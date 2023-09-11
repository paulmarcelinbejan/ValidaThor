package com.paulmarcelinbejan.validator.test.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.paulmarcelinbejan.validator.test.entities.Car;
import com.paulmarcelinbejan.validator.test.entities.CarStatus;
import com.paulmarcelinbejan.validator.test.entities.Owner;
import com.paulmarcelinbejan.validator.test.entities.YEAR;
import com.paulmarcelinbejan.validator.test.entities.companies.Headquarters;
import com.paulmarcelinbejan.validator.test.entities.companies.info.Manufacturer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectValorizator {

	public static Car populateCar() {
		Manufacturer ferrari = new Manufacturer("Ferrari", LocalDate.of(1947, 3, 12), new Headquarters("Maranello"));
		List<Owner> owners = new ArrayList<>();
		owners.add(new Owner("Mario", "Rossi", (short)25));
		owners.add(new Owner("Francesco", "Totti", (short)45));
		Map<YEAR, CarStatus> carMaintenance = new EnumMap<>(YEAR.class);
		carMaintenance.put(YEAR._1976, new CarStatus(false, "Kingwood"));
		carMaintenance.put(YEAR._1977, new CarStatus(true, "Windsor Garage"));
		carMaintenance.put(YEAR._1978, new CarStatus(false, "Monster Garage"));
		carMaintenance.put(YEAR._1979, new CarStatus(true, "Street Service LTD"));
		carMaintenance.put(YEAR._1980, new CarStatus(false, "Street Service LTD"));
		Car ferrari_308GTB = new Car(ferrari, "308 GTB", 2, 255, LocalDate.of(1976, 1, 1), owners, carMaintenance);
		return ferrari_308GTB;
	}
	
	public static Car populateCarNullString() {
		Manufacturer ferrari = new Manufacturer("Ferrari", LocalDate.of(1947, 3, 12), new Headquarters(null));
		List<Owner> owners = new ArrayList<>();
		owners.add(new Owner(null, "Rossi", (short)25));
		owners.add(new Owner(null, "Totti", (short)45));
		Map<YEAR, CarStatus> carMaintenance = new EnumMap<>(YEAR.class);
		carMaintenance.put(YEAR._1976, new CarStatus(false, "Kingwood"));
		carMaintenance.put(YEAR._1977, new CarStatus(true, "Windsor Garage"));
		carMaintenance.put(YEAR._1978, new CarStatus(false, "Monster Garage"));
		carMaintenance.put(YEAR._1979, new CarStatus(true, "Street Service LTD"));
		carMaintenance.put(YEAR._1980, new CarStatus(false, null));
		Car ferrari_308GTB = new Car(ferrari, "308 GTB", 2, 255, LocalDate.of(1976, 1, 1), owners, carMaintenance);
		return ferrari_308GTB;
	}
	
	public static Car populateCarEmptyString() {
		Manufacturer ferrari = new Manufacturer("Ferrari", LocalDate.of(1947, 3, 12), new Headquarters(""));
		List<Owner> owners = new ArrayList<>();
		owners.add(new Owner("", "Rossi", (short)25));
		owners.add(new Owner("", "Totti", (short)45));
		Map<YEAR, CarStatus> carMaintenance = new EnumMap<>(YEAR.class);
		carMaintenance.put(YEAR._1976, new CarStatus(false, "Kingwood"));
		carMaintenance.put(YEAR._1977, new CarStatus(true, "Windsor Garage"));
		carMaintenance.put(YEAR._1978, new CarStatus(false, ""));
		carMaintenance.put(YEAR._1979, new CarStatus(true, "Street Service LTD"));
		carMaintenance.put(YEAR._1980, new CarStatus(false, "Street Service LTD"));
		Car ferrari_308GTB = new Car(ferrari, "308 GTB", 2, 255, LocalDate.of(1976, 1, 1), owners, carMaintenance);
		return ferrari_308GTB;
	}
	
}
