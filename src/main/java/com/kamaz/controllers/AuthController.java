package com.kamaz.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamaz.models.ERole;
import com.kamaz.models.Role;
import com.kamaz.models.User;
import com.kamaz.payload.request.LoginRequest;
import com.kamaz.payload.request.SignupRequest;
import com.kamaz.payload.response.JwtResponse;
import com.kamaz.payload.response.MessageResponse;
import com.kamaz.repository.RoleRepository;
import com.kamaz.repository.UserRepository;
import com.kamaz.security.jwt.JwtUtils;
import com.kamaz.security.services.UserDetailsImpl;
import com.kamaz.services.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private UserService userService;

	/*
	 * register user post { "username": "test", "email": "test@test.com",
	 * "password": "pa$$w0rd", "role": ["user","mod","admin"] }
	 *
	 * signin user post { "username": "Test", "password": "ssvcge123q" }
	 *
	 * Authorization Bearer code (header)
	 */

	private static long get64LeastSignificantBitsForVersion1() {
		Random random = new Random();
		long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
		long variant3BitFlag = 0x8000000000000000L;
		return random63BitLong + variant3BitFlag;
	}

	private static long get64MostSignificantBitsForVersion1() {
		LocalDateTime start = LocalDateTime.of(1582, 10, 15, 0, 0, 0);
		Duration duration = Duration.between(start, LocalDateTime.now());
		long seconds = duration.getSeconds();
		long nanos = duration.getNano();
		long timeForUuidIn100Nanos = seconds * 10000000 + nanos * 100;
		long least12SignificatBitOfTime = (timeForUuidIn100Nanos & 0x000000000000FFFFL) >> 4;
		long version = 1 << 12;
		return (timeForUuidIn100Nanos & 0xFFFFFFFFFFFF0000L) + version + least12SignificatBitOfTime;
	}

	public static UUID generateType1UUID() {
		long most64SigBits = get64MostSignificantBitsForVersion1();
		long least64SigBits = get64LeastSignificantBitsForVersion1();
		return new UUID(most64SigBits, least64SigBits);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		String currentPrincipalName = authentication.getName();

		UUID type1UUID = null;
		Optional<User> roomData = userRepository.findByUsername(currentPrincipalName);
		if (roomData.isPresent()) {
			try {
				type1UUID = generateType1UUID();
				User _room = roomData.get();
				_room.setUserroom(type1UUID);
				userRepository.save(_room);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		System.out.println("Yup, 💰 is there 💰");

		String tempKey = userRepository.findById(userDetails.getId()).get().getPassword();
		String devicekeys = tempKey.substring(tempKey.length() - 12);

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), type1UUID, roles, devicekeys));
	}

	@PostMapping("/signup")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Ok...!..."));
	}

}
