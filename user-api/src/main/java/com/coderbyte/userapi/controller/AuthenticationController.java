package com.coderbyte.userapi.controller;

import com.coderbyte.userapi.model.UserResponse;
import com.coderbyte.userapi.service.AuthenticationService;
import com.coderbyte.userapi.model.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping
  public ResponseEntity<UserResponse> registerUser(
      @RequestBody UserRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @GetMapping("/{userId}")
  public ResponseEntity<UserRequest> getUser(@PathVariable String userId) {
    return ResponseEntity.ok(service.getUser(userId));
  }


}
