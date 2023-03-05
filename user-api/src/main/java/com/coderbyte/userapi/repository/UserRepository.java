package com.coderbyte.userapi.repository;

import java.util.Optional;

import com.coderbyte.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  Optional<User> findById(String id);

}
