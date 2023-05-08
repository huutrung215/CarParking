package com.trycoder.dto;

import com.trycoder.model.Car;

public class CarDto {
	private Long id;	
	private String numberPlates;
	private String carName;
	private String color;
	private String description;
	
	public CarDto() {
		
	}
	
	public CarDto(Car car) {
		this.id = car.getCarId();
		this.carName = car.getCarName();
		this.numberPlates = car.getNumberPlates();
		this.color = car.getColor();
		this.description = car.getDescription();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumberPlates() {
		return numberPlates;
	}

	public void setNumberPlates(String numberPlates) {
		this.numberPlates = numberPlates;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
