package com.blog.mapper;

import com.blog.dto.UserCreationDTO;
import com.blog.dto.UserDTO;
import com.blog.models.User;

public class UserMapper {
  private static UserMapper INSTANCE;

  public static UserMapper getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new UserMapper();
    }
    return INSTANCE;
  }

  public User toEntity(UserCreationDTO dto) {
    User user = new User();
    user.setUsername(dto.getUsername());
    user.setPassword(dto.getPassword());
    user.setRole(dto.getRole());
    user.setStatus(dto.getStatus());
    return user;
  }

  public UserDTO toDTO(User user) {
    UserDTO dto = new UserDTO();
    dto.setUsername(user.getUsername());
    dto.setRole(user.getRole());
    dto.setStatus(user.getStatus());
    return dto;
  }

}
