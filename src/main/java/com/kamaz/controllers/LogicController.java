package com.kamaz.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamaz.payload.request.UserPositionRequest;
import com.kamaz.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/logic")
public class LogicController {
	private static final Logger logger = LoggerFactory.getLogger(LogicController.class);

	@Autowired
	UserService userService;

	@PostMapping("/userposition")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> set_position_to_user(@Valid @RequestBody UserPositionRequest userPositionRequest) {
		List<?> list = new ArrayList<String>();
		try {
			list = userService.addPositionToUser(userPositionRequest.getUserid(), userPositionRequest.getParamid());
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getCause().getCause().getLocalizedMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/userposition")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> delete_user_position(@Valid @RequestBody UserPositionRequest userPositionRequest) {
		List<?> list = new ArrayList<String>();
		try {
			list = userService.deleteUserPosition(userPositionRequest.getUserid(), userPositionRequest.getParamid());
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getCause().getCause().getLocalizedMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/userdepartament")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> set_departament_to_user(@Valid @RequestBody UserPositionRequest userPositionRequest) {
		List<?> list = new ArrayList<String>();
		try {
			list = userService.addUserToDepartament(userPositionRequest.getUserid(), userPositionRequest.getParamid());
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getCause().getCause().getLocalizedMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/userdepartament")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> delete_user_departament(@Valid @RequestBody UserPositionRequest userPositionRequest) {
		List<?> list = new ArrayList<String>();
		try {
			list = userService.deleteUserFromDepartament(userPositionRequest.getUserid(),
					userPositionRequest.getParamid());
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getCause().getCause().getLocalizedMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Employment
	@PostMapping("/useremployment")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> set_employment_to_user(@Valid @RequestBody UserPositionRequest userPositionRequest) {
		List<?> list = new ArrayList<String>();
		try {
			list = userService.addUserEmployment(userPositionRequest.getUserid(), userPositionRequest.getParamid());
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getCause().getCause().getLocalizedMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/useremployment")
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> delete_user_employment(@Valid @RequestBody UserPositionRequest userPositionRequest) {
		List<?> list = new ArrayList<String>();
		try {
			list = userService.deleteUserEmployment(userPositionRequest.getUserid(), userPositionRequest.getParamid());
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("ERROR {} " + e.getMessage());
			return new ResponseEntity<>(e.getCause().getCause().getLocalizedMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
