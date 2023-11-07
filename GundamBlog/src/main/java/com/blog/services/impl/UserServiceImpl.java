package com.blog.services.impl;

import com.blog.common.Message;
import com.blog.common.StringCommon;
import com.blog.models.User;
import com.blog.models.UserInfo;
import com.blog.repositories.UserRepository;
import com.blog.services.UserInfoService;
import com.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  UserInfoService userInfoService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public boolean isUserNameExist(String username) {
    User checkUsernameExist = userRepository.findByUsername(username);
    return checkUsernameExist != null;
  }

  @Override
  public User registerUser(String username, String password, String name, String email, String phone) {

    // username already  exist
    if (isUserNameExist(username)) {
      System.out.println(Message.USERNAME_EXIST);
      return null;
      // phone already  exist
    } else if (userInfoService.isPhoneExist(phone)) {
      System.out.println(Message.PHONE_EXIST);
      return null;
      // email already  exist
    } else if (userInfoService.isEmailExist(email)) {
      System.out.println(Message.EMAIL_EXIST);
      return null;
    }
    User user = new User(StringCommon.trimAllSpace(username), password, "ADMIN");
    UserInfo userInfo = new UserInfo(StringCommon.trimSpace(name), email, phone);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setStatus(true);
    user.setUserInfo(userInfo);
    userInfo.setUser(user);
    return userRepository.save(user);
  }
}
