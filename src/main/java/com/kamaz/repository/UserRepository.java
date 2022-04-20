package com.kamaz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kamaz.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Optional<User> findById(Long id);

	@Modifying
	@Query(value = "insert into user_positions (user_id, position_id) VALUES (:userId, :positionId)", nativeQuery = true)
	@Transactional
	void addUserPosition(@Param("userId") Long userId, @Param("positionId") Long positionId);

	@Modifying
	@Query(value = "delete from user_positions where user_id = :userId and position_id = :positionId", nativeQuery = true)
	@Transactional
	void deleteUserPosition(@Param("userId") Long userId, @Param("positionId") Long positionId);

	@Modifying
	@Query(value = "insert into user_departaments (user_id, departament_id) VALUES (:userId, :departamentId)", nativeQuery = true)
	@Transactional
	void addUserToDepartament(@Param("userId") Long userId, @Param("departamentId") Long departamentId);

	@Modifying
	@Query(value = "delete from user_departaments where user_id = :userId and departament_id = :departamentId", nativeQuery = true)
	@Transactional
	void deleteUserFromDepartament(@Param("userId") Long userId, @Param("departamentId") Long departamentId);

	@Modifying
	@Query(value = "insert into user_employments (user_id, employment_id) VALUES (:userId, :employmentId)", nativeQuery = true)
	@Transactional
	void addUserEmployment(@Param("userId") Long userId, @Param("employmentId") Long employmentId);

	@Modifying
	@Query(value = "delete from user_employments where user_id = :userId and employment_id = :employmentId", nativeQuery = true)
	@Transactional
	void deleteUserEmployment(@Param("userId") Long userId, @Param("employmentId") Long employmentId);

	List<User> findUserCallbackById(Long id);

	@Query(value = "SELECT * FROM users c WHERE c.email = :email", nativeQuery = true)
	public User findByUserEmail(@Param("email") String email);

	public User findByResetPasswordToken(String token);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	User findUserById(Long id);

}
