package com.blog.services;

public interface UserInfoService {
  boolean isPhoneExist(String phone);
  boolean isEmailExist(String email);
}

