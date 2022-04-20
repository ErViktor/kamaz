package com.kamaz.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kamaz.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
	List<Room> findAll();

	Optional<Room> findByName(String name);

	Optional<Room> findById(Long id);

	List<Room> findUserRoomByUsersId(Long id);

}