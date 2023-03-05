package com.alibou.security.controller;

import com.alibou.security.model.UserResponse;
import com.alibou.security.service.AuthenticationService;
import com.alibou.security.model.UserRequest;
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
