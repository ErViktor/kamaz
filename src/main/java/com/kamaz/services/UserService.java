package com.kamaz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kamaz.exceptions.ServicesException;
import com.kamaz.models.User;
import com.kamaz.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private UserRepository userRepository;

	public Optional<User> getUserLoginByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User updateUser(User user) {
		try {
			User u = userRepository.findUserById(user.getId());
			u.setAge(user.getAge());
			u.setDateOf(user.getDateOf());
			u.setEmail(user.getEmail());
			u.setFullname(user.getFullname());
			u.setUsername(user.getUsername());
			return userRepository.save(u);
		} catch (Exception ex) {
			throw new ServicesException(ex.getCause().getCause().getLocalizedMessage());
		}
	}

	// position
	public List<User> addPositionToUser(Long userid, Long positionid) {
		try {
			userRepository.addUserPosition(userid, positionid);
			return userRepository.findUserCallbackById(userid);
		} catch (Exception ex) {
			throw new ServicesException(ex.getCause().getCause().getLocalizedMessage());
		}
	}

	public List<User> deleteUserPosition(Long userid, Long positionid) {
		try {
			userRepository.deleteUserPosition(userid, positionid);
			return userRepository.findUserCallbackById(userid);
		} catch (Exception ex) {
			throw new ServicesException(ex.getCause().getCause().getLocalizedMessage());
		}
	}

	// departament
	public List<User> addUserToDepartament(Long userid, Long departamentid) {
		try {
			userRepository.addUserToDepartament(userid, departamentid);
			return userRepository.findUserCallbackById(userid);
		} catch (Exception ex) {
			throw new ServicesException(ex.getCause().getCause().getLocalizedMessage());
		}
	}

	public List<User> deleteUserFromDepartament(Long userid, Long departamentid) {
		try {
			userRepository.deleteUserFromDepartament(userid, departamentid);
			return userRepository.findUserCallbackById(userid);
		} catch (Exception ex) {
			throw new ServicesException(ex.getCause().getCause().getLocalizedMessage());
		}
	}

	// employment
	public List<User> addUserEmployment(Long userid, Long employmentid) {
		try {
			userRepository.addUserEmployment(userid, employmentid);
			return userRepository.findUserCallbackById(userid);
		} catch (Exception ex) {
			throw new ServicesException(ex.getCause().getCause().getLocalizedMessage());
		}
	}

	public List<User> deleteUserEmployment(Long userid, Long employmentid) {
		try {
			userRepository.deleteUserEmployment(userid, employmentid);
			return userRepository.findUserCallbackById(userid);
		} catch (Exception ex) {
			throw new ServicesException(ex.getCause().getCause().getLocalizedMessage());
		}
	}

	public void updateResetPasswordToken(String token, String email) throws Exception {
		User customer = userRepository.findByUserEmail(email);
		if (customer != null) {
			customer.setResetPasswordToken(token);
			userRepository.save(customer);
		} else {
			throw new Exception("Could not find any customer with the email " + email);
		}
	}

	public User getByResetPasswordToken(String token) {
		return userRepository.findByResetPasswordToken(token);
	}

	public void updatePassword(User customer, String newPassword) {
		String encodedPassword = encoder.encode(newPassword);
		customer.setPassword(encodedPassword);
		customer.setResetPasswordToken(null);
		userRepository.save(customer);
	}
}
