package com.team5.bms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.team5.bms.model.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    List<User> findByBuildingId(Long buildingId);

}
