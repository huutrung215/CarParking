package com.trycoder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trycoder.model.Parking;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

	List<Parking> findByCheckOutIsNull();
	
	List<Parking> findByCheckOutIsNotNull();

}
