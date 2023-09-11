package com.paulmarcelinbejan.toolbox.validathor.bfs.entities.level1;

import java.time.LocalDate;

import com.paulmarcelinbejan.toolbox.validathor.bfs.entities.level1.level2.Headquarters;

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
