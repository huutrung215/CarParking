package com.trycoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trycoder.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

}
