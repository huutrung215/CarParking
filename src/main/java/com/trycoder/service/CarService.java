package com.trycoder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trycoder.model.Car;
import com.trycoder.repository.CarRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CarService {
	@Autowired
	private CarRepository carRepo;
	
	public CarService(CarRepository carRepo) {
		this.carRepo = carRepo;
	}
	
	public Car getCarById(Long id) {
		return carRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Không tìm thấy xe id " + id));
	}	
}
