package com.blog.services.impl;

import com.blog.models.UserInfo;
import com.blog.repositories.UserInfoRepository;
import com.blog.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UserInfoServiceImpl implements UserInfoService {
  @Autowired
  UserInfoRepository userInfoRepository;

  @Override
  public boolean isPhoneExist(String phone) {
    UserInfo checkPhoneExist = userInfoRepository.findByPhone(phone);
    return checkPhoneExist != null;
  }

  @Override
  public boolean isEmailExist(String email) {
    UserInfo checkEmailExist = userInfoRepository.findByEmail(email);
    return checkEmailExist != null;
  }


}
