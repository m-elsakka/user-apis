package com.alibou.security.repository;

import java.util.Optional;

import com.alibou.security.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  Optional<Token> findByToken(String token);
}
