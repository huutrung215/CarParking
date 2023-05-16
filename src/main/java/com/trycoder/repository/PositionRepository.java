package com.trycoder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trycoder.model.Position;
import com.trycoder.model.PositionCondition;
import com.trycoder.model.PositionStatus;

public interface PositionRepository extends JpaRepository<Position, Long> {
	List<Position> findByStatus(PositionStatus status);
	long countByStatus(PositionStatus status);
	long countByCondition(PositionCondition condition);
	Position findByPositionName(String name);
}
