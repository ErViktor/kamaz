package com.kamaz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kamaz.models.Employment;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Long> {

	Optional<Employment> findById(Long id);

	Optional<Employment> findByName(String name);

	List<Employment> findAll();

}
