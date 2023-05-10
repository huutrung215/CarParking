package com.trycoder.model;

import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ParkingPrice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Long priceDay;
	private Long priceNight;
	private Long priceMonth;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Long getPriceDay() {
		return priceDay;
	}
	public void setPriceDay(Long priceDay) {
		this.priceDay = priceDay;
	}
	public Long getPriceNight() {
		return priceNight;
	}
	public void setPriceNight(Long priceNight) {
		this.priceNight = priceNight;
	}
	public Long getPriceMonth() {
		return priceMonth;
	}
	public void setPriceMonth(Long priceMonth) {
		this.priceMonth = priceMonth;
	}
	
}
