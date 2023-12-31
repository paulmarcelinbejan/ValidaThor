package io.github.paulmarcelinbejan.toolbox.validathor.bfs.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.Car;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.CarStatus;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.Cat;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.Dog;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.Owner;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.YEAR;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.level1.Manufacturer;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.level1.level2.Headquarters;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectValorizator {

	public static Car populateCar() {
		Manufacturer ferrari = new Manufacturer("Ferrari", LocalDate.of(1947, 3, 12), new Headquarters("Maranello"));
		List<Owner> owners = new ArrayList<>();
		owners.add(new Owner("Mario", "Rossi", (short)25, new Cat("Miao", (short)1)));
		owners.add(new Owner("Francesco", "Totti", (short)45, new Dog("Bau", (short)1)));
		Map<YEAR, CarStatus> carMaintenance = new EnumMap<>(YEAR.class);
		carMaintenance.put(YEAR._1976, new CarStatus(false, "Kingwood"));
		carMaintenance.put(YEAR._1977, new CarStatus(true, "Windsor Garage"));
		carMaintenance.put(YEAR._1978, new CarStatus(false, "Monster Garage"));
		carMaintenance.put(YEAR._1979, new CarStatus(true, "Street Service LTD"));
		carMaintenance.put(YEAR._1980, new CarStatus(false, "Street Service LTD"));
		Car ferrari_308GTB = new Car(ferrari, "308 GTB", 2, 255, LocalDate.of(1976, 1, 1), owners, carMaintenance);
		return ferrari_308GTB;
	}
	
}
