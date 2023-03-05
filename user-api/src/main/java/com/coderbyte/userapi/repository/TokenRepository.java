package com.coderbyte.userapi.repository;

import java.util.Optional;

import com.coderbyte.userapi.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  Optional<Token> findByToken(String token);
}
