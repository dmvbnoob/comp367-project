package com.team5.bms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team5.bms.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
