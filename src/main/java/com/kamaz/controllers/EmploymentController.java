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
import com.kamaz.models.Employment;
import com.kamaz.repository.RoleRepository;
import com.kamaz.repository.UserRepository;
import com.kamaz.services.EmploymentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/emp")
public class EmploymentController {
	private static final Logger logger = LoggerFactory.getLogger(EmploymentController.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	EmploymentService employmentService;

	@PostMapping("/employment")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> add_new_employment(@RequestBody Employment positionData) {
		List<?> list = new ArrayList<String>();
		String json = null;
		try {
			list = employmentService.newEmployment(positionData);
			json = list.toString();
			return new ResponseEntity<>(json, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employments")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> get_all_employments() throws JsonProcessingException {
		try {
			List<Employment> arr = employmentService.getEmployments();
			if (arr.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(arr, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getLocalizedMessage(), HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping("/employment")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> update_employment(@RequestBody Employment positionData) {
		try {
			Employment pos = employmentService.updateEmployment(positionData);
			return new ResponseEntity<>(pos, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employment/{id}")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete_employment(@PathVariable("id") long id) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		String json = null;
		try {
			Employment pos = employmentService.deleteEmployment(id);
			json = mapper.writeValueAsString(pos);
			return new ResponseEntity<>(json, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
