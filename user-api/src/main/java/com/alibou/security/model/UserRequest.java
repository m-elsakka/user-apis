package com.alibou.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

  private String id;
  private String firstname;
  private String lastname;
  private String email;
  private boolean marketingConsent;
}
