package com.trycoder.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Parking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long parkingId;	
	private String parkingName;
	private LocalDateTime checkIn;
	private LocalDateTime checkOut;
	private Long parkingPrice;
	
	@OneToOne
	@JoinColumn(name = "car_id")
	private Car car;

	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "position_id")
	    private Position position;
	
	 public void setCar(Car car) {
		this.car = car;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@OneToOne
	@JoinColumn(name = "ticket_id")
    private MonthlyTicket monthlyTicket;
	
	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
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

	public Car getCar() {
		return car;
	}

	public void setCars(Car car) {
		this.car = car;
	}

	public Position getPosition() {
		return position;
	}

	public void setPositions(Position position) {
		this.position = position;
	}

	public MonthlyTicket getMonthlyTicket() {
		return monthlyTicket;
	}

	public void setMonthlyTicket(MonthlyTicket monthlyTicket) {
		this.monthlyTicket = monthlyTicket;
	}
	 
}
