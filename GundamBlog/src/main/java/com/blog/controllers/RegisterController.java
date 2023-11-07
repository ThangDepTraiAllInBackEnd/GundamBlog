package com.blog.controllers;

import com.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
  @Autowired
  private UserService userService;

  @GetMapping("/register")
  private String showRegisterForm() {
    return "/pages/register";
  }

  @PostMapping("/register")
  private String registerUser(String username, String password, String name, String email, String phone) {
    userService.registerUser(username, password, name, email, phone);
    return "pages/login";
  }
}
