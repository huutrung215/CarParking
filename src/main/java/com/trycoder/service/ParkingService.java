package com.trycoder.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trycoder.model.Car;
import com.trycoder.model.Parking;
import com.trycoder.model.ParkingPrice;
import com.trycoder.model.Position;
import com.trycoder.model.PositionStatus;
import com.trycoder.repository.CarRepository;
import com.trycoder.repository.ParkingPriceRepository;
import com.trycoder.repository.ParkingRepository;
import com.trycoder.repository.PositionRepository;

import io.micrometer.common.util.StringUtils;

import org.springframework.data.domain.Sort;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ParkingService {
	
	@Autowired
	private ParkingRepository parkingRepo;
	
	@Autowired
	private static ParkingPriceRepository parkingPriceRepo;
	
	@Autowired
	private CarRepository carRepo;
    
    public ParkingService(ParkingRepository parkingRepo, ParkingPriceRepository parkingPriceRepo) {
        this.parkingPriceRepo = parkingPriceRepo;
		this.parkingRepo = parkingRepo;
    }
	
	public List<Parking> getAllParkings() {
		return parkingRepo.findAll();
	}
	
	
	public static Long getPriceDay(int id) {
	    ParkingPrice parkingPrice = parkingPriceRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Id: " + id));
        return parkingPrice.getPriceDay();
	
	}
	
	public static Long getPriceNight(int id) {
	    ParkingPrice parkingPrice = parkingPriceRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy id: " + id));
	    return parkingPrice.getPriceNight();
	}
	
	public Parking createNomalParking(Parking parking) {
		
		 	parking.setCheckIn(LocalDateTime.now());
		 	
		 	//chuyển status position
		 	Position position = parking.getPosition();
		 	if (position == null) {
		        throw new IllegalArgumentException("Position cannot be null");
		    }

		 	position.setStatus(PositionStatus.OCCUPIED);
		 	positionRepo.save(position);
		 	
		    parking.setPosition(position);
		    
		    Parking savedParking = parkingRepo.save(parking);
		    if (savedParking == null) {
		        throw new RuntimeException("Failed to save parking");
		    }
		    
		    return savedParking;
	 } 
	

	public static Long calculateParkingPrice(LocalDateTime checkIn, LocalDateTime checkOut) {
	    if (checkIn.isAfter(checkOut)) {
	        throw new IllegalArgumentException("End date time must be after start date time");
	    }
	   
        Long fee2 = getPriceDay(1);
        Long fee1 = getPriceNight(1);
        
	    Long price = (long) 0;
	    LocalDateTime currentDateTime = checkIn;
	    while (currentDateTime.isBefore(checkOut)) {
	        int hourOfDay = currentDateTime.getHour();
	        if (hourOfDay >= 22 || hourOfDay < 5) { // qua đêm từ 22h đến 5h sáng hôm sau
	            price += fee1;
	            currentDateTime = currentDateTime.plusDays(1).withHour(5).withMinute(0).withSecond(0).withNano(0);
	        } else if (Duration.between(currentDateTime, checkOut).toHours() <= 2) {
                price += fee2;
                currentDateTime = currentDateTime.plusHours(1);
            } else if (Duration.between(currentDateTime, checkOut).toHours() <= 4) {
                price += (fee2 + 5000);
                currentDateTime = currentDateTime.plusHours(1);
            } else if (Duration.between(currentDateTime, checkOut).toHours() <= 6) {
                price += (fee2 + 10000);
                currentDateTime = currentDateTime.plusHours(1);
            } else {
                price += (fee2 + 35000);
                currentDateTime = currentDateTime.plusHours(1);
            }
	    }
	    return price;
	}

	
	 public Parking getParkingById(Long id) {
	        return parkingRepo.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy parkingId " + id));
	 }
	 
	 public ParkingPrice getParkingPriceById(int id) {
	        return parkingPriceRepo.findById(id)
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
		return parkingRepo.findByCheckOutIsNotNull(Sort.by(Sort.Direction.DESC, "parkingId"));	 
	 }
	 
	 public Long calParkingPriceNotMonTickDay() {
		 LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
	    LocalDateTime now = LocalDateTime.now();
	    List<Parking> parkings = parkingRepo.findByMonthlyTicketIsNull();
	    Long totalPrice = 0L;

	    for (Parking parking : parkings) {
	        if (parking.getCheckOut() != null && 
	        	parking.getCheckOut().isAfter(startOfDay) && parking.getCheckOut().isAfter(now)) {
	            totalPrice += parking.getParkingPrice();
	        }
	    }
	    return totalPrice;
	 }
	 
	 public Long calParkingPriceNotMonTickLastMonth() {
		 LocalDateTime currentTime = LocalDateTime.now();
	    YearMonth lastMonth = YearMonth.from(currentTime.minusMonths(1));
	    LocalDateTime firstDayOfMonth = lastMonth.atDay(1).atStartOfDay();
	    LocalDateTime lastDayOfMonth = lastMonth.atEndOfMonth().atTime(23, 59, 59);
	    List<Parking> parkings = parkingRepo.findByMonthlyTicketIsNull();
	    Long totalPrice = 0L;
	    for (Parking parking : parkings) {
	    	if (parking.getCheckOut() != null && parking.getCheckOut().isAfter(firstDayOfMonth) 
	    			&& parking.getCheckOut().isBefore(lastDayOfMonth)) {
	    		totalPrice += parking.getParkingPrice();
	    	}
	    }
		return totalPrice;
	 }
}
