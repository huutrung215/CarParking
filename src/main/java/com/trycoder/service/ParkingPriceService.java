package com.trycoder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.trycoder.model.ParkingPrice;
import com.trycoder.repository.ParkingPriceRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ParkingPriceService {
	@Autowired
	private ParkingPriceRepository parkingPriceRepo;
	
	public ParkingPriceService(ParkingPriceRepository parkingPriceRepo) {
        this.parkingPriceRepo = parkingPriceRepo;
    }
	
	public ParkingPrice getParkingPriceById(int id) {
        return parkingPriceRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy id " + id));
} 
	
	public ParkingPrice updateParkingPrice(int id, ParkingPrice newParkingPrice) throws ConfigDataResourceNotFoundException {
		ParkingPrice parkingPrice = getParkingPriceById(id);
		parkingPrice.setPriceDay(newParkingPrice.getPriceDay());
		parkingPrice.setPriceNight(newParkingPrice.getPriceNight());
		return parkingPriceRepo.save(parkingPrice);
	}
}
