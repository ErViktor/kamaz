package com.kamaz.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kamaz.models.Departament;
import com.kamaz.repository.RoleRepository;
import com.kamaz.repository.UserRepository;
import com.kamaz.services.DepartamentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dep")
public class DepartamentController {
	private static final Logger logger = LoggerFactory.getLogger(DepartamentController.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	DepartamentService departamentService;

	@PostMapping("/departament")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> add_new_departament(@RequestBody Departament positionData) {
		List<?> list = new ArrayList<String>();
		String json = null;
		try {
			list = departamentService.newDepartament(positionData);
			json = list.toString();
			return new ResponseEntity<>(json, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/departaments")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> get_all_departaments() throws JsonProcessingException {
		try {
			List<Departament> arr = departamentService.getDepartaments();
			if (arr.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(arr, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getLocalizedMessage(), HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping("/departament")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> update_departament(@RequestBody Departament positionData) {
		try {
			Departament pos = departamentService.updateDepartament(positionData);
			return new ResponseEntity<>(pos, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/departament/{id}")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete_departament(@PathVariable("id") long id) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		String json = null;
		try {
			Departament pos = departamentService.deleteDepartament(id);
			json = mapper.writeValueAsString(pos);
			return new ResponseEntity<>(json, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
