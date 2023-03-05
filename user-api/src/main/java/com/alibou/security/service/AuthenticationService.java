package com.alibou.security.service;

import com.alibou.security.model.UserResponse;
import com.alibou.security.model.UserRequest;
import com.alibou.security.model.Token;
import com.alibou.security.repository.TokenRepository;
import com.alibou.security.model.User;
import com.alibou.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public UserResponse register(UserRequest request) {
    var user = User.builder()
        .id(jwtService.encryptEmail(request.getEmail()))
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);
    return UserResponse.builder()
        .id(savedUser.getId())
        .accessToken(jwtToken)
        .build();
  }

  public UserRequest getUser(String userId) {
    var user = repository.findById(userId)
        .orElseThrow();
    return UserRequest.builder()
        .id(user.getId())
        .firstname(user.getFirstname())
        .lastname(user.getLastname())
        .marketingConsent(user.isMarketingConsent())
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .accessToken(jwtToken)
        .build();
    tokenRepository.save(token);
  }
}
