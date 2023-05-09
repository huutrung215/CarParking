package com.trycoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trycoder.model.ParkingPrice;

public interface ParkingPriceRepository extends JpaRepository<ParkingPrice, Integer> {
}
