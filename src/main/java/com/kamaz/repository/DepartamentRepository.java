package com.kamaz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kamaz.models.Departament;

@Repository
public interface DepartamentRepository extends JpaRepository<Departament, Long> {

	Optional<Departament> findById(Long id);

	Optional<Departament> findByName(String name);

	List<Departament> findAll();

}
