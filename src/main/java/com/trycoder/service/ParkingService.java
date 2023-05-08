package com.trycoder.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trycoder.model.Parking;
import com.trycoder.model.Position;
import com.trycoder.model.PositionStatus;
import com.trycoder.repository.ParkingRepository;
import com.trycoder.repository.PositionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ParkingService {
	@Autowired
	private ParkingRepository parkingRepo;
	
	public ParkingService(ParkingRepository parkingRepo) {
        this.parkingRepo = parkingRepo;
    }
	
	public List<Parking> getAllParkings() {
		return parkingRepo.findAll();
	}
	
	
	public static Long calculateParkingPrice(LocalDateTime checkIn, LocalDateTime checkOut) {
	    if (checkIn.isAfter(checkOut)) {
	        throw new IllegalArgumentException("End date time must be after start date time");
	    }
	    Long price = (long) 0;
	    LocalDateTime currentDateTime = checkIn;
	    while (currentDateTime.isBefore(checkOut)) {
	        int hourOfDay = currentDateTime.getHour();
	        if (hourOfDay >= 22 || hourOfDay < 5) { // qua đêm từ 22h đến 5h sáng hôm sau
	            price += 120000;
	            currentDateTime = currentDateTime.plusDays(1).withHour(5).withMinute(0).withSecond(0).withNano(0);
	        } else if (Duration.between(currentDateTime, checkOut).toHours() <= 2) {
                price += 15000;
                currentDateTime = currentDateTime.plusHours(1);
            } else if (Duration.between(currentDateTime, checkOut).toHours() <= 4) {
                price += 20000;
                currentDateTime = currentDateTime.plusHours(1);
            } else if (Duration.between(currentDateTime, checkOut).toHours() <= 6) {
                price += 25000;
                currentDateTime = currentDateTime.plusHours(1);
            } else {
                price += 50000;
                currentDateTime = currentDateTime.plusHours(1);
            }
	    }
	    return price;
	}

	
	 public Parking getParkingById(Long id) {
	        return parkingRepo.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy parkingId " + id));
	 }
	 
	 public List<Parking> findAllParkingsWithNullCheckOut() {
		return parkingRepo.findByCheckOutIsNull();	 
	 }
	 
	 @Autowired
	 private PositionRepository positionRepo;
	 
	 public void updateParkingCheckOut(Long id) {
	    Parking parking = parkingRepo.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy parkingId: " + id));
	    parking.setCheckOut(LocalDateTime.now());
	    Position position = parking.getPosition();
	    position.setStatus(PositionStatus.AVAILABLE);
	    positionRepo.save(position);
	    parkingRepo.save(parking);
	}
	 
	 public List<Parking> findParkingsBetween(LocalDateTime from, LocalDateTime to) {
		List<Parking> parkings = getAllParkingsWithNotNullCheckOut();
		List<Parking> result = new ArrayList<>();
		 for (Parking parking : parkings) {
			 if (parking.getCheckOut().isAfter(from) && parking.getCheckOut().isBefore(to)) {
		            result.add(parking);
	         }
		 }
	    return result;
	}
	 
	 public List<Parking> getAllParkingsWithNotNullCheckOut() {
		return parkingRepo.findByCheckOutIsNotNull();	 
	 }
}