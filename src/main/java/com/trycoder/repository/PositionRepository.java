package com.trycoder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trycoder.model.Position;
import com.trycoder.model.PositionCondition;
import com.trycoder.model.PositionStatus;

public interface PositionRepository extends JpaRepository<Position, Long> {
	List<Position> findByStatus(PositionStatus status);
	long countByStatus(PositionStatus status);
	long countByCondition(PositionCondition condition);
	Position findByPositionName(String name);
	
	@Query("SELECT p FROM Position p WHERE p.status = 'AVAILABLE' AND p.condition = 'ACTIVE' AND p.description = 'KV 2'")
	List<Position> getAvailableActivePositions();
	
	List<Position> findByStatusAndConditionAndDescription(PositionStatus status, PositionCondition condition, String description);
}
