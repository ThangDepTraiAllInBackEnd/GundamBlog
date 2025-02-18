package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDTO implements Serializable {

  private String username;
  private String password;
  private String role;
  private Boolean status;
}
