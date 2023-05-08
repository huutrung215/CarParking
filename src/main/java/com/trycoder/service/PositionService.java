package com.trycoder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trycoder.model.Position;
import com.trycoder.model.PositionStatus;
import com.trycoder.repository.PositionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PositionService {
	
	@Autowired
    private PositionRepository positionRepository;
	
	 public List<Position> getAllPositions() {
	        return positionRepository.findAll();
    }
	 
	 
	 public Position getPositionById(Long id) {
	        return positionRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy vị trí id " + id));
    } 
	 
	 public Position createPosition(Position position) {
	        return positionRepository.save(position);
    } 
	 
	 public Position updatePosition(Long id, Position newPosition) {
	        Position position = getPositionById(id);
	        position.setPositionName(newPosition.getPositionName());
	        position.setCondition(newPosition.getCondition());
	        position.setDescription(newPosition.getDescription());
	        return positionRepository.save(position);
    } 
	 
	 public void deletePosition(Long id) {
	        positionRepository.deleteById(id);
    } 
	 
	public List<Position> getPositionsByStatus(PositionStatus status) {
	        return positionRepository.findByStatus(status);
    } 
}
