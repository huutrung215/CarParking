package com.trycoder.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trycoder.model.Parking;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

	List<Parking> findByCheckOutIsNull();
	
	List<Parking> findByCheckOutIsNotNull(Sort sort);
	
	List<Parking> findByMonthlyTicketIsNull();

}
