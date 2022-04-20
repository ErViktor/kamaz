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
import com.kamaz.models.Position;
import com.kamaz.repository.RoleRepository;
import com.kamaz.repository.UserRepository;
import com.kamaz.services.PositionService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pos")
public class PositionController {
	private static final Logger logger = LoggerFactory.getLogger(PositionController.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PositionService positionService;

	@PostMapping("/position")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> add_new_position(@RequestBody Position positionData) {
		List<?> list = new ArrayList<String>();
		String json = null;
		try {
			list = positionService.newPosition(positionData);
			json = list.toString();
			return new ResponseEntity<>(json, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/positions")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> get_all_positions() throws JsonProcessingException {
		try {
			List<Position> arr = positionService.getPositions();
			if (arr.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(arr, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getLocalizedMessage(), HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping("/position")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> update_position(@RequestBody Position positionData) {
		try {
			Position pos = positionService.updatePosition(positionData);
			return new ResponseEntity<>(pos, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/position/{id}")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete_position(@PathVariable("id") long id) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		String json = null;
		try {
			Position pos = positionService.deletePosition(id);
			json = mapper.writeValueAsString(pos);
			return new ResponseEntity<>(json, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
