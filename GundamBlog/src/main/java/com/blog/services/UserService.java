package com.blog.services;

import com.blog.models.User;

public interface UserService {
  boolean isUserNameExist(String username);

  User registerUser(String username, String password, String name, String email, String phone);

}
