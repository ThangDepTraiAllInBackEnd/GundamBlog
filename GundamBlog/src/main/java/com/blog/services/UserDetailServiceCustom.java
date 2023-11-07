package com.blog.services;

import com.blog.common.Message;
import com.blog.models.User;
import com.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceCustom implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(Message.USER_NOT_FOUND);
    }
    if (user.getStatus() == false) {
      System.out.println(Message.USER_IS_BANNED);
      return null;
    }
    org.springframework.security.core.userdetails.User.UserBuilder builder = org.springframework.security.core.userdetails.User
        .withUsername(username);
    builder.password(user.getPassword());
    builder.roles(user.getRole());
    return builder.build();
  }
}
