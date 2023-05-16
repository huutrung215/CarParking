package com.trycoder.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.trycoder.model.Car;
import com.trycoder.model.MonthlyTicket;
import com.trycoder.model.Parking;
import com.trycoder.model.Position;

public class ParkingDto {
	private Long id;	
	private String parkingName;
	private LocalDateTime checkIn;
	private LocalDateTime checkOut;
	private Position position;
	private Long parkingPrice;
	private MonthlyTicket monthlyTicket;
	private Car car;
	private String numberPlates;
	
	public ParkingDto() {}
	
	public ParkingDto(Parking parking) {
		this.id = parking.getParkingId();
		this.parkingName = parking.getParkingName();
		this.checkIn = parking.getCheckIn();
		this.checkOut = parking.getCheckOut();
		this.position = parking.getPosition();
		this.parkingPrice = parking.getParkingPrice();
		this.monthlyTicket = parking.getMonthlyTicket();
		this.car = parking.getCar();
		this.numberPlates = parking.getNumberPlates();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public LocalDateTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDateTime checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDateTime getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDateTime checkOut) {
		this.checkOut = checkOut;
	}

	public Long getParkingPrice() {
		return parkingPrice;
	}

	public void setParkingPrice(Long parkingPrice) {
		this.parkingPrice = parkingPrice;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public MonthlyTicket getMonthlyTicket() {
		return monthlyTicket;
	}

	public void setMonthlyTicket(MonthlyTicket monthlyTicket) {
		this.monthlyTicket = monthlyTicket;
	}

	public String getNumberPlates() {
		return numberPlates;
	}

	public void setNumberPlates(String numberPlates) {
		this.numberPlates = numberPlates;
	}
	
}
